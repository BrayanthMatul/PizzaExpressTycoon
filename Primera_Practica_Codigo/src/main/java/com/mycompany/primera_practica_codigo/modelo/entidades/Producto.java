package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Producto {
    private int idProducto;
    private String nombre;
    private Sucursal sucursal;
    private boolean activo;

    public Producto(int idProducto, String nombre, Sucursal sucursal, boolean activo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.sucursal = sucursal;
        this.activo = activo;
    }

    public Producto(String nombre, Sucursal sucursal) {
        this.nombre = nombre;
        this.sucursal = sucursal;
        this.activo = true;
    }

    public Producto() {
    }

    // Getters y Setters
    public int getId() {
        return idProducto;
    }

    public void setId(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
