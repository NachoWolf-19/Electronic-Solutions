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
    CONSTRAINT uq_clienteDNI 		UNIQUE (clienteDNI)
);

-- =================================== Tabla Repuestos
CREATE TABLE repuestos(
	repuestoId INT AUTO_INCREMENT,
    repuestoNombre VARCHAR(150) NOT NULL,
    repuestoStock INT NOT NULL,
    repuestoPrecio DECIMAL(10,2),
    CONSTRAINT pk_repuestos PRIMARY KEY (repuestoId),
    CONSTRAINT ch_repuestoNombre CHECK (LENGTH(TRIM(repuestoNombre)) > 0),
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

-- =================================== Tabla Tecnicos
CREATE TABLE tecnicos(
	tecnicoId INT AUTO_INCREMENT,
    tecnicoNombre VARCHAR(50) NOT NULL,
    tecnicoApellido VARCHAR(50) NOT NULL,
    categoriaId INT NOT NULL,
    tecnicoEstado VARCHAR(15) DEFAULT 'Activo',
    CONSTRAINT pk_tecnicos 				PRIMARY KEY (tecnicoId),
    CONSTRAINT fk_tecnicos_categorias 	FOREIGN KEY (categoriaId) REFERENCES categorias(categoriaId),
    CONSTRAINT ch_tecnicoNombre 		CHECK (tecnicoNombre REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(tecnicoNombre)) > 0),
    CONSTRAINT ch_tecnicoApellido 		CHECK (tecnicoApellido REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(tecnicoApellido)) > 0),
    CONSTRAINT ch_tecnicoEstado			CHECK (tecnicoEstado IN ('Activo', 'Vacaciones', 'Retirado'))
);

-- =================================== Tabla Ordenes
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
    ordenPrecio DECIMAL(10,2) NOT NULL,
    ordenEstado VARCHAR (15) NOT NULL DEFAULT 'Registrado',
    CONSTRAINT pk_ordenes PRIMARY KEY (ordenId),
    CONSTRAINT fk_ordenes_clientes FOREIGN KEY (clienteId) REFERENCES clientes(clienteId),
    CONSTRAINT fk_ordenes_categoria FOREIGN KEY (categoriaId) REFERENCES categorias(categoriaId),
    CONSTRAINT ch_ordenServicio CHECK (ordenServicio IN ('Mantenimiento', 'Reparacion')) 
);

CREATE TABLE detalleOrdenes(
    ordenId          		INT NOT NULL,
    repuestoId       		INT NOT NULL,
    repuestoCantidad 		INT NOT NULL,
    repuestoPrecioUnitario  DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_detalles_ordenes PRIMARY KEY (ordenid, repuestoid),
    CONSTRAINT fk_detalles_ordenes_ordenes FOREIGN KEY (ordenid) REFERENCES ordenes(ordenid),
    CONSTRAINT fk_detalles_ordenes_repuestos FOREIGN KEY (repuestoid) REFERENCES repuestos(repuestoid),
    CONSTRAINT ch_detalle_cantidad CHECK (cantidad > 0),
    CONSTRAINT ch_detalle_precio CHECK (preciounitario >= 0)
);