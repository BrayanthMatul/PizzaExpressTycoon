package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.util.ConexionBD;

public class EstadisticaGlobalDAO {

    public int totalPartidasJugadas() throws SQLException {
        String query = "SELECT COUNT(*) FROM partida;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de partidas jugadas: " + e.getMessage(), e);
        }
        return 0;
    }

    public int totalPartidasJugadasPorSucursal(int idSucursal) throws SQLException {
        String query = "SELECT COUNT(*) FROM partida WHERE id_sucursal = ?";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, idSucursal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de partidas jugadas por sucursal: " + e.getMessage(), e);
        }
        return 0;
    }

    public int nivelMaximoAlcanzadoPorSucursal(int idSucursal) throws SQLException {
        String query = "SELECT MAX(nivel) FROM partida WHERE id_sucursal = ?";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, idSucursal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el nivel máximo alcanzado por sucursal: " + e.getMessage(), e);
        }
        return 0;
    }

    public int totalPedidosEntregados() throws SQLException {
        // sumar las filas de la tabla partida en la columna pedidosCompletados
        String query = "SELECT SUM(pedidosCompletados) FROM partida;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de pedidos entregados: " + e.getMessage(), e);
        }
        return 0;
    }

    public int totalPedidosEntregadosPorSucursal(int idSucursal) throws SQLException {
        String query = "SELECT SUM(pedidosCompletados) FROM partida WHERE id_sucursal = ?;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, idSucursal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de pedidos entregados por sucursal: " + e.getMessage(),
                    e);
        }
        return 0;
    }

    public int totalPedidosCancelados() throws SQLException {
        String query = "SELECT SUM(pedidosCancelados) FROM partida;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de pedidos cancelados: " + e.getMessage(), e);
        }
        return 0;
    }

    public int totalPedidosCanceladosPorSucursal(int idSucursal) throws SQLException {
        String query = "SELECT SUM(pedidosCancelados) FROM partida WHERE id_sucursal = ?;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, idSucursal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new SQLException("Error al obtener el total de pedidos cancelados por sucursal: " + e.getMessage(),
                    e);
        }
        return 0;
    }

    public int totalPedidosNoEntregados() throws SQLException {
        String query = "SELECT SUM(pedidosNoEntregados) FROM partida;";
        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new SQLException("Error al obtener el total de pedidos no entregados: " + e.getMessage(), e);
        }
        return 0;
    }

    public int totalPedidosNoEntregadosPorSucursal(int idSucursal) throws SQLException {
        String query = "SELECT SUM(pedidosNoEntregados) FROM partida WHERE id_sucursal = ?;";

        try (Connection connection = ConexionBD.getConexion();
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, idSucursal);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el total de pedidos no entregados por sucursal: " + e.getMessage(),
                    e);
        }
        return 0;
    }
}
