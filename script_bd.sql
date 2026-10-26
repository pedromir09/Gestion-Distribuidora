CREATE DATABASE IF NOT EXISTS distribuidora;
USE distribuidora;

CREATE TABLE IF NOT EXISTS productos (
    id     BIGINT        AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20)   NOT NULL UNIQUE,
    nombre VARCHAR(100)  NOT NULL,
    stock  INT           NOT NULL,
    precio DECIMAL(10,2) NOT NULL
);
