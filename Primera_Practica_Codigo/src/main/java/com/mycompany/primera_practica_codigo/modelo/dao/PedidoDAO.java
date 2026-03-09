package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.modelo.entidades.Pedido;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

public class PedidoDAO {

    public void guardarDetallesPedido(Pedido pedido, int idPartida) throws SQLException {
        String query = "INSERT INTO detalles_pedido (id_partida, nombre_producto, estado_final, puntos_obtenidos) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPartida);
            ps.setString(2, pedido.getNombreProducto());
            ps.setString(3, pedido.getEstadoActual().toString());
            ps.setInt(4, pedido.getPuntosObtenidos());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar detalles del pedido: " + e.getMessage());
        }
    }

    public int obtenerIdUltimoPedidoAgregado() throws SQLException {
        String query = "SELECT MAX(id) AS idPedido FROM detalles_pedido";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idPedido");
            } else {
                throw new SQLException("No se pudo obtener el ID del último pedido agregado.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID del último pedido agregado: " + e.getMessage());
        }
    }
}
