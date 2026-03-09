package com.mycompany.primera_practica_codigo.util;

import java.sql.Timestamp;

import com.mycompany.primera_practica_codigo.modelo.entidades.EstadoPedido;

public class HistorialEstado {

    private int idHistorial;
    private int idDetallePedido;
    private EstadoPedido estado;
    private Timestamp fechaHora;

    public HistorialEstado(EstadoPedido estado, Timestamp fechaHora) {
        this.estado = estado;
        this.fechaHora = fechaHora;
    }

    // Getters

    public EstadoPedido getEstado() {
        return estado;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    // Setters

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public void setIdDetallePedido(int idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

}
