/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.primera_practica_codigo.vista.superadmin.gestion_usuarios;

import java.sql.SQLException;
import java.util.List;

import com.mycompany.primera_practica_codigo.exceptions.TextoVacioException;
import com.mycompany.primera_practica_codigo.modelo.dao.SucursalDAO;
import com.mycompany.primera_practica_codigo.modelo.dao.UsuarioDAO;
import com.mycompany.primera_practica_codigo.modelo.entidades.Rol;
import com.mycompany.primera_practica_codigo.modelo.entidades.Sucursal;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;
import com.mycompany.primera_practica_codigo.util.RecolectorDeDatos;
import com.mycompany.primera_practica_codigo.vista.MensajeErrorFrame;
import com.mycompany.primera_practica_codigo.vista.MensajeExitoFrame;
import com.mycompany.primera_practica_codigo.vista.superadmin.FrameInicioSuperAdministrador;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

/**
 *
 * @author Matul
 */
public class FrameEditorUsuario extends javax.swing.JFrame {

    private RecolectorDeDatos recolector;
    private boolean errorEnRecolector;
    private boolean errorEnValidacion;
    private String nombreUsuario;
    private String password;
    private Rol rol;
    private Sucursal sucursal;
    private boolean esEdicion;
    private int idUsuario;

    /**
     * Creates new form FrameEditorUsuario
     */
    public FrameEditorUsuario() {
        initComponents();
        setLocationRelativeTo(null);

        this.errorEnRecolector = false;
        this.errorEnValidacion = false;
        this.esEdicion = false;

        jLabelTitle.setText("Crear nuevo usuario");
        jButtonCrear.setText("Crear");
        cargarRoles();
        cargarSucursales();
    }

    public FrameEditorUsuario(Usuario usuario) {

        initComponents();
        setLocationRelativeTo(null);

        this.errorEnRecolector = false;
        this.errorEnValidacion = false;
        this.esEdicion = true;
        this.sucursal = usuario.getSucursal();
        this.idUsuario = usuario.getIdUsuario();

        jLabelTitle.setText("Editar usuario");
        jButtonCrear.setText("Actualizar");

        jTextFieldNombreUsuario.setText(usuario.getNombreUsuario());
        jTextFieldPassword.setText(usuario.getPassword());

        cargarRoles();
        cargarSucursales();

        jComboBoxSucursales.setSelectedItem(usuario.getSucursal().getNombre());
        jComboBoxRol.setSelectedItem(usuario.getRol().toString());
    }

    private void cargarRoles() {
        // limpiar el combo box
        jComboBoxRol.removeAllItems();
        // cargar los roles disponibles
        jComboBoxRol.addItem("Administrador Sucursal");
        jComboBoxRol.addItem("Jugador");
    }

