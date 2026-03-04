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
