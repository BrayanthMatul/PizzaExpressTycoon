package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private String email;
    private boolean activo;

    // Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String password, String email, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.email = email;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Métodos
    public boolean validarCredenciales() {
        // TODO: implementar
        return false;
    }
    
    public TipoUsuario getTipoUsuario() {
        if (this instanceof Jugador) {
            return TipoUsuario.JUGADOR;
        } else if (this instanceof Administrador) {
            return TipoUsuario.ADMINISTRADOR;
        } else if (this instanceof SuperAdministrador) {
            return TipoUsuario.SUPERADMINISTRADOR;
        }
        return null;
    }
}
