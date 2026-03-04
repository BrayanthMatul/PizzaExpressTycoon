/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_practica_codigo.util;

import com.mycompany.primera_practica_codigo.exceptions.NumeroInvalidoException;
import com.mycompany.primera_practica_codigo.exceptions.TextoVacioException;
import javax.swing.text.JTextComponent;

/**
 *
 * @author brayanth
 */
public class RecolectorDeDatos {
    public String recolectarTexto(JTextComponent campo) throws TextoVacioException {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new TextoVacioException();
        }
        return texto;
    }
    
    public int recolectarEntero(JTextComponent campo) throws NumeroInvalidoException {
        try {
            String texto = campo.getText().trim();
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new NumeroInvalidoException();
        }
    }
}
