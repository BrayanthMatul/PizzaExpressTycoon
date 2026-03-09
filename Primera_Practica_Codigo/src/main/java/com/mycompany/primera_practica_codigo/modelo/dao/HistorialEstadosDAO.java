package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.util.ConexionBD;
import com.mycompany.primera_practica_codigo.util.HistorialEstado;

public class HistorialEstadosDAO {

    public void guardarHistorialEstado(int idPedido, HistorialEstado historial) throws SQLException {
        String query = "INSERT INTO historial_de_estado (id_detalle_pedido, estado, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idPedido);
            ps.setString(2, historial.getEstado().toString());
            ps.setTimestamp(3, historial.getFechaHora());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar historial de estados: " + e.getMessage());
        }
    }
}
