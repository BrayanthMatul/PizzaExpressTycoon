package com.mycompany.primera_practica_codigo.modelo.entidades;

public class Sucursal {
    private int id;
    private String nombre;
    private String ubicacion;
    private String telefono;

    // Constructores
    public Sucursal() {
    }

    // Constructor para crear nueva sucursal 
    public Sucursal(String nombre, String ubicacion, String telefono) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }

    // Constructor completo
    public Sucursal(int id, String nombre, String ubicacion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
