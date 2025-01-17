-- Crear tabla usuarios
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    correo_electronico VARCHAR(50) NOT NULL unique,
    contrasena VARCHAR(400) NOT NULL,
    PRIMARY KEY (id)
);

-- Crear tabla perfiles
CREATE TABLE perfiles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(10),
    PRIMARY KEY (id)
);

-- Crear tabla cursos
CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) unique,
    categoria VARCHAR(20),
    PRIMARY KEY (id)
);

-- Crear tabla topicos
CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(100) NOT NULL,
    fecha_creacion DATE NOT NULL,
    status BOOLEAN,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios (id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos (id)
);

-- Crear tabla perfiles_usuarios
CREATE TABLE perfiles_usuarios (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id) ON DELETE CASCADE,
    FOREIGN KEY (perfil_id) REFERENCES perfiles (id) ON DELETE CASCADE
);
