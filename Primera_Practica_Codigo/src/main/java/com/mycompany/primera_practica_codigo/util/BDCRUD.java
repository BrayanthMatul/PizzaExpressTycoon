package com.mycompany.primera_practica_codigo.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BDCRUD<T, ID> {

    public abstract void insertar(T entidad) throws SQLException;

    public abstract Optional<T> encontrarPorID(ID id) throws SQLException;

    public abstract Optional<T> encontrarPorNombre(String nombre) throws SQLException;

    public abstract List<T> obtenerTodo() throws SQLException;

    public abstract void actualizar(T entidad) throws SQLException;

    public abstract void eliminar(ID id) throws SQLException;
}