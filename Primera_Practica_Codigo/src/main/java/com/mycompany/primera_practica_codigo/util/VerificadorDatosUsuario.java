package com.mycompany.primera_practica_codigo.util;

import java.sql.SQLException;
import java.util.Optional;

import com.mycompany.primera_practica_codigo.modelo.dao.UsuarioDAO;
import com.mycompany.primera_practica_codigo.modelo.entidades.Usuario;

public class VerificadorDatosUsuario {

    private boolean existeNombreUsuario;

    public void verificarDatos(String nombreUsuario) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Optional<Usuario> usuario = usuarioDAO.encontrarPorNombre(nombreUsuario);
        this.existeNombreUsuario = usuario.isPresent();
    }

    public boolean getExisteNombreUsuario() {
        return existeNombreUsuario;
    }

}
