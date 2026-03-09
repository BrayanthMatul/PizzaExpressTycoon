/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.primera_practica_codigo.vista.juego;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JPanel;

import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;
import com.mycompany.primera_practica_codigo.modelo.entidades.Pedido;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.Actualizable;
import com.mycompany.primera_practica_codigo.util.Temporizable;
import com.mycompany.primera_practica_codigo.util.Temporizador;
import com.mycompany.primera_practica_codigo.vista.MensajeErrorFrame;

/**
 *
 * @author Matul
 */
public class FrameJuego extends javax.swing.JFrame implements Temporizable, Actualizable {

    private Temporizador temporizador;
    private Usuario usuario;
    private int tiempoPartida;
    private int segundosInciales;
    private int minutosIniciales;
    private Partida partida;
    private int panelsVaciosDisponibles;

    /**
     * Creates new form FrameJuego
     */
    public FrameJuego(Usuario usuario, Partida partida) {
        initComponents();
        setLocationRelativeTo(null);
        this.usuario = usuario;
        this.partida = partida;
        configurarDatosIniciales();
        inicializarPanelesVacios();
        temporizador = new Temporizador(minutosIniciales, segundosInciales);
        temporizador.setTemporizable(this);
        temporizador.start();
    }

    @Override
    public void cambioDeTiempo(int segundos, int minutos) {
        String tiempoFormateado = String.format("%02d : %02d", minutos, segundos);
        jLabelTiempo.setText(tiempoFormateado);
        if (temporizador.isTiempoAgotado()) {
            partida.setPartidaActiva(false);
            jLabelTiempo.setText("Tiempo Agotado");

            String mensajeError = "¡Tiempo agotado! La partida ha terminado.";
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(this, true, mensajeError);
            this.dispose();
            FrameInicioJugador frameInicioJugador = new FrameInicioJugador(usuario);
            frameInicioJugador.setVisible(true);
        }
    }

    private void inicializarPanelesVacios() {
        jPanelPedidos.removeAll();
        panelsVaciosDisponibles = partida.getMaximoPedidosActivos();

        for (int i = 0; i < panelsVaciosDisponibles; i++) {
            JPanel panelVacio = crearPanelVacio();
            jPanelPedidos.add(panelVacio);
        }

        jPanelPedidos.revalidate();
        jPanelPedidos.repaint();
    }

