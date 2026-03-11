package com.mycompany.primera_practica_codigo.util;

import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.modelo.dao.SucursalDAO;

public class VerificadorDatosSucursal {

    private boolean existeSucursal;

    public void verificarDatos(String nombreSucursal) throws SQLException {
        SucursalDAO sucursalDAO = new SucursalDAO();
        this.existeSucursal = sucursalDAO.encontrarPorNombre(nombreSucursal) != null;
    }

    public boolean getExisteSucursal() {
        return existeSucursal;
    }

}
