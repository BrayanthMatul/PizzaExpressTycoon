/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.primera_practica_codigo.vista.administrador_sucursal;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.mycompany.primera_practica_codigo.modelo.dao.JugadorDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.PartidaDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.UsuarioDAO;
import com.mycompany.primera_practica_codigo.modelo.entidades.Jugador;
import com.mycompany.primera_practica_codigo.modelo.entidades.Partida;
import com.mycompany.primera_practica_codigo.modelo.entidades.Sucursal;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.GuardadorReporte;
import com.mycompany.primera_practica_codigo.vista.MensajeErrorFrame;

/**
 *
 * @author Matul
 */
public class FrameListaExportacion extends javax.swing.JFrame {

    private boolean esEstadisticaPartidasJugadasSucursal;
    private boolean esEstadisticaRankingJugadores;
    private Usuario usuario;
    private Sucursal sucursal;
    private List<Partida> partidas;
    private List<Jugador> jugadores;

    /**
     * Creates new form FrameListaExportacion
     */
    public FrameListaExportacion(boolean esEstadisticaPartidasJugadasSucursal, boolean esEstadisticaRankingJugadores,
            Usuario usuario) {
        initComponents();
        setLocationRelativeTo(null);
        this.esEstadisticaPartidasJugadasSucursal = esEstadisticaPartidasJugadasSucursal;
        this.esEstadisticaRankingJugadores = esEstadisticaRankingJugadores;
        this.usuario = usuario;
        this.sucursal = usuario.getSucursal();
        colocarDatos();
    }

    private void colocarDatos() {
        if (esEstadisticaPartidasJugadasSucursal) {
            jLabelTitle.setText("Partidas Jugadas en la Sucursal");
            cargarTablaPartidasJugadasSucursal();
        } else if (esEstadisticaRankingJugadores) {
            jLabelTitle.setText("Ranking de Jugadores");
            cargarTablaRankingJugadores();
        }
    }

    private void cargarTablaPartidasJugadasSucursal() {
        // limpiar tabla antes de cargar datos
        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Jugador", "Pedidos Entregados", "Pedidos Cancelados", "Pedidos No Entregados",
                        "Total pedidos Generados", "Puntaje Total", "Nivel", "Fecha y Hora Fin" }));

        PartidaDAO partidaDAO = new PartidaDAO();

        try {
            this.partidas = partidaDAO.obtenerPartidasPorSucursal(sucursal.getId());
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            for (Partida partida : this.partidas) {
                Object[] fila = new Object[8];
                int idUsuario = partida.getIdUsuario();
                Optional<Usuario> usuario = usuarioDAO.encontrarPorID(idUsuario);
                String nombreUsuario = usuario.isPresent() ? usuario.get().getNombreUsuario() : "Desconocido";
                partida.setNombreJugador(nombreUsuario);
                fila[0] = partida.getNombreJugador();
                fila[1] = partida.getPedidosCompletados();
                fila[2] = partida.getPedidosCancelados();
                fila[3] = partida.getPedidosNoEntregados();
                fila[4] = partida.getContadorPedidos();
                fila[5] = partida.getPuntajeTotal();
                fila[6] = partida.getNivel();
                fila[7] = partida.getFechaYHoraFin();

                ((javax.swing.table.DefaultTableModel) jTableDatos.getModel()).addRow(fila);
            }
        } catch (SQLException e) {
            String mensajeError = "Error al cargar partidas de la sucursal: " + e.getMessage();
            MensajeErrorFrame frameError = new MensajeErrorFrame(null, true, mensajeError);
        }

    }

    private void cargarTablaRankingJugadores() {
        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Jugador", "Puntaje Máximo", "Nivel Máximo Alcanzado", "Puntaje Total Acumulado",
                        "Partidas Jugadas" }));

        JugadorDAO jugadorDAO = new JugadorDAO();

        try {
            this.jugadores = jugadorDAO.obtenerTopJugadoresPorSucursal(sucursal.getId());
            for (Jugador jugador : this.jugadores) {
                Object[] fila = new Object[5];
                fila[0] = jugador.getNombreUsuario();
                fila[1] = jugador.getPuntajeMaximo();
                fila[2] = jugador.getNivelMaximoAlcanzado();
                fila[3] = jugador.getPuntajeTotalAcumulado();
                fila[4] = jugador.getPartidasJugadas();

                ((javax.swing.table.DefaultTableModel) jTableDatos.getModel()).addRow(fila);
            }
        } catch (SQLException e) {
            String mensajeError = "Error al cargar ranking de jugadores: " + e.getMessage();
            MensajeErrorFrame frameError = new MensajeErrorFrame(null, true, mensajeError);
        }
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButtonRegresar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabelTitle.setText("Titulo");
        jPanel5.add(jLabelTitle);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jPanel7.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jScrollPane1.setViewportView(jTableDatos);

        jPanel7.add(jScrollPane1);

        jPanel3.add(jPanel7);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        jPanel8.setLayout(new java.awt.GridLayout(1, 0));
        jPanel8.add(jLabel1);

        jButton1.setText("Exportar estadistica CSV");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton1);
        jPanel8.add(jLabel2);

        jPanel4.add(jPanel8);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 10, 20));
        jPanel10.add(jLabel12);

        jButtonRegresar.setText("Regresar");
        jButtonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonRegresar);
        jPanel10.add(jLabel11);

        jPanel4.add(jPanel10);
        jPanel4.add(jLabel10);

        jPanel1.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRegresarActionPerformed
        this.dispose();
        FrameInicioAdministradorSucursal frameInicio = new FrameInicioAdministradorSucursal(usuario);
        frameInicio.setVisible(true);
    }// GEN-LAST:event_jButtonRegresarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        GuardadorReporte guardadorReporte = new GuardadorReporte();

        try {
            if (esEstadisticaPartidasJugadasSucursal && partidas != null) {
                guardadorReporte.guardarReportePartida(partidas);
            } else if (esEstadisticaRankingJugadores && jugadores != null) {
                guardadorReporte.guardarReporteRanking(jugadores);
            }
        } catch (Exception e) {
            String mensajeError = "Error al exportar el reporte: " + e.getMessage();
            MensajeErrorFrame frameError = new MensajeErrorFrame(null, true, mensajeError);
        }

    }// GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDatos;
    // End of variables declaration//GEN-END:variables
}
