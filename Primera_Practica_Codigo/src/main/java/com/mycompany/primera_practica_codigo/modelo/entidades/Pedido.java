package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Pedido {
    private int idPedido;
    private String nombreProducto;
    private Partida partida;
    private int numeroPedido;
    private int tiempoLimiteSeg;
    private int tiempoRestanteSeg;
    private EstadoPedido estadoActual;

    // Getters y Setters

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public int getTiempoLimiteSeg() {
        return tiempoLimiteSeg;
    }

    public void setTiempoLimiteSeg(int tiempoLimiteSeg) {
        this.tiempoLimiteSeg = tiempoLimiteSeg;
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

    public void setEstadoActual(EstadoPedido estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void avanzarEstado() {
        // TODO: implementar
    }

    public void incrementarNivel() {
        // TODO: implementar
    }

    public void cancelar() {
        // TODO: implementar
    }

    public void marcarComoNoEntregado() {
        // TODO: implementar
    }

    public boolean puedeCancelar() {
        // TODO: implementar
        return false;
    }

    public boolean estaFinalizado() {
        // TODO: implementar
        return false;
    }

    public boolean esRapido() {
        // TODO: implementar
        return false;
    }
}
