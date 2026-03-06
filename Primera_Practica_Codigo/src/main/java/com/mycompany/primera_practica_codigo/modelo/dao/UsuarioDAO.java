/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_practica_codigo.modelo.dao;

import com.mycompany.primera_practica_codigo.modelo.entidades.Rol;
import com.mycompany.primera_practica_codigo.modelo.entidades.Sucursal;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.BDCRUD;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 *
 * @author Matul
 */
public class UsuarioDAO extends BDCRUD<Usuario, Integer> {

    @Override
    public void insertar(Usuario entidad) throws SQLException {
        int idRol = obtenerIdRol(entidad.getRol());
        int idSucursal = obtenerIdSucursal(entidad.getSucursal());
        String query = "INSERT INTO usuario (nombre_usuario, password, rol_id, sucursal_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, entidad.getNombreUsuario());
            ps.setString(2, entidad.getPassword());
            ps.setInt(3, idRol);
            ps.setInt(4, idSucursal);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> encontrarPorID(Integer id) throws SQLException {
        String query = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(obtenerRolPorId(rs.getInt("rol_id")));
                usuario.setSucursal(obtenerSucursalPorId(rs.getInt("sucursal_id")));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al encontrar usuario por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> encontrarPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM usuario WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(obtenerRolPorId(rs.getInt("rol_id")));
                usuario.setSucursal(obtenerSucursalPorId(rs.getInt("sucursal_id")));
                return Optional.of(usuario);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al encontrar usuario por nombre: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> obtenerTodo() throws SQLException {
        String query = "SELECT * FROM usuario";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setPassword(rs.getString("password"));
                int rolId = rs.getInt("rol_id");
                int sucursalId = rs.getInt("sucursal_id");
                usuario.setRol(obtenerRolPorId(rolId));
                if (sucursalId != 0) {
                    usuario.setSucursal(obtenerSucursalPorId(sucursalId));
                } else {
                    usuario.setSucursal(null);
                }
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            throw new SQLException("Error al obtener usuarios: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario entidad) throws SQLException {
        String query = "UPDATE usuario SET nombre_usuario = ?, password = ?, rol_id = ?, sucursal_id = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, entidad.getNombreUsuario());
            ps.setString(2, entidad.getPassword());
            int idRol = obtenerIdRol(entidad.getRol());
            ps.setInt(3, idRol);
            int idSucursal = obtenerIdSucursal(entidad.getSucursal());
            ps.setInt(4, idSucursal);
            ps.setInt(5, entidad.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int obtenerIdRol(Rol rol) {
        switch (rol) {
            case JUGADOR:
                return 1;
            case ADMINISTRADOR_SUCURSAL:
                return 2;
            case SUPERADMINISTRADOR:
                return 3;
            default:
                throw new IllegalArgumentException("Rol desconocido: " + rol);
        }
    }

    private Rol obtenerRolPorId(int idRol) {
        switch (idRol) {
            case 1:
                return Rol.JUGADOR;
            case 2:
                return Rol.ADMINISTRADOR_SUCURSAL;
            case 3:
                return Rol.SUPERADMINISTRADOR;
            default:
                throw new IllegalArgumentException("ID de rol desconocido: " + idRol);
        }
    }

    private int obtenerIdSucursal(Sucursal sucursal) throws SQLException {
        SucursalDAO sucursalDAO = new SucursalDAO();
        // Usar encontrar por nombre para obtener el ID de la sucursal
        try {
            Optional<Sucursal> sucursalOpt = sucursalDAO.encontrarPorNombre(sucursal.getNombre());
            if (sucursalOpt.isPresent()) {
                return sucursalOpt.get().getId();
            } else {
                throw new SQLException("Sucursal no encontrada con nombre: " + sucursal.getNombre());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener ID de sucursal: " + e.getMessage(), e);
        }
    }

    private Sucursal obtenerSucursalPorId(int idSucursal) throws SQLException {
        SucursalDAO sucursalDAO = new SucursalDAO();
        try {
            Optional<Sucursal> sucursalOpt = sucursalDAO.encontrarPorID(idSucursal);
            if (sucursalOpt.isPresent()) {
                return sucursalOpt.get();
            } else {
                throw new SQLException("Sucursal no encontrada con ID: " + idSucursal);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener sucursal por ID: " + e.getMessage(), e);
        }
    }

}
