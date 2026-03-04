package com.mycompany.primera_practica_codigo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/pizza_express_tycoon";
    private static final String USER = "dbpractica1";
    private static final String PASSWORD = "12345";
    private static Connection conexion;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC", e);
        }
    }
    
    private ConexionBD() {}
    
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            synchronized (ConexionBD.class) {
                if (conexion == null || conexion.isClosed()) {
                    conexion = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
                }
            }
        }
        return conexion;
    }
}
