-- ============================================================================================================================
-- ======================================================DataBase==============================================================
-- ============================================================================================================================
CREATE DATABASE ElecSoluDB;
USE ElecSoluDB;

-- =================================== Tabla Cliente
CREATE TABLE clientes(
	clienteId 		INT AUTO_INCREMENT,
    clienteDNI 		CHAR(8) NULL,
    clienteNombre 	VARCHAR(50) NULL,
    clienteApellido VARCHAR(50) NULL,
    clienteNumero 	VARCHAR(10) NULL,
    clienteEmail 	VARCHAR(170) NULL,
    clienteEstado 	VARCHAR(10) NOT NULL DEFAULT 'Activo',
    CONSTRAINT pk_clientes			PRIMARY KEY (clienteId),
    CONSTRAINT ch_clienteDNI		CHECK (clienteDNI REGEXP '^[0-9]{8}$'),
    CONSTRAINT ch_clienteNombre 	CHECK (clienteNombre REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(clienteNombre)) > 0),
    CONSTRAINT ch_clienteApellido 	CHECK (clienteApellido REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(clienteApellido)) > 0),
    CONSTRAINT ch_clienteNumero 	CHECK (clienteNumero REGEXP '^[0-9]{9}$' OR clienteNumero = 'Sin Numero'),
    CONSTRAINT ch_clienteEmail 		CHECK (clienteEmail REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' OR clienteEmail = 'Sin Email'),
    CONSTRAINT ch_clienteEstado 	CHECK (clienteEstado IN ('Activo', 'Borrado')),
    CONSTRAINT uq_clienteDNI 		UNIQUE (clienteEstado)
);

-- =================================== Tabla Repuestos
CREATE TABLE repuestos(
	repuestoId INT AUTO_INCREMENT,
    repuestoNombre VARCHAR(150) NOT NULL,
    repuestoStock INT NOT NULL,
    repuestoPrecio DECIMAL(10,2),
    CONSTRAINT pk_repuestos PRIMARY KEY (repuestoId),
    CONSTRAINT ch_repuestoNombre CHECK (LENGTH(TRIM(clienteNombre)) > 0),
    CONSTRAINT ch_repuestoStock CHECK (repuestoStock >= 0),
    CONSTRAINT ch_repuestoPrecio CHECK (repuestoPrecio >= 0),
    CONSTRAINT uq_repuestoNombre UNIQUE (repuestoNombre)
);

-- =================================== Tabla Categorias
CREATE TABLE categorias(
	categoriaId 	INT AUTO_INCREMENT,
    categoriaNombre VARCHAR(50),
    CONSTRAINT pk_categorias 		PRIMARY KEY (categoriaId),
    CONSTRAINT ch_categoriaNombre 	CHECK (categoriaNombre REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(categoriaNombre)) > 0),
    CONSTRAINT uq_categoriaNombre 	UNIQUE (categoriaNombre)
);

CREATE TABLE ordenes(
	ordenId INT AUTO_INCREMENT,
    ordenServicio VARCHAR(15) NOT NULL,
    clienteId INT NOT NULL,
    ordenClienteAtual VARCHAR(110) NOT NULL,
    ordenEquipo VARCHAR(100) NOT NULL,
    ordenDescripcion TEXT NOT NULL,
    categoriaId INT NOT NULL,
    ordenSolucion TEXT NULL,
    ordenFechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ordenFechaEntrega DATE NOT NULL,
    ordenGarantiaMeses INT NOT NULL,
    ordenEstado VARCHAR (15) NOT NULL DEFAULT 'Registrado',
    CONSTRAINT pk_ordenes PRIMARY KEY (ordenId),
    CONSTRAINT fk_ordenes_clientes FOREIGN KEY (clienteId) REFERENCES clientes(clienteId),
    CONSTRAINT fk_ordenes_categoria FOREIGN KEY (categoriaId) REFERENCES categorias(categoriaId),
    CONSTRAINT ch_ordenServicio CHECK (ordenServicio IN ('Mantenimiento', 'Reparacion')) 
);