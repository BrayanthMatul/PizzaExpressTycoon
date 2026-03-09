package com.mycompany.primera_practica_codigo.util;

import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;

public class GeneradorPedidos extends Thread {

    private Partida partida;
    private int frecuenciaGeneracionPedidos;

    public GeneradorPedidos(Partida partida) {
        this.partida = partida;
        this.frecuenciaGeneracionPedidos = partida.getFrecuenciaGeneracionPedidosNivel();
    }

    @Override
    public void run() {
        while (partida.isPartidaActiva()) {
            try {
                Thread.sleep(frecuenciaGeneracionPedidos * 1000);
                if (partida.isPartidaActiva()) {
                    partida.agregarPedido();
                }
            } catch (InterruptedException e) {
                break; // Salir del bucle si el hilo es interrumpido

            }
        }
    }

}
