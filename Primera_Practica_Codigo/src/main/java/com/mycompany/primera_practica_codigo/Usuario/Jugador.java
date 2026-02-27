package com.mycompany.primera_practica_codigo.Usuario;

import com.mycompany.primera_practica_codigo.ClasesIniciales.Sucursal;
import com.mycompany.primera_practica_codigo.ClasesIniciales.Partida;

public class Jugador extends Usuario {
    private int idJugador;
    private Sucursal sucursal;
    private int partidasJugadas;
    private int nivelMaximoAlcanzado;
    private int puntajeMaximo;
    private int puntajeAcumulado;

    // Constructores
    public Jugador() {
        super();
    }

    public Jugador(int idJugador, Sucursal sucursal, int partidasJugadas, int nivelMaximoAlcanzado, 
                   int puntajeMaximo, int puntajeAcumulado) {
        super();
        this.idJugador = idJugador;
        this.sucursal = sucursal;
        this.partidasJugadas = partidasJugadas;
        this.nivelMaximoAlcanzado = nivelMaximoAlcanzado;
        this.puntajeMaximo = puntajeMaximo;
        this.puntajeAcumulado = puntajeAcumulado;
    }

    // Getters y Setters
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
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

    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
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