    private JPanel crearPanelVacio() {
        JPanel panelVacio = new JPanel();
        panelVacio.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));
        panelVacio.setBackground(new java.awt.Color(240, 240, 240));
        return panelVacio;
    }

    private void eliminarTodosPanelesVacios() {
        Component[] componentes = jPanelPedidos.getComponents();

        // Eliminar todos los paneles vacíos
        for (int i = componentes.length - 1; i >= 0; i--) {
            if (!(componentes[i] instanceof JPanelPedido)) {
                jPanelPedidos.remove(i);
            }
        }
    }

    private void agregarPanelesVacios(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            JPanel panelVacio = crearPanelVacio();
            jPanelPedidos.add(panelVacio);
        }
    }

    private void configurarDatosIniciales() {

        try {
            this.partida.definirconfiguracionPartida();
            this.partida.crearListaDeProductos();
        } catch (SQLException e) {
            String mensajeError = "Error al cargar la configuración del juego: " + e.getMessage();
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensajeError);
        }

        tiempoPartida = partida.tiempoPartida();
        minutosIniciales = tiempoPartida / 60;
        segundosInciales = tiempoPartida % 60;

        System.out.println("Tiempo de partida: " + minutosIniciales + " minutos y " + segundosInciales + " segundos.");

        jLabelNombreJugador.setText(usuario.getNombreUsuario());
        jLabelNivelSiguiente.setText(String.format("Siguiente %d / %d PTS", 0,
                partida.getPuntosParaSubirNivel2()));
        jLabelPuntaje.setText("0 puntos");
        jLabelPedidosTitle.setText("Pedidos (Max: " + partida.getMaximoPedidosActivos() + ")");

    }

    @Override
    public void subirNivel(int nivel) {
        jLabelNivel.setText("Nivel : " + nivel);
    }

    @Override
    public void actualizarPuntos(int puntos) {
        jLabelPuntaje.setText(puntos + " puntos");
    }

    @Override
    public void actualizarPuntosParaSubirNivel(int puntosAcumulados, int puntosParaSubirNivel) {
        jLabelNivelSiguiente.setText(String.format("Siguiente %d / %d PTS", puntosAcumulados, puntosParaSubirNivel));
    }

    @Override
    public void agregarPedido(Pedido pedido) {
        eliminarTodosPanelesVacios();

        // Agregar el nuevo pedido
        JPanelPedido nuevoPedido = new JPanelPedido(pedido);
        jPanelPedidos.add(nuevoPedido);

        panelsVaciosDisponibles--;
        agregarPanelesVacios(panelsVaciosDisponibles);

        jPanelPedidos.revalidate();
        jPanelPedidos.repaint();
    }

    @Override
    public void eliminarPedido(int numeroPedido) {
        Component[] componentes = jPanelPedidos.getComponents();

        for (int i = 0; i < componentes.length; i++) {
            if (componentes[i] instanceof JPanelPedido) {
                JPanelPedido panelPedido = (JPanelPedido) componentes[i];
                if (panelPedido.getNumeroOrden() == numeroPedido) {
                    jPanelPedidos.remove(i);

                    eliminarTodosPanelesVacios();

                    panelsVaciosDisponibles++;
                    agregarPanelesVacios(panelsVaciosDisponibles);

                    break;
                }
            }
        }

        jPanelPedidos.revalidate();
        jPanelPedidos.repaint();
    }

    @Override
    public void ocultarPuntosParaSubirNivel() {
        jLabelNivelSiguiente.setText("Nivel maximo alcanzado");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelDatos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabelJugador = new javax.swing.JLabel();
        jLabelNombreJugador = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNivel = new javax.swing.JLabel();
        jLabelNivelSiguiente = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelPuntajeTitle = new javax.swing.JLabel();
        jLabelPuntaje = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTiempoTitle = new javax.swing.JLabel();
        jLabelTiempo = new javax.swing.JLabel();
        jPanelEspacio = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelPedidosTitle = new javax.swing.JLabel();
        jPanelPedidos = new javax.swing.JPanel();
        jPanelOpcionesSalida = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanelDatos.setLayout(new java.awt.GridLayout(1, 0, 25, 0));

        jPanel6.setLayout(new java.awt.GridLayout(0, 1));
        jPanel6.add(jLabel4);

        jLabelJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador.setText("Jugador");
        jPanel6.add(jLabelJugador);

        jLabelNombreJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNombreJugador.setText("Nombre Jugador");
        jPanel6.add(jLabelNombreJugador);

        jPanelDatos.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(0, 1));
        jPanel7.add(jLabel5);

        jLabelNivel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNivel.setText("Nivel : 1");
        jPanel7.add(jLabelNivel);

        jLabelNivelSiguiente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNivelSiguiente.setText("Siguiente 10 / 100 EXP");
        jPanel7.add(jLabelNivelSiguiente);

        jPanelDatos.add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(0, 1));
        jPanel8.add(jLabel6);

        jLabelPuntajeTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPuntajeTitle.setText("Puntaje");
        jPanel8.add(jLabelPuntajeTitle);

        jLabelPuntaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPuntaje.setText("0 puntos");
        jPanel8.add(jLabelPuntaje);

        jPanelDatos.add(jPanel8);

        jPanel9.setLayout(new java.awt.GridLayout(0, 1));
        jPanel9.add(jLabel7);

        jLabelTiempoTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTiempoTitle.setText("Tiempo restante de turno");
        jPanel9.add(jLabelTiempoTitle);

        jLabelTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTiempo.setText(" :  ");
        jPanel9.add(jLabelTiempo);

        jPanelDatos.add(jPanel9);

        jPanel1.add(jPanelDatos);

        jPanelEspacio.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(0, 1));

        jLabelPedidosTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPedidosTitle.setText("Pedidos");
        jPanel2.add(jLabelPedidosTitle);

        jPanelEspacio.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanelEspacio);

        jPanelPedidos.setLayout(new java.awt.GridLayout(2, 4, 10, 10));
        jPanel1.add(jPanelPedidos);

        jPanelOpcionesSalida.setLayout(new java.awt.GridLayout(0, 1));
        jPanelOpcionesSalida.add(jLabel10);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));
        jPanel3.add(jLabel8);

        jButton1.setText("Regresar");
        jPanel3.add(jButton1);
        jPanel3.add(jLabel9);

        jPanelOpcionesSalida.add(jPanel3);
        jPanelOpcionesSalida.add(jLabel11);

        jPanel1.add(jPanelOpcionesSalida);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 1160, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelJugador;
    private javax.swing.JLabel jLabelNivel;
    private javax.swing.JLabel jLabelNivelSiguiente;
    private javax.swing.JLabel jLabelNombreJugador;
    private javax.swing.JLabel jLabelPedidosTitle;
    private javax.swing.JLabel jLabelPuntaje;
    private javax.swing.JLabel jLabelPuntajeTitle;
    private javax.swing.JLabel jLabelTiempo;
    private javax.swing.JLabel jLabelTiempoTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDatos;
    private javax.swing.JPanel jPanelEspacio;
    private javax.swing.JPanel jPanelOpcionesSalida;
    private javax.swing.JPanel jPanelPedidos;
    // End of variables declaration//GEN-END:variables

}
