package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mycompany.primera_practica_codigo.modelo.entidades.Jugador;
import com.mycompany.primera_practica_codigo.util.BDCRUD;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

public class JugadorDAO extends BDCRUD<Jugador, Integer> {

    @Override
    public void insertar(Jugador jugador) throws SQLException {
        String query = "INSERT INTO jugador (id_usuario, partidasJugadas, nivelMaximoAlcanzado, puntajeMaximo, puntajeTotalAcumulado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, jugador.getIdUsuario());
            ps.setInt(2, 1); // Inicialmente, el jugador no ha jugado ninguna partida
            ps.setInt(3, jugador.getNivelMaximoAlcanzado());
            ps.setInt(4, jugador.getPuntajeMaximo());
            ps.setInt(5, jugador.getPuntajeTotalAcumulado());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al insertar el jugador: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Jugador> encontrarPorID(Integer id) throws SQLException {
        String query = "SELECT * FROM jugador WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setIdUsuario(rs.getInt("id_usuario"));
                jugador.setPartidasJugadas(rs.getInt("partidasJugadas"));
                jugador.setNivelMaximoAlcanzado(rs.getInt("nivelMaximoAlcanzado"));
                jugador.setPuntajeMaximo(rs.getInt("puntajeMaximo"));
                jugador.setPuntajeTotalAcumulado(rs.getInt("puntajeTotalAcumulado"));
                return Optional.of(jugador);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al encontrar jugador por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Jugador> encontrarPorNombre(String nombre) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encontrarPorNombre'");
    }

    @Override
    public List<Jugador> obtenerTodo() throws SQLException {
        String query = "SELECT * FROM jugador";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            List<Jugador> jugadores = new ArrayList<>();
            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setIdUsuario(rs.getInt("id_usuario"));
                jugador.setPartidasJugadas(rs.getInt("partidasJugadas"));
                jugador.setNivelMaximoAlcanzado(rs.getInt("nivelMaximoAlcanzado"));
                jugador.setPuntajeMaximo(rs.getInt("puntajeMaximo"));
                jugador.setPuntajeTotalAcumulado(rs.getInt("puntajeTotalAcumulado"));
                jugadores.add(jugador);
            }
            return jugadores;
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los jugadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Jugador jugador) throws SQLException {
        String query = "UPDATE jugador SET partidasJugadas = ?, nivelMaximoAlcanzado = ?, puntajeMaximo = ?, puntajeTotalAcumulado = ? WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, jugador.getPartidasJugadas());
            ps.setInt(2, jugador.getNivelMaximoAlcanzado());
            ps.setInt(3, jugador.getPuntajeMaximo());
            ps.setInt(4, jugador.getPuntajeTotalAcumulado());
            ps.setInt(5, jugador.getIdUsuario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el jugador: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    public boolean JugadorExiste(int idUsuario) throws SQLException {
        String query = "SELECT 1 FROM jugador WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si hay un resultado, el jugador existe
        } catch (SQLException e) {
            throw new SQLException("Error al verificar si el jugador existe: " + e.getMessage(), e);
        }
    }

    public List<Jugador> obtenerJugadoresPorSucursal(int idSucursal) throws SQLException {
        String query = " SELECT j.*, u.nombre_usuario FROM jugador j JOIN usuario u ON j.id_usuario = u.id WHERE u.sucursal_id = ?";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idSucursal);
            ResultSet rs = ps.executeQuery();
            List<Jugador> jugadores = new java.util.ArrayList<>();
            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setNombreUsuario(rs.getString("nombre_usuario"));
                jugador.setPartidasJugadas(rs.getInt("partidasJugadas"));
                jugador.setNivelMaximoAlcanzado(rs.getInt("nivelMaximoAlcanzado"));
                jugador.setPuntajeMaximo(rs.getInt("puntajeMaximo"));
                jugador.setPuntajeTotalAcumulado(rs.getInt("puntajeTotalAcumulado"));
                jugadores.add(jugador);
            }
            return jugadores;

        } catch (SQLException e) {
            throw new SQLException("Error al obtener jugadores por sucursal: " + e.getMessage());
        }
    }

    public List<Jugador> obtenerTopJugadoresPorSucursal(int idSucursal) throws SQLException {
        List<Jugador> jugadores = obtenerJugadoresPorSucursal(idSucursal);
        if (jugadores != null) {
            jugadores = ordenarJugadoresPorPuntaje(jugadores);
            return jugadores;
        }
        return null;
    }

    private List<Jugador> ordenarJugadoresPorPuntaje(List<Jugador> jugadores) {
        jugadores.sort((j1, j2) -> Integer.compare(j2.getPuntajeMaximo(), j1.getPuntajeMaximo()));
        return jugadores;
    }

}
