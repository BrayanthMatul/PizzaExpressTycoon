package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private Rol rol;
    private Sucursal sucursal;

    // Constructores
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String password, Rol rol, Sucursal sucursal) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.sucursal = sucursal;
    }

    public Usuario(String nombreUsuario, String password, Rol rol, Sucursal sucursal) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.sucursal = sucursal;
    }

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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    // Métodos
    public boolean validarCredenciales() {
        // TODO: implementar
        return false;
    }

}
