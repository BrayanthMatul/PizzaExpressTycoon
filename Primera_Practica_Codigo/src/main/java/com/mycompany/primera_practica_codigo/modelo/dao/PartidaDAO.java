package com.mycompany.primera_practica_codigo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.BDCRUD;
import com.mycompany.primera_practica_codigo.util.ConexionBD;

/**
 *
 * @author Matul
 */
public class PartidaDAO extends BDCRUD<Partida, Integer> {

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

    @Override
    public void insertar(Partida partida) throws SQLException {
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

    @Override
    public Optional<Partida> encontrarPorID(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encontrarPorID'");
    }

    @Override
    public Optional<Partida> encontrarPorNombre(String nombre) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encontrarPorNombre'");
    }

    @Override
    public List<Partida> obtenerTodo() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodo'");
    }

    @Override
    public void actualizar(Partida entidad) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    public int obtenerIdUltimaPartidaAgregada() throws SQLException {
        String query = "SELECT MAX(id) AS idPartida FROM partida";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idPartida");
            } else {
                throw new SQLException("No se pudo obtener el ID de la última partida agregada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el ID de la última partida agregada: " + e.getMessage());
        }
    }

    public List<Partida> obtenerPartidasPorUsuario(int idUsuario) throws SQLException {
        return null;
    }

    public List<Partida> obtenerPartidasPorSucursal(int idSucursal) throws SQLException {
        List<Partida> partidas = obtenerTodasLasPartidas();
        return filtrarPartidasPorSucursal(partidas, idSucursal);
    }

    public List<Partida> obtenerTodasLasPartidas() throws SQLException {
        String query = "SELECT * FROM partida";
        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<Partida> partidas = new ArrayList<>();
            while (rs.next()) {
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("id"));
                partida.setIdUsuario(rs.getInt("id_usuario"));
                partida.setIdSucursal(rs.getInt("id_sucursal"));
                partida.setPuntajeTotal(rs.getInt("puntajeTotal"));
                partida.setPedidosEntregados(rs.getInt("pedidosCompletados"));
                partida.setPedidosCancelados(rs.getInt("pedidosCancelados"));
                partida.setPedidosNoEntregados(rs.getInt("pedidosNoEntregados"));
                partida.setNivel(rs.getInt("nivel"));
                partida.setContadorPedidos(rs.getInt("contadorPedidos"));
                partida.setFechaYHoraFin(rs.getTimestamp("fechaYHoraFin"));
                partida.setTiempoPartida(rs.getInt("tiempoPartida"));
                partidas.add(partida);
            }
            return partidas;
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todas las partidas: " + e.getMessage());
        }
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

    private List<Partida> filtrarPartidasPorSucursal(List<Partida> partidas, int idSucursal) {
        for (int i = partidas.size() - 1; i >= 0; i--) {
            if (partidas.get(i).getIdSucursal() != idSucursal) {
                partidas.remove(i);
            }
        }
        return partidas;
    }
}