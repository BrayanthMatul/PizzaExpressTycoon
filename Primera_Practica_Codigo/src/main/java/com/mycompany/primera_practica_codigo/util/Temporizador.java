/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_practica_codigo.util;

/**
 *
 * @author Matul
 */
public class Temporizador extends Thread {

    private int segundos;
    private int minutos;
    private boolean tiempoAgotado;
    private Temporizable temporizable;

    public Temporizador(int minutos, int segundos) {
        this.minutos = minutos;
        this.segundos = segundos;
        this.tiempoAgotado = false;
    }

    public int getSegundos() {
        return segundos;
    }

    public int getMinutos() {
        return minutos;
    }

    public void notificarCambioDeTiempo() {
        temporizable.cambioDeTiempo(segundos, minutos);
    }

    public void setTemporizable(Temporizable temporizable) {
        this.temporizable = temporizable;
    }

    private void actualizarTiempo() {
        if (segundos == 0) {
            if (minutos > 0) {
                minutos--;
                segundos = 59;
            } else {
                tiempoAgotado = true;
            }
        } else {
            segundos--;
        }
    }

    @Override
    public void run() {
        while (!tiempoAgotado) {
            try {
                Thread.sleep(1000); // Espera 1 segundo
                actualizarTiempo();
                notificarCambioDeTiempo();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("El temporizador fue interrumpido", e);
            }
        }
    }
}
