/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.primera_practica_codigo.util;

import com.mycompany.primera_practica_codigo.modelo.entidades.Pedido;

/**
 *
 * @author Matul
 */
public interface Actualizable {

    void subirNivel(int nivel);

    void actualizarPuntos(int puntos);

    void actualizarPuntosParaSubirNivel(int puntosAcumulados, int puntosParaSubirNivel);

    void agregarPedido(Pedido pedido);

    void eliminarPedido(int numeroPedido);

    void ocultarPuntosParaSubirNivel();

}
