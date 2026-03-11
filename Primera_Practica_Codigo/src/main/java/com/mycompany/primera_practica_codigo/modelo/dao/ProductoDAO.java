/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_practica_codigo.modelo.dao;

import com.mycompany.primera_practica_codigo.modelo.entidades.Producto;
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

/**
 *
 * @author Matul
 */
public class ProductoDAO extends BDCRUD<Producto, Integer> {

    @Override
    public void insertar(Producto entidad) throws SQLException {
        int idSucursal = entidad.getSucursal().getId();
        String noombreProducto = entidad.getNombre();
        String query = "INSERT INTO producto (nombre, sucursal_id) VALUES (?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, noombreProducto);
            ps.setInt(2, idSucursal);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar producto: " + e.getMessage());
        }
    }

    @Override
    public Optional<Producto> encontrarPorID(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Producto> encontrarPorNombre(String nombre) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Optional<Producto> encontrarPorNombreYIdSucursal(String nombre, Integer idSucursal) throws SQLException {
        String query = "SELECT * FROM producto WHERE nombre = ? AND sucursal_id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setInt(2, idSucursal);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        null, // No necesario cargar la sucursal completa aquí
                        rs.getBoolean("activo"));
                return Optional.of(producto);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SQLException("Error al encontrar producto por nombre y sucursal: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> obtenerTodo() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Producto> obtenerTodoPorSucursal(Integer idSucursal) throws SQLException {
        SucursalDAO sucursalDAO = new SucursalDAO();
        Optional<Sucursal> sucursal = sucursalDAO.encontrarPorID(idSucursal);
        String query = "SELECT * FROM producto WHERE sucursal_id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        sucursal.orElse(null),
                        rs.getBoolean("activo"));
                productos.add(producto);
            }
            return productos;
        } catch (SQLException e) {
            throw new SQLException("Error al obtener productos por sucursal: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Producto entidad) throws SQLException {
        String query = "UPDATE producto SET nombre = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, entidad.getNombre());
            ps.setInt(2, entidad.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar producto: " + e.getMessage());
        }
    }

    public void activarDesactivarProducto(Integer id, boolean activar) throws SQLException {
        String query = "UPDATE producto SET activo = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBoolean(1, activar);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al activar/desactivar producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
