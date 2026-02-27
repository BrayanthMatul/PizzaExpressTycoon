package com.mycompany.primera_practica_codigo.Usuario;

import com.mycompany.primera_practica_codigo.ClasesIniciales.Rol;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private String email;
    private Rol rol;
    private boolean activo;

    // Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String password, String email, Rol rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.email = email;
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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
}
