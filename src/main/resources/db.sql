CREATE DATABASE habitTracker;
USE habitTracker;

CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombres VARCHAR(100) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
                         contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE habito (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(255),
                        fecha_creacion DATE DEFAULT (CURRENT_DATE),
                        usuario_id BIGINT NOT NULL,

                        CONSTRAINT fk_usuario
                            FOREIGN KEY (usuario_id)
                                REFERENCES usuario(id)
                                ON DELETE CASCADE
);

CREATE TABLE habito_registrado (
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

SELECT * FROM usuario;
SELECT * FROM habito_registrado;
SHOW TABLES;

INSERT INTO usuario (nombres, email, nombre_usuario, contrasena)
VALUES (
           'Luis',
           'luis@test.com',
           'luis',
           '$2a$10$Dow1mYfE7bQ8gF1JxgRrUuM7Vv3ZC6G6s2wKzZr1yqf7X9k3h8e1G'
       );