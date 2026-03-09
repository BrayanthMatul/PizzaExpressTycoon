package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.primera_practica_codigo.modelo.entidades.Jugador;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

public class JugadorDAO {

    public void guardarJugador(Jugador jugador) {
        String query = "INSERT INTO jugador (id_usuario, partidasJugadas, nivelMaximoAlcanzado, puntajeMaximo, puntajeTotalAcumulado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, jugador.getIdUsuario());
            stmt.setInt(2, 1); // Inicialmente, el jugador no ha jugado ninguna partida
            stmt.setInt(3, jugador.getNivelMaximoAlcanzado());
            stmt.setInt(4, jugador.getPuntajeMaximo());
            stmt.setInt(5, jugador.getPuntajeTotalAcumulado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar el jugador: " + e.getMessage());
        }
    }

    public void actualizarJugador(Jugador jugador) {
        String query = "UPDATE jugador SET partidasJugadas = ?, nivelMaximoAlcanzado = ?, puntajeMaximo = ?, puntajeTotalAcumulado = ? WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, jugador.getPartidasJugadas());
            stmt.setInt(2, jugador.getNivelMaximoAlcanzado());
            stmt.setInt(3, jugador.getPuntajeMaximo());
            stmt.setInt(4, jugador.getPuntajeTotalAcumulado());
            stmt.setInt(5, jugador.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el jugador: " + e.getMessage());
        }
    }

    public Jugador obtenerJugadorPorId(int idUsuario) {
        String query = "SELECT * FROM jugador WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jugador jugador = new Jugador();
                    jugador.setIdUsuario(rs.getInt("id_usuario"));
                    jugador.setPartidasJugadas(rs.getInt("partidasJugadas"));
                    jugador.setNivelMaximoAlcanzado(rs.getInt("nivelMaximoAlcanzado"));
                    jugador.setPuntajeMaximo(rs.getInt("puntajeMaximo"));
                    jugador.setPuntajeTotalAcumulado(rs.getInt("puntajeTotalAcumulado"));
                    return jugador;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el jugador: " + e.getMessage());
        }
        return null;
    }

    public boolean JugadorExiste(int idUsuario) throws SQLException {
        String query = "SELECT 1 FROM jugador WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si hay un resultado, el jugador existe
            }
        } catch (SQLException e) {
            throw new SQLException("Error al verificar si el jugador existe: " + e.getMessage(), e);
        }
    }
}
