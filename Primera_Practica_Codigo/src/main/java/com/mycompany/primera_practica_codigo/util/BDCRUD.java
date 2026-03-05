package com.mycompany.primera_practica_codigo.util;

import java.util.List;
import java.util.Optional;

public abstract class BDCRUD<T, ID> {
    

    public abstract void insertar(T entidad) throws Exception;

    public abstract Optional<T> encontrarPorID(ID id) throws Exception;

    public abstract Optional<T> encontrarPorNombre(String nombre) throws Exception;

    public abstract List<T> obtenerTodo();

    public abstract void actualizar(T entidad) throws Exception;

    public abstract void eliminar(ID id) throws Exception;
}