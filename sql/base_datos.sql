-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS prog2_db;
USE prog2_db;


DROP TABLE IF EXISTS productos;


CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO productos (nombre, categoria, precio, stock) VALUES 
('Mouse inalambrico', 'Perifericos', 89.99, 25),
('Teclado mecanico RGB', 'Perifericos', 249.50, 10),
('Monitor curvo 24 pulgadas', 'Pantallas', 899.00, 5),
('Auriculares gamer 7.1', 'Audio', 350.00, 12),
('Silla ergonomica oficina', 'Mobiliario', 1250.00, 4),
('Disco duro SSD 1TB', 'Almacenamiento', 550.00, 18),
('Memoria RAM 16GB DDR4', 'Componentes', 420.00, 15),
('Tarjeta grafica RTX 3060', 'Componentes', 3200.00, 3);


CREATE INDEX idx_categoria ON productos(categoria);


SELECT * FROM productos;
SELECT 
    id, 
    nombre, 
    categoria, 
    precio, 
    stock, 
    (precio * stock) AS valor_total_inventario 
FROM productos;

SELECT 
    categoria AS Categoria,
    COUNT(*) AS Total_Productos_Diferentes,
    SUM(stock) AS Unidades_Totales_En_Stock,
    CONCAT('$ ', FORMAT(SUM(precio * stock), 2)) AS Valor_Total_Categoria
FROM productos
GROUP BY categoria;


SELECT * FROM productos WHERE stock < 5;