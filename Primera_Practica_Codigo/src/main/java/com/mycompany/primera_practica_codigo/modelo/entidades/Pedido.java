package com.mycompany.primera_practica_codigo.modelo.entidades;

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
        this.tiempoRestanteSeg--;
        if (this.tiempoRestanteSeg == 0) {
            marcarComoNoEntregado();
        }
    }

    public void avanzarEstado() {
        switch (estadoActual) {
            case RECIBIDA:
                estadoActual = EstadoPedido.PREPARANDO;
                break;
            case PREPARANDO:
                estadoActual = EstadoPedido.EN_HORNO;
                break;
            case EN_HORNO:
                estadoActual = EstadoPedido.ENTREGADO;
                partida.eliminarPedido(numeroPedido, FormaEliminacion.ENTREGADO);
                break;
            default:
                // No hacer nada si ya está entregado o en un estado no válido
                return;
        }
        if (actualizablePedido != null) {
            actualizablePedido.actualizarEstadoPedido(estadoActual);
        }
    }

    public void cancelar() {
        this.cancelado = true;
        this.estadoActual = EstadoPedido.CANCELADO;
        partida.eliminarPedido(numeroPedido, FormaEliminacion.CANCELADO);
    }

    public void marcarComoNoEntregado() {
        this.noEntregado = true;
        this.estadoActual = EstadoPedido.NO_ENTREGADO;
        partida.eliminarPedido(numeroPedido, FormaEliminacion.NO_ENTREGADO);
    }

    public boolean puedeCancelar() {
        if (estadoActual == EstadoPedido.RECIBIDA || estadoActual == EstadoPedido.PREPARANDO) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estaFinalizado() {
        if (estadoActual == EstadoPedido.ENTREGADO) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esRapido() {
        int tiempoRapido = tiempoLimiteSeg / 2;
        if (estadoActual == EstadoPedido.RECIBIDA && tiempoRestanteSeg <= tiempoRapido) {
            return true;
        } else {
            return false;
        }
    }
}
