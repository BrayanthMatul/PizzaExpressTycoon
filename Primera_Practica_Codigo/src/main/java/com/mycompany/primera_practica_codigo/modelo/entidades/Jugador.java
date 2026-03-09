package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Jugador extends Usuario {
    private int partidasJugadas;
    private int nivelMaximoAlcanzado;
    private int puntajeMaximo;
    private int puntajeTotalAcumulado;

    // Constructores
    public Jugador() {
        super();
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public int getNivelMaximoAlcanzado() {
        return nivelMaximoAlcanzado;
    }

    public void setNivelMaximoAlcanzado(int nivelMaximoAlcanzado) {
        this.nivelMaximoAlcanzado = nivelMaximoAlcanzado;
    }

    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public int getPuntajeTotalAcumulado() {
        return puntajeTotalAcumulado;
    }

    public void setPuntajeTotalAcumulado(int puntajeTotalAcumulado) {
        this.puntajeTotalAcumulado = puntajeTotalAcumulado;
    }

    // Métodos
    public void actualizarEstadisticas(Partida partida) {
        // TODO: implementar
    }

    public void incrementarPartidasJugadas() {
        // TODO: implementar
    }

    public void actualizarNivelMaximo(int nivel) {
        // TODO: implementar
    }

    public void actualizarPuntajeMaximo(int puntaje) {
        // TODO: implementar
    }

    public void agregarPuntajeAcumulado(int puntaje) {
        // TODO: implementar
    }
}
