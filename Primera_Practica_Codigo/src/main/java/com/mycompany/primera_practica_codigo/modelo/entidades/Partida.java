package com.mycompany.primera_practica_codigo.modelo.entidades;

import java.security.Timestamp;
import java.sql.Time;

public class Partida {
    private int idPartida;
    private Usuario jugador;
    private int puntajeTotal;
    private int nivelAlcanzado;
    private int pedidosCompletados;
    private int pedidosCancelados;
    private int pedidosNoEntregados;
    private int nivelActual;
    private Timestamp fechaYHoraInicio;
    private Timestamp fechaYHoraFin;

    private Partida() {
        this.nivelActual = 1;

    }

    public void agregarPuntos(int puntos) {
        // TODO: implementar
    }

    public void incrementarNivel() {
        // TODO: implementar
    }

    public void finalizarPartida() {
        // TODO: implementar
    }
}
