package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

/**
 *
 * @author Matul
 */
public class PartidaDAO {

    private Usuario jugador;
    private int puntajeTotal;
    private int pedidosCompletados;
    private int pedidosCancelados;
    private int pedidosNoEntregados;
    private int nivel;
    private int contadorPedidos;
    private Timestamp fechaYHoraFin;
    private int tiempoPartida;
    private Partida partida;

    public void guardarPartida(Partida partida) throws SQLException {
        this.partida = partida;
        definirDatos();

        String query = "INSERT INTO partida (id_usuario, id_sucursal, puntajeTotal, pedidosCompletados, pedidosCancelados, pedidosNoEntregados, nivel, contadorPedidos, fechaYHoraFin, tiempoPartida) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, jugador.getIdUsuario());
            ps.setInt(2, jugador.getSucursal().getId());
            ps.setInt(3, puntajeTotal);
            ps.setInt(4, pedidosCompletados);
            ps.setInt(5, pedidosCancelados);
            ps.setInt(6, pedidosNoEntregados);
            ps.setInt(7, nivel);
            ps.setInt(8, contadorPedidos);
            ps.setTimestamp(9, fechaYHoraFin);
            ps.setInt(10, tiempoPartida);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Error al insertar partida: " + e.getMessage());
        }
    }

    public List<Partida> obtenerPartidasPorUsuario(int idUsuario) throws SQLException {
        return null;
    }

    public List<Partida> obtenerPartidasPorSucursal(int idSucursal) throws SQLException {
        return null;
    }

    public List<Partida> obtenerTodasLasPartidas(Timestamp fechaInicio, Timestamp fechaFin) throws SQLException {
        return null;
    }

    private void definirDatos() {
        this.jugador = partida.getJugador();
        this.puntajeTotal = partida.getPuntajeTotal();
        this.pedidosCompletados = partida.getPedidosCompletados();
        this.pedidosCancelados = partida.getPedidosCancelados();
        this.pedidosNoEntregados = partida.getPedidosNoEntregados();
        this.nivel = partida.getNivel();
        this.contadorPedidos = partida.getContadorPedidos();
        this.fechaYHoraFin = partida.getFechaYHoraFin();
        this.tiempoPartida = partida.getTiempoPartida();
    }
}