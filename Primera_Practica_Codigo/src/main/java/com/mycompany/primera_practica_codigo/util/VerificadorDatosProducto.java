package com.mycompany.primera_practica_codigo.util;

import java.sql.SQLException;
import java.util.Optional;
import com.mycompany.primera_practica_codigo.modelo.dao.ProductoDAO;
import com.mycompany.primera_practica_codigo.modelo.entidades.Producto;

public class VerificadorDatosProducto {

    private boolean existeNombreProducto;

    public void verificarDatos(String nombreProducto, Integer idSucursal) throws SQLException {
        ProductoDAO productoDAO = new ProductoDAO();
        Optional<Producto> producto = productoDAO.encontrarPorNombreYIdSucursal(nombreProducto, idSucursal);
        this.existeNombreProducto = producto.isPresent();
    }

    public boolean getExisteNombreProducto() {
        return existeNombreProducto;
    }

}
