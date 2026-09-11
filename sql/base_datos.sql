CREATE DATABASE IF NOT EXISTS prog2_db;
USE prog2_db;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
);

INSERT IGNORE INTO productos (id, nombre, categoria, precio, stock) VALUES
    (1, 'Mouse inalambrico', 'Perifericos', 89.99, 25),
    (2, 'Teclado mecanico', 'Perifericos', 249.50, 10),
    (3, 'Monitor 22
    4 pulgadas', 'Pantallas', 899.00, 5);