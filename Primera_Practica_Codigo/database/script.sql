-- Base de datos para Pizza Express Tycoon
CREATE DATABASE pizza_express_tycoon;

CREATE USER 'dbpractica1'@'localhost' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON pizza_express_tycoon.* TO 'dbpractica1'@'localhost';
FLUSH PRIVILEGES;

USE pizza_express_tycoon;

CREATE TABLE sucursal (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    ubicacion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE rol (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO rol (nombre) VALUES 
    ('JUGADOR'),
    ('ADMINISTRADOR'),
    ('SUPERADMINISTRADOR');

CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol_id INT NOT NULL,
    sucursal_id INT NULL, 
    FOREIGN KEY (rol_id) REFERENCES rol(id),
    FOREIGN KEY (sucursal_id) REFERENCES sucursal(id) ON DELETE SET NULL
);


INSERT INTO usuario (nombre_usuario, password, rol_id, sucursal_id) 
VALUES ('admin', '12345', 3, NULL);

CREATE TABLE producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    sucursal_id INT NOT NULL,
    FOREIGN KEY (sucursal_id) REFERENCES sucursal(id) ON DELETE CASCADE
);
