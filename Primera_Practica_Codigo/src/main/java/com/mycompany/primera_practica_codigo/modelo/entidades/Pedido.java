package com.mycompany.primera_practica_codigo.modelo.entidades;

import java.util.concurrent.atomic.AtomicBoolean;
import com.mycompany.primera_practica_codigo.util.ActualizablePedido;

public class Pedido {
    private int idPedido;
    private String nombreProducto;
    private Partida partida;
    private int numeroPedido;
    private int tiempoLimiteSeg;
    private int tiempoRestanteSeg;
    private EstadoPedido estadoActual;
    private boolean cancelado;
    private boolean noEntregado;
    private ActualizablePedido actualizablePedido;

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
        this.estadoActual = EstadoPedido.RECIBIDA;
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
                break;
            case PREPARANDO:
                estadoActual = EstadoPedido.EN_HORNO;
                desactivarBotonCancelar();
                break;
            case EN_HORNO:
                // Solo eliminar si es la primera vez
                if (eliminado.compareAndSet(false, true)) {
                    estadoActual = EstadoPedido.ENTREGADO;
                    partida.eliminarPedido(numeroPedido, FormaEliminacion.ENTREGADO);
                }
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
            partida.eliminarPedido(numeroPedido, FormaEliminacion.CANCELADO);
        }
    }

    public void marcarComoNoEntregado() {
        // Solo eliminar si es la primera vez
        if (eliminado.compareAndSet(false, true)) {
            this.noEntregado = true;
            this.estadoActual = EstadoPedido.NO_ENTREGADO;
            partida.eliminarPedido(numeroPedido, FormaEliminacion.NO_ENTREGADO);
        }
    }

    public boolean puedeCancelar() {
        return estadoActual == EstadoPedido.RECIBIDA || estadoActual == EstadoPedido.PREPARANDO;
    }

    public boolean estaFinalizado() {
        return estadoActual == EstadoPedido.ENTREGADO;
    }

    public boolean esRapido() {
        int tiempoRapido = tiempoLimiteSeg / 2;
        return estadoActual == EstadoPedido.RECIBIDA && tiempoRestanteSeg <= tiempoRapido;
    }
}