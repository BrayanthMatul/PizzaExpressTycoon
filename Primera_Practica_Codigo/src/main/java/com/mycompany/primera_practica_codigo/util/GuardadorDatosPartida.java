package com.mycompany.primera_practica_codigo.util;

import java.sql.SQLException;
import java.util.List;

import com.mycompany.primera_practica_codigo.modelo.dao.HistorialEstadosDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.JugadorDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.PartidaDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.PedidoDAO;
import com.mycompany.primera_practica_codigo.modelo.entidades.Jugador;
import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;
import com.mycompany.primera_practica_codigo.modelo.entidades.Pedido;

public class GuardadorDatosPartida {

    private Partida partida;
    private int idPartida;

    public GuardadorDatosPartida(Partida partida) {
        this.partida = partida;
        this.idPartida = 0;
    }

    public void guardarDatosPartida() throws SQLException {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.guardarPartida(partida);
        idPartida = partidaDAO.obtenerIdUltimaPartidaAgregada();
        guardarDatosPedidos();
        guardarDatosJugador();
    }

    private void guardarDatosPedidos() throws SQLException {
        List<Pedido> pedidos = partida.getPedidos();
        PedidoDAO pedidoDAO = new PedidoDAO();
        HistorialEstadosDAO historialDAO = new HistorialEstadosDAO();

        for (Pedido pedido : pedidos) {
            pedidoDAO.guardarDetallesPedido(pedido, idPartida);

            int idDetallePedido = pedidoDAO.obtenerIdUltimoPedidoAgregado();
            pedido.setIdPedido(idDetallePedido);

            List<HistorialEstado> historialEstados = pedido.getHistorialEstados();
            for (HistorialEstado estado : historialEstados) {
                historialDAO.guardarHistorialEstado(idDetallePedido, estado);
            }
        }
    }

    private void guardarDatosJugador() throws SQLException {
        JugadorDAO jugadorDAO = new JugadorDAO();
        Jugador jugador = null;
        boolean jugadorExiste = false;

        jugadorExiste = jugadorDAO.JugadorExiste(partida.getJugador().getIdUsuario());
        if (jugadorExiste) {
            jugador = jugadorDAO.obtenerJugadorPorId(partida.getJugador().getIdUsuario());
            actualizarJugadorExistente(jugador);
        } else {
            jugador = new Jugador();
            jugador.setIdUsuario(partida.getJugador().getIdUsuario());
            jugador.setPartidasJugadas(0);
            jugador.setNivelMaximoAlcanzado(0);
            jugador.setPuntajeMaximo(0);
            jugador.setPuntajeTotalAcumulado(0);
            guardarNuevoJugador(jugador);
        }
    }

    private void guardarNuevoJugador(Jugador jugador) throws SQLException {
        JugadorDAO jugadorDAO = new JugadorDAO();
        jugador.setPartidasJugadas(1);
        jugador.setNivelMaximoAlcanzado(partida.getNivel());
        jugador.setPuntajeMaximo(partida.getPuntajeTotal());
        jugador.setPuntajeTotalAcumulado(partida.getPuntajeTotal());

        jugadorDAO.guardarJugador(jugador);
    }

    private void actualizarJugadorExistente(Jugador jugador) throws SQLException {
        int nuevoPartidasJugadas = jugador.getPartidasJugadas() + 1;
        int nuevoNivelMaximo = Math.max(jugador.getNivelMaximoAlcanzado(), partida.getNivel());
        int nuevoPuntajeMaximo = Math.max(jugador.getPuntajeMaximo(), partida.getPuntajeTotal());
        int nuevoPuntajeTotalAcumulado = jugador.getPuntajeTotalAcumulado() + partida.getPuntajeTotal();

        jugador.setPartidasJugadas(nuevoPartidasJugadas);
        jugador.setNivelMaximoAlcanzado(nuevoNivelMaximo);
        jugador.setPuntajeMaximo(nuevoPuntajeMaximo);
        jugador.setPuntajeTotalAcumulado(nuevoPuntajeTotalAcumulado);

        JugadorDAO jugadorDAO = new JugadorDAO();
        jugadorDAO.actualizarJugador(jugador);
    }
}