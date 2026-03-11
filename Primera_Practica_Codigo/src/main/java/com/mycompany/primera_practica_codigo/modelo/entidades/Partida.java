package com.mycompany.primera_practica_codigo.modelo.entidades;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mycompany.primera_practica_codigo.modelo.dao.ConfiguracionJuegoDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.ProductoDAO;
import com.mycompany.primera_practica_codigo.util.Actualizable;
import com.mycompany.primera_practica_codigo.util.GeneradorPedidos;
import com.mycompany.primera_practica_codigo.util.GuardadorDatosPartida;
import com.mycompany.primera_practica_codigo.vista.juego.FrameJuego;

public class Partida {

    // Constantes
    private final int PUNTOS_POR_PEDIDO_ENTREGADO = 20;
    private final int PUNTOS_POR_PEDIDO_NO_ENTREGADO = 15;
    private final int PUNTOS_POR_PEDIDO_CANCELADO = 10;
    private final int PUNTOS_EXTRAS_POR_EFICIENCIA = 10;

    // Identificadores y datos de usuario
    private int idPartida;
    private int idUsuario;
    private int idSucursal;
    private String nombreJugador;
    private Usuario jugador;

    // Estado de la partida
    private boolean partidaActiva;
    private int nivel;
    private int puntajeTotal;
    private int pedidosCompletados;
    private int pedidosCancelados;
    private int pedidosNoEntregados;
    private int contadorPedidos;
    private int puntosAcumuladosNivelActual;
    private Timestamp fechaYHoraFin;
    private int tiempoPartida;

    // Configuración de la partida
    private ConfiguracionJuego configuracionJuego;
    private int puntosParaSubirNivel2;
    private int puntosParaSubirNivel3;
    private int segundosPorPedidoNivel1;
    private int segundosPorPedidoNivel2;
    private int segundosPorPedidoNivel3;
    private int maximoPedidosActivos;
    private int frecuenciaGeneracionPedidosNivel;

    // Objetos de utilidad y control
    private FrameJuego frameJuego;
    private Actualizable actualizable;
    private List<Producto> productosActivos;
    private AtomicInteger pedidosActivos;
    private GeneradorPedidos generadorPedidos;
    private List<Pedido> pedidos;
    private final Lock lockPedidos = new ReentrantLock();

    public Partida() {

    }

    public Partida(Usuario jugador) {
        this.jugador = jugador;
        this.idSucursal = jugador.getSucursal().getId();
        this.puntajeTotal = 0;
        this.nivel = 1;
        this.contadorPedidos = 0;
        this.puntosAcumuladosNivelActual = 0;
        this.pedidosCompletados = 0;
        this.pedidosCancelados = 0;
        this.pedidosNoEntregados = 0;
        this.partidaActiva = true;
        this.pedidosActivos = new AtomicInteger(0);
        this.frameJuego = new FrameJuego(jugador, this);
        actualizable = frameJuego;
        this.pedidos = new ArrayList<>();
    }

    // Getters y Setters ordenados
    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Usuario getJugador() {
        return jugador;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isPartidaActiva() {
        return partidaActiva;
    }

    public void setPartidaActiva(boolean partidaActiva) {
        this.partidaActiva = partidaActiva;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int getPedidosCompletados() {
        return pedidosCompletados;
    }

    public void setPedidosEntregados(int pedidosCompletados) {
        this.pedidosCompletados = pedidosCompletados;
    }

    public int getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(int pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }

    public int getPedidosNoEntregados() {
        return pedidosNoEntregados;
    }

    public void setPedidosNoEntregados(int pedidosNoEntregados) {
        this.pedidosNoEntregados = pedidosNoEntregados;
    }

    public int getContadorPedidos() {
        return contadorPedidos;
    }

    public void setContadorPedidos(int contadorPedidos) {
        this.contadorPedidos = contadorPedidos;
    }

    public int getPuntosAcumuladosNivelActual() {
        return puntosAcumuladosNivelActual;
    }

    public Timestamp getFechaYHoraFin() {
        return fechaYHoraFin;
    }

    public void setFechaYHoraFin(Timestamp fechaYHoraFin) {
        this.fechaYHoraFin = fechaYHoraFin;
    }

    public int getTiempoPartida() {
        return tiempoPartida;
    }

    public void setTiempoPartida(int tiempoPartida) {
        this.tiempoPartida = tiempoPartida;
    }

    public int tiempoPartida() {
        return tiempoPartida;
    }

    public int getPuntosParaSubirNivel2() {
        return puntosParaSubirNivel2;
    }

    public int getPuntosParaSubirNivel3() {
        return puntosParaSubirNivel3;
    }

    public int getSegundosPorPedidoNivel1() {
        return segundosPorPedidoNivel1;
    }

    public int getSegundosPorPedidoNivel2() {
        return segundosPorPedidoNivel2;
    }

    public int getSegundosPorPedidoNivel3() {
        return segundosPorPedidoNivel3;
    }

    public int getMaximoPedidosActivos() {
        return maximoPedidosActivos;
    }

    public int getFrecuenciaGeneracionPedidosNivel() {
        return frecuenciaGeneracionPedidosNivel;
    }

    public List<Producto> getProductosActivos() {
        return productosActivos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void iniciarPartida() {
        frameJuego.setVisible(true);
        generadorPedidos = new GeneradorPedidos(this);
        generadorPedidos.start();
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    // Métodos públicos
    public void agregarPuntos(boolean esEficiente) {
        puntajeTotal += PUNTOS_POR_PEDIDO_ENTREGADO;
        if (esEficiente) {
            puntajeTotal += PUNTOS_EXTRAS_POR_EFICIENCIA;
            puntosAcumuladosNivelActual += PUNTOS_EXTRAS_POR_EFICIENCIA;
        }
        pedidosCompletados++;
        puntosAcumuladosNivelActual += PUNTOS_POR_PEDIDO_ENTREGADO;
        actualizable.actualizarPuntos(puntajeTotal);
        if (nivel == 1) {
            actualizable.actualizarPuntosParaSubirNivel(puntosAcumuladosNivelActual, puntosParaSubirNivel2);
        } else if (nivel == 2) {
            actualizable.actualizarPuntosParaSubirNivel(puntosAcumuladosNivelActual, puntosParaSubirNivel3);
        }
        verificarSubirNivel();
    }

    public void restarPuntosPorNoEntregado() {
        if (puntajeTotal <= PUNTOS_POR_PEDIDO_NO_ENTREGADO) {
            puntajeTotal = 0;
        } else {
            puntajeTotal -= PUNTOS_POR_PEDIDO_NO_ENTREGADO;
        }
        pedidosNoEntregados++;
        actualizable.actualizarPuntos(puntajeTotal);
    }

    public void restarPuntosPorCancelado() {
        if (puntajeTotal <= PUNTOS_POR_PEDIDO_CANCELADO) {
            puntajeTotal = 0;
        } else {
            puntajeTotal -= PUNTOS_POR_PEDIDO_CANCELADO;
        }
        pedidosCancelados++;
        actualizable.actualizarPuntos(puntajeTotal);
    }

    public void eliminarPedido(FormaEliminacion formaEliminacion, Pedido pedido) {
        lockPedidos.lock();
        try {
            int pedidosActuales = pedidosActivos.get();
            int numeroPedido = pedido.getNumeroPedido();
            boolean esEficiente = pedido.esRapido();

            // No decrementar si ya está en 0 o menos
            if (pedidosActuales <= 0) {
                return; // Salir sin hacer nada
            }
            pedidosActivos.decrementAndGet();

            actualizable.eliminarPedido(numeroPedido);
            DetallePedido detalle = null;
            int puntajePorPedido = 0;

            if (formaEliminacion == FormaEliminacion.ENTREGADO) {
                agregarPuntos(esEficiente);
                puntajePorPedido = PUNTOS_POR_PEDIDO_ENTREGADO;
                if (esEficiente) {
                    puntajePorPedido += PUNTOS_EXTRAS_POR_EFICIENCIA;
                }
                pedido.setPuntosObtenidos(puntajePorPedido);
                pedidos.add(pedido);

            } else if (formaEliminacion == FormaEliminacion.NO_ENTREGADO) {
                restarPuntosPorNoEntregado();
                pedido.setPuntosObtenidos(-PUNTOS_POR_PEDIDO_NO_ENTREGADO);
                pedidos.add(pedido);
            } else {
                restarPuntosPorCancelado();
                pedido.setPuntosObtenidos(-PUNTOS_POR_PEDIDO_CANCELADO);
                pedidos.add(pedido);
            }
        } finally {
            lockPedidos.unlock();
        }
    }

    public void agregarPedido() {
        lockPedidos.lock();
        try {
            int pedidosActuales = pedidosActivos.get();

            if (partidaActiva && pedidosActuales < maximoPedidosActivos) {
                contadorPedidos++;
                Pedido nuevoPedido = crearPedido();
                pedidosActivos.incrementAndGet();

                actualizable.agregarPedido(nuevoPedido);
            }
        } finally {
            lockPedidos.unlock();
        }
    }

    public Pedido crearPedido() {
        int nuemroProductosActivos = productosActivos.size();
        int indiceProducto = (int) (Math.random() * nuemroProductosActivos);
        Producto productoPedido = productosActivos.get(indiceProducto);
        String nombrePedido = productoPedido.getNombre();
        int tiempoLimiteSeg = definirTiempoLimiteSegundosPorPedido();
        return new Pedido(nombrePedido, this, contadorPedidos, tiempoLimiteSeg);
    }

    public void crearListaDeProductos() throws SQLException {
        ProductoDAO productoDAO = new ProductoDAO();
        Integer idSucursal = jugador.getSucursal().getId();
        this.productosActivos = productoDAO.obtenerTodoPorSucursal(idSucursal);
        quitarProductosInactivos();
    }

    public void definirconfiguracionPartida() throws SQLException {
        ConfiguracionJuegoDAO configuracionDAO = new ConfiguracionJuegoDAO();
        this.configuracionJuego = configuracionDAO.obtenerConfiguracion();

        this.segundosPorPedidoNivel1 = configuracionJuego.getSegundosPorPedidosNivel1();
        this.segundosPorPedidoNivel2 = configuracionJuego.getSegundosPorPedidosNivel2();
        this.segundosPorPedidoNivel3 = configuracionJuego.getSegundosPorPedidosNivel3();
        this.maximoPedidosActivos = configuracionJuego.getMaximoDePedidosActivos();
        this.tiempoPartida = configuracionJuego.getDuracionTurno();
        this.puntosParaSubirNivel2 = configuracionJuego.getPuntosParaSubirNivel2();
        this.puntosParaSubirNivel3 = configuracionJuego.getPuntosParaSubirNivel3();
        this.frecuenciaGeneracionPedidosNivel = configuracionJuego.getTiempoFrecuenciaPedidos();

    }

    public void GuardarDatosPartida() throws SQLException {
        generadorPedidos.interrupt();

        int pedidosActivosRestantes = pedidosActivos.get();

        pedidosNoEntregados += pedidosActivosRestantes;

        for (int i = 0; i < pedidosActivosRestantes; i++) {
            if (puntajeTotal <= PUNTOS_POR_PEDIDO_NO_ENTREGADO) {
                puntajeTotal = 0;
            } else {
                puntajeTotal -= PUNTOS_POR_PEDIDO_NO_ENTREGADO;
            }
        }

        fechaYHoraFin = new Timestamp(System.currentTimeMillis());
        GuardadorDatosPartida guardador = new GuardadorDatosPartida(this);
        guardador.guardarDatosPartida();
    }

    // Métodos privados
    private void verificarSubirNivel() {
        if (nivel == 1 && puntosAcumuladosNivelActual >= puntosParaSubirNivel2) {
            incrementarNivel();
        } else if (nivel == 2 && puntosAcumuladosNivelActual >= puntosParaSubirNivel3) {
            incrementarNivel();
        }
    }

    private void incrementarNivel() {
        this.puntosAcumuladosNivelActual = 0;
        this.nivel++;
        actualizable.subirNivel(nivel);
        if (nivel == 2) {
            System.out.println("puntos para subir nivel 3: " + puntosParaSubirNivel3);
            actualizable.actualizarPuntosParaSubirNivel(puntosAcumuladosNivelActual, puntosParaSubirNivel3);
        } else if (nivel == 3) {
            actualizable.ocultarPuntosParaSubirNivel();
        }
    }

    private void quitarProductosInactivos() {
        for (Producto producto : productosActivos) {
            if (!producto.isActivo()) {
                productosActivos.remove(producto);
            }
        }
    }

    private int definirTiempoLimiteSegundosPorPedido() {
        if (nivel == 1) {
            return segundosPorPedidoNivel1;
        } else if (nivel == 2) {
            return segundosPorPedidoNivel2;
        } else {
            return segundosPorPedidoNivel3;
        }
    }

}
