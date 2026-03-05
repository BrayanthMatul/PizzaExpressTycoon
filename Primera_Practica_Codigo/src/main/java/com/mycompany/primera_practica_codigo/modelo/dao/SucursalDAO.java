package com.mycompany.primera_practica_codigo.modelo.dao;

import com.mycompany.primera_practica_codigo.modelo.entidades.Sucursal;
import com.mycompany.primera_practica_codigo.util.BDCRUD;
import com.mycompany.primera_practica_codigo.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SucursalDAO extends BDCRUD<Sucursal, Integer> {

    @Override
    public void insertar(Sucursal sucursal) throws SQLException {
        String query = "INSERT INTO sucursal (nombre, ubicacion, telefono) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, sucursal.getNombre());
            ps.setString(2, sucursal.getUbicacion());
            ps.setString(3, sucursal.getTelefono());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar sucursal: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sucursal> encontrarPorID(Integer id) throws SQLException {
        String query = "SELECT * FROM sucursal WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Sucursal sucursal = new Sucursal(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getString("telefono")
                );
                return Optional.of(sucursal);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new SQLException("Error al buscar sucursal: " + e.getMessage());
        }
    }

    @Override
    public Optional<Sucursal> encontrarPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM sucursal WHERE nombre = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Sucursal sucursal = new Sucursal(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getString("telefono")
                );
                return Optional.of(sucursal);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new SQLException("Error al buscar sucursal por nombre: " + e.getMessage());
        }
    }

    @Override
    public List<Sucursal> obtenerTodo() {
        List<Sucursal> sucursales = new ArrayList<>();
        String query = "SELECT * FROM sucursal";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sucursal sucursal = new Sucursal(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getString("telefono")
                );
                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener sucursales: " + e.getMessage());
        }
        return sucursales;
    }

    @Override
    public void actualizar(Sucursal entidad) throws SQLException {
        String query = "UPDATE sucursal SET nombre = ?, ubicacion = ?, telefono = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getUbicacion());
            ps.setString(3, entidad.getTelefono());
            ps.setInt(4, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar sucursal: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
