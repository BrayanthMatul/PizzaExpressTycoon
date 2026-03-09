package com.mycompany.primera_practica_codigo.modelo.entidades;

import java.sql.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import com.mycompany.primera_practica_codigo.util.ActualizablePedido;
import com.mycompany.primera_practica_codigo.util.HistorialEstado;

public class Pedido {

    private String nombreProducto;
    private EstadoPedido estadoActual;
    private int puntosObtenidos;

    private int numeroPedido;
    private Partida partida;
    private int idPedido;
    private int tiempoLimiteSeg;
    private int tiempoRestanteSeg;
    private boolean cancelado;
    private boolean noEntregado;
    private ActualizablePedido actualizablePedido;
    private List<HistorialEstado> historialEstados;

    // Evita que un pedido se elimine multiples veces
    private AtomicBoolean eliminado = new AtomicBoolean(false);

    public Pedido(String nombreProducto, Partida partida, int numeroPedido, int tiempoLimiteSeg) {
        this.nombreProducto = nombreProducto;
        this.partida = partida;
        this.numeroPedido = numeroPedido;
        this.tiempoLimiteSeg = tiempoLimiteSeg;
        this.tiempoRestanteSeg = tiempoLimiteSeg;
        this.cancelado = false;
        this.noEntregado = false;
        this.puntosObtenidos = 0;
        this.estadoActual = EstadoPedido.RECIBIDA;
        this.historialEstados = new ArrayList<>();
        generarHistorialEstado();

    }

    // Getters y Setters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public int getTiempoLimiteSeg() {
        return tiempoLimiteSeg;
    }

    public int getTiempoRestanteSeg() {
        return tiempoRestanteSeg;
    }

    public void setTiempoRestanteSeg(int tiempoRestanteSeg) {
        this.tiempoRestanteSeg = tiempoRestanteSeg;
    }

    public EstadoPedido getEstadoActual() {
        return estadoActual;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public boolean isNoEntregado() {
        return noEntregado;
    }

    public void setActualizablePedido(ActualizablePedido actualizablePedido) {
        this.actualizablePedido = actualizablePedido;
    }

    public List<HistorialEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    public void restarTiempo() {
        // Si ya fue eliminado, ignorar
        if (eliminado.get()) {
            return;
        }

        this.tiempoRestanteSeg--;
        if (this.tiempoRestanteSeg <= 0) {
            marcarComoNoEntregado();
        }
    }

    public void avanzarEstado() {
        // Si ya fue eliminado, ignorar
        if (eliminado.get()) {
            return;
        }

        switch (estadoActual) {
            case RECIBIDA:
                estadoActual = EstadoPedido.PREPARANDO;
                generarHistorialEstado();
                break;
            case PREPARANDO:
                estadoActual = EstadoPedido.EN_HORNO;
                desactivarBotonCancelar();
                generarHistorialEstado();
                break;
            case EN_HORNO:
                // Solo eliminar si es la primera vez
                if (eliminado.compareAndSet(false, true)) {
                    estadoActual = EstadoPedido.ENTREGADO;

                    partida.eliminarPedido(FormaEliminacion.ENTREGADO, this);
                }
                generarHistorialEstado();
                return; // No actualizar vista si ya fue eliminado
            default:
                return;
        }

        // Actualizar vista solo si no fue eliminado
        if (actualizablePedido != null) {
            actualizablePedido.actualizarEstadoPedido(estadoActual);
        }
    }

    private void desactivarBotonCancelar() {
        actualizablePedido.desactivarBotonCancelar();
    }

    public void cancelar() {
        // Solo eliminar si es la primera vez
        if (eliminado.compareAndSet(false, true)) {
            this.cancelado = true;
            this.estadoActual = EstadoPedido.CANCELADO;
            partida.eliminarPedido(FormaEliminacion.CANCELADO, this);
        }
    }

    public void marcarComoNoEntregado() {
        // Solo eliminar si es la primera vez
        if (eliminado.compareAndSet(false, true)) {
            this.noEntregado = true;
            this.estadoActual = EstadoPedido.NO_ENTREGADO;
            partida.eliminarPedido(FormaEliminacion.NO_ENTREGADO, this);
        }
    }

    public boolean puedeCancelar() {
        return estadoActual == EstadoPedido.RECIBIDA || estadoActual == EstadoPedido.PREPARANDO;
    }

    public boolean estaFinalizado() {
        return estadoActual == EstadoPedido.ENTREGADO;
    }

    public boolean esRapido() {
        if (estadoActual == EstadoPedido.CANCELADO || estadoActual == EstadoPedido.NO_ENTREGADO) {
            return false;
        } else {
            int tiempoMitad = (int) (tiempoLimiteSeg / 2);
            return tiempoRestanteSeg >= tiempoMitad;
        }
    }

    private void generarHistorialEstado() {
        Timestamp fechaHoraActual = new Timestamp(System.currentTimeMillis());
        HistorialEstado historial = new HistorialEstado(estadoActual, fechaHoraActual);
        historialEstados.add(historial);
    }
}