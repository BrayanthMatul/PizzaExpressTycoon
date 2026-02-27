package com.mycompany.primera_practica_codigo.Usuario;

import com.mycompany.primera_practica_codigo.ClasesIniciales.Sucursal;

public class Administrador extends Usuario {
    private Sucursal sucursal;

    // Constructores
    public Administrador() {
        super();
    }

    public Administrador(Sucursal sucursal) {
        super();
        this.sucursal = sucursal;
    }

    // Getters y Setters
    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    
    // Métodos
    public void gestionarProductos() {
        // TODO: implementar
    }
    
    public void consultarEstadisticasSucursal() {
        // TODO: implementar
    }
    
    public void consultarRankingSucursal() {
        // TODO: implementar
    }
    
    public void exportarReporte() {
        // TODO: implementar
    }
}