    private void cargarSucursales() {
        // limpiar el combo box
        jComboBoxSucursales.removeAllItems();

        SucursalDAO sucursalDAO = new SucursalDAO();
        List<Sucursal> sucursales = null;
        try {
            sucursales = sucursalDAO.obtenerTodo();
        } catch (SQLException e) {
            String mensajeError = "Error al cargar sucursales: " + e.getMessage();
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensajeError);
        }
        for (Sucursal sucursal : sucursales) {
            jComboBoxSucursales.addItem(sucursal.getNombre());
        }
    }

    private void recolectarDatosUsuario() {
        this.errorEnRecolector = false;
        recolector = new RecolectorDeDatos();
        String mensajeError = "";

        try {
            this.nombreUsuario = recolector.recolectarTexto(jTextFieldNombreUsuario);
        } catch (TextoVacioException e) {
            mensajeError = "El campo nombre de usuario no puede estar vacio.";
            mostrarMensajeErrorRecolector(mensajeError);
        }

        try {
            this.password = recolector.recolectarTexto(jTextFieldPassword);
        } catch (TextoVacioException e) {
            mensajeError = "El campo contraseña no puede estar vacio.";
            mostrarMensajeErrorRecolector(mensajeError);
        }

        this.rol = convertirStringARol((String) jComboBoxRol.getSelectedItem());
        this.sucursal = convertirStringASucursal((String) jComboBoxSucursales.getSelectedItem());

    }

    private Rol convertirStringARol(String rolString) {
        if (rolString.equals("Administrador Sucursal")) {
            return Rol.ADMINISTRADOR_SUCURSAL;
        } else if (rolString.equals("Jugador")) {
            return Rol.JUGADOR;
        } else {
            return null;
        }
    }

    private Sucursal convertirStringASucursal(String sucursalString) {
        SucursalDAO sucursalDAO = new SucursalDAO();
        List<Sucursal> sucursales;
        try {
            sucursales = sucursalDAO.obtenerTodo();
        } catch (SQLException e) {
            String mensajeError = "Error al cargar sucursales para conversión: " + e.getMessage();
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensajeError);
            return null;
        }
        for (Sucursal sucursal : sucursales) {
            if (sucursal.getNombre().equals(sucursalString)) {
                return sucursal;
            }
        }
        return null;
    }

    private void validarDatosUsuario() {
        this.errorEnValidacion = false;
        String mensajeError = "";

        if (nombreUsuario.length() < 4) {
            mensajeError = "El nombre de usuario debe tener al menos 4 caracteres.";
            mostrarMensajeErrorValidador(mensajeError);
        }

        if (password.length() < 6) {
            mensajeError = "La contraseña debe tener al menos 6 caracteres.";
            mostrarMensajeErrorValidador(mensajeError);
        }
    }

    private void limpiarCampos() {
        jTextFieldNombreUsuario.setText("");
        jTextFieldPassword.setText("");
        jComboBoxRol.setSelectedIndex(0);
        jComboBoxSucursales.setSelectedIndex(0);
    }

    private void mostrarMensajeErrorRecolector(String mensaje) {
        MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensaje);
        errorEnRecolector = true;
    }

    private void mostrarMensajeErrorValidador(String mensaje) {
        MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensaje);
        errorEnValidacion = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabelNombreSucursal = new javax.swing.JLabel();
        jTextFieldNombreUsuario = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabelNombrePassword = new javax.swing.JLabel();
        jTextFieldPassword = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabelRol = new javax.swing.JLabel();
        jComboBoxRol = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabelSucursal = new javax.swing.JLabel();
        jComboBoxSucursales = new javax.swing.JComboBox<>();
        jButtonCrear = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButtonRegresar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jLabelTitle.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabelTitle.setText("Crear Nueva Usuario");
        jPanel2.add(jLabelTitle);

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 380, Short.MAX_VALUE));

        jPanel3.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(0, 1, 0, 30));

        jPanel9.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNombreSucursal.setText("Nombre Usuario");
        jPanel9.add(jLabelNombreSucursal);
        jPanel9.add(jTextFieldNombreUsuario);

        jPanel5.add(jPanel9);

        jPanel12.setLayout(new java.awt.GridLayout(0, 1));

        jLabelNombrePassword.setText("Contraseña");
        jPanel12.add(jLabelNombrePassword);
        jPanel12.add(jTextFieldPassword);

        jPanel5.add(jPanel12);

        jPanel10.setLayout(new java.awt.GridLayout(0, 1));

        jLabelRol.setText("Rol");
        jPanel10.add(jLabelRol);

        jComboBoxRol
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador Sucursal", "Jugador" }));
        jPanel10.add(jComboBoxRol);

        jPanel5.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(0, 1));

        jLabelSucursal.setText("Sucursal");
        jPanel11.add(jLabelSucursal);

        jComboBoxSucursales.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel11.add(jComboBoxSucursales);

        jPanel5.add(jPanel11);

        jButtonCrear.setText("Crear");
        jButtonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearActionPerformed(evt);
            }
        });
        jPanel5.add(jButtonCrear);

        jPanel3.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 380, Short.MAX_VALUE));

        jPanel3.add(jPanel6);

        jPanel1.add(jPanel3);

        jPanel17.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel19Layout.setVerticalGroup(
                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 72, Short.MAX_VALUE));

        jPanel17.add(jPanel19);

        jPanel20.setLayout(new java.awt.GridLayout(0, 1));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 24, Short.MAX_VALUE));

        jPanel20.add(jPanel7);

        jButtonRegresar.setText("Regresar");
        jButtonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarActionPerformed(evt);
            }
        });
        jPanel20.add(jButtonRegresar);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 24, Short.MAX_VALUE));

        jPanel20.add(jPanel8);

        jPanel17.add(jPanel20);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 177, Short.MAX_VALUE));
        jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 72, Short.MAX_VALUE));

        jPanel17.add(jPanel18);

        jPanel1.add(jPanel17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCrearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonCrearActionPerformed
        recolectarDatosUsuario();

        if (!errorEnRecolector) {
            if (!errorEnValidacion) {
                if (!esEdicion) {
                    guardarUsuario();
                } else {
                    actualizarUsuario();
                }
            }
        }
    }// GEN-LAST:event_jButtonCrearActionPerformed

    private void guardarUsuario() {
        Usuario nuevoUsuario = new Usuario(nombreUsuario, password, rol, sucursal);
        UsuarioDAO registradorUsuario = new UsuarioDAO();

        try {
            registradorUsuario.insertar(nuevoUsuario);
            String mensaje = "Usuario creado exitosamente.";
            MensajeExitoFrame mensajeExitoFrame = new MensajeExitoFrame(null, true, mensaje);
            limpiarCampos();
        } catch (SQLException e) {
            String mensaje = "Error al crear el usuario: " + e.getMessage();
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensaje);
        }
    }

    private void actualizarUsuario() {
        Usuario usuarioActualizado = new Usuario(idUsuario, nombreUsuario, password, rol, sucursal);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            usuarioDAO.actualizar(usuarioActualizado);
            String mensaje = "Usuario actualizado exitosamente.";
            MensajeExitoFrame mensajeExitoFrame = new MensajeExitoFrame(null, true, mensaje);
            FrameInicioSuperAdministrador frameSuperAdmin = new FrameInicioSuperAdministrador();
            frameSuperAdmin.setVisible(true);
            this.dispose();
        } catch (SQLException e) {
            String mensaje = "Error al actualizar el usuario: " + e.getMessage();
            MensajeErrorFrame mensajeErrorFrame = new MensajeErrorFrame(null, true, mensaje);
        }
    }

    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRegresarActionPerformed
        this.dispose();
        FrameInicioSuperAdministrador frameSuperAdmin = new FrameInicioSuperAdministrador();
        frameSuperAdmin.setVisible(true);
    }// GEN-LAST:event_jButtonRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCrear;
    private javax.swing.JButton jButtonRegresar;
    private javax.swing.JComboBox<String> jComboBoxRol;
    private javax.swing.JComboBox<String> jComboBoxSucursales;
    private javax.swing.JLabel jLabelNombrePassword;
    private javax.swing.JLabel jLabelNombreSucursal;
    private javax.swing.JLabel jLabelRol;
    private javax.swing.JLabel jLabelSucursal;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextFieldNombreUsuario;
    private javax.swing.JTextField jTextFieldPassword;
    // End of variables declaration//GEN-END:variables
}
