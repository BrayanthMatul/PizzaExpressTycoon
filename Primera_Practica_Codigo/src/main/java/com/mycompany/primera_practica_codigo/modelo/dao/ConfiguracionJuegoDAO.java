/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.modelo.entidades.ConfiguracionJuego;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

/**
 *
 * @author Matul
 */
public class ConfiguracionJuegoDAO {

    private int segundosPorPedidosNivel1;
    private int segundosPorPedidosNivel2;
    private int segundosPorPedidosNivel3;
    private int maximoDePedidosActivos;
    private int duracionTurno;
    private int puntosParaSubirNivel2;
    private int puntosParaSubirNivel3;
    private ConfiguracionJuego configuracion;

    public void actualizarConfiguracion(ConfiguracionJuego configuracion) throws SQLException {
        this.configuracion = configuracion;
        definirVariables();
        actualizarSegundosPorPedidosNivel1();
        actualizarSegundosPorPedidosNivel2();
        actualizarSegundosPorPedidosNivel3();
        actualizarMaximoDePedidosActivos();
        actualizarDuracionTurno();
        actualizarPuntosParaSubirNivel2();
        actualizarPuntosParaSubirNivel3();
    }

    public ConfiguracionJuego obtenerConfiguracion() throws SQLException {
        ConfiguracionJuego config = new ConfiguracionJuego();
        String query = "SELECT * FROM configuracion_juego";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int valor = rs.getInt("valor");
                switch (id) {
                    case 1:
                        config.setSegundosPorPedidosNivel1(valor);
                        break;
                    case 2:
                        config.setSegundosPorPedidosNivel2(valor);
                        break;
                    case 3:
                        config.setSegundosPorPedidosNivel3(valor);
                        break;
                    case 4:
                        config.setMaximoDePedidosActivos(valor);
                        break;
                    case 5:
                        config.setDuracionTurno(valor);
                        break;
                    case 6:
                        config.setPuntosParaSubirNivel2(valor);
                        break;
                    case 7:
                        config.setPuntosParaSubirNivel3(valor);
                        break;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener configuración: " + e.getMessage());
        }
        return config;
    }

    private void definirVariables() {
        this.segundosPorPedidosNivel1 = configuracion.getSegundosPorPedidosNivel1();
        this.segundosPorPedidosNivel2 = configuracion.getSegundosPorPedidosNivel2();
        this.segundosPorPedidosNivel3 = configuracion.getSegundosPorPedidosNivel3();
        this.maximoDePedidosActivos = configuracion.getMaximoDePedidosActivos();
        this.duracionTurno = configuracion.getDuracionTurno();
        this.puntosParaSubirNivel2 = configuracion.getPuntosParaSubirNivel2();
        this.puntosParaSubirNivel3 = configuracion.getPuntosParaSubirNivel3();

    }

    private void actualizarSegundosPorPedidosNivel1() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 1";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, segundosPorPedidosNivel1);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar segundos_por_pedidos_nivel1: " + e.getMessage());
        }

    }

    private void actualizarSegundosPorPedidosNivel2() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 2";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, segundosPorPedidosNivel2);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar segundos_por_pedidos_nivel2: " + e.getMessage());
        }
    }

    private void actualizarSegundosPorPedidosNivel3() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 3";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, segundosPorPedidosNivel3);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar segundos_por_pedidos_nivel3: " + e.getMessage());
        }
    }

    private void actualizarMaximoDePedidosActivos() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 4";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, maximoDePedidosActivos);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar maximo_de_pedidos_activos: " + e.getMessage());
        }
    }

    private void actualizarDuracionTurno() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 5";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, duracionTurno);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar duracion_turno: " + e.getMessage());
        }
    }

    private void actualizarPuntosParaSubirNivel2() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 6";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, puntosParaSubirNivel2);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar puntos_para_subir_nivel2: " + e.getMessage());
        }
    }

    private void actualizarPuntosParaSubirNivel3() throws SQLException {
        String query = "UPDATE configuracion_juego SET valor = ? WHERE id = 7";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, puntosParaSubirNivel3);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar puntos_para_subir_nivel3: " + e.getMessage());
        }
    }

}
