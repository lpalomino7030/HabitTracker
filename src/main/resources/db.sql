CREATE DATABASE habitTracker;
USE habitTracker;

CREATE TABLE habito (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_creacion DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE habito_Registrado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    completado BOOLEAN NOT NULL,
    habito_id BIGINT NOT NULL,

    CONSTRAINT fk_habito
        FOREIGN KEY (habito_id)
        REFERENCES habito(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_habito_fecha
        UNIQUE (habito_id, fecha)
);
select * from habito;
SELECT * FROM habito_registrado;
SHOW TABLES;