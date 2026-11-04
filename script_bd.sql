CREATE DATABASE IF NOT EXISTS distribuidora CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE distribuidora;

CREATE TABLE IF NOT EXISTS clientes (
    id              BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre_apellido VARCHAR(100) NOT NULL,
    direccion       VARCHAR(150),
    cuit            VARCHAR(13)  NOT NULL UNIQUE,
    telefono        VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS productos (
    id     BIGINT        AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20)   NOT NULL UNIQUE,
    nombre VARCHAR(100)  NOT NULL,
    stock  INT           NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS ventas (
    id         BIGINT      AUTO_INCREMENT PRIMARY KEY,
    fecha      DATETIME    NOT NULL,
    id_cliente BIGINT      NOT NULL,
    estado     VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

-- El precio se guarda en el detalle porque el del producto puede cambiar despues
CREATE TABLE IF NOT EXISTS detalle_venta (
    id_venta        BIGINT        NOT NULL,
    id_producto     BIGINT        NOT NULL,
    cantidad        INT           NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_venta, id_producto),
    FOREIGN KEY (id_venta) REFERENCES ventas(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);
