-- ============================================================================================================================
-- ======================================================DataBase==============================================================
-- ============================================================================================================================
CREATE DATABASE ElecSoluDB;
USE ElecSoluDB;

-- =================================== Tabla Cliente
CREATE TABLE clientes(
	clienteId 			INT AUTO_INCREMENT,
    clienteDNI 			CHAR(8) NULL,
    clienteNombres 		VARCHAR(70) NULL,
    clienteApellidos 	VARCHAR(70) NULL,
    clienteNumero 		VARCHAR(10) NULL,
    clienteEmail 		VARCHAR(170) NULL,
    clienteEstado 		VARCHAR(10) NOT NULL DEFAULT 'Activo',
    CONSTRAINT pk_clientes			PRIMARY KEY (clienteId),
    CONSTRAINT ch_clienteDNI		CHECK (clienteDNI REGEXP '^[0-9]{8}$'),
    CONSTRAINT ch_clienteNombre 	CHECK (clienteNombres REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(clienteNombres)) > 0),
    CONSTRAINT ch_clienteApellido 	CHECK (clienteApellidos REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(clienteApellidos)) > 0),
    CONSTRAINT ch_clienteNumero 	CHECK (clienteNumero REGEXP '^[0-9]{9}$' OR clienteNumero = 'Sin Numero'),
    CONSTRAINT ch_clienteEmail 		CHECK (clienteEmail REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' OR clienteEmail = 'Sin Email'),
    CONSTRAINT ch_clienteEstado 	CHECK (clienteEstado IN ('Activo', 'Borrado')),
    CONSTRAINT uq_clienteDNI 		UNIQUE (clienteDNI)
);

-- =================================== Tabla Repuestos
CREATE TABLE repuestos(
	repuestoId 		INT AUTO_INCREMENT,
    repuestoCod		CHAR(6) NOT NULL,
    repuestoNombre 	VARCHAR(80) NOT NULL,
    repuestoStock 	INT NOT NULL,
    repuestoPrecio 	DECIMAL(10,2) NOT NULL,
    repuestoEstado	VARCHAR(10) NOT NULL DEFAULT 'Activo',
    CONSTRAINT pk_repuestos 		PRIMARY KEY (repuestoId),
    CONSTRAINT ch_repuestoNombre 	CHECK (LENGTH(TRIM(repuestoNombre)) > 0),
    CONSTRAINT ch_repuestoStock 	CHECK (repuestoStock >= 0),
    CONSTRAINT ch_repuestoPrecio 	CHECK (repuestoPrecio >= 0),
    CONSTRAINT ch_repuestoEstado	CHECK (repuestoEstado IN ('Activo', 'Descontinuado')),
    CONSTRAINT uq_repuestoCod		UNIQUE (repuestoCod),
    CONSTRAINT uq_repuestoNombre 	UNIQUE (repuestoNombre)
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
	tecnicoId 			INT AUTO_INCREMENT,
    tecnicoNombres		VARCHAR(70) NOT NULL,
    tecnicoApellidos	VARCHAR(70) NOT NULL,
    tecnicoEstado		VARCHAR(15) DEFAULT 'Activo',
    CONSTRAINT pk_tecnicos 				PRIMARY KEY (tecnicoId),
    CONSTRAINT ch_tecnicoNombres 		CHECK (tecnicoNombres REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(tecnicoNombres)) > 0),
    CONSTRAINT ch_tecnicoApellidos 		CHECK (tecnicoApellidos REGEXP '^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$' AND LENGTH(TRIM(tecnicoApellidos)) > 0),
    CONSTRAINT ch_tecnicoEstado			CHECK (tecnicoEstado IN ('Activo', 'Vacaciones', 'Retirado'))
);

-- =================================== Tabla Ordenes
CREATE TABLE ordenes(
	ordenId 			INT AUTO_INCREMENT,
    clienteId 			INT NOT NULL,
    ordenClienteActual 	VARCHAR(110) NOT NULL,
    ordenServicio 		VARCHAR(15) NOT NULL,
    ordenEquipo 		VARCHAR(100) NOT NULL,
    categoriaId 		INT NOT NULL,
    ordenDescripcion 	TEXT NOT NULL,
    tecnicoId 			INT NOT NULL,
    ordenSolucion 		TEXT NULL,
    ordenFechaRegistro 	DATETIME DEFAULT CURRENT_TIMESTAMP,
    ordenFechaEntrega 	DATE NOT NULL,
    ordenGarantiaMeses 	INT NOT NULL,
    ordenPrecio 		DECIMAL(10,2) NOT NULL,
    ordenEstado 		VARCHAR (15) NOT NULL DEFAULT 'Registrado',
    CONSTRAINT pk_ordenes 				PRIMARY KEY (ordenId),
    CONSTRAINT fk_ordenes_clientes 		FOREIGN KEY (clienteId) REFERENCES clientes(clienteId),
    CONSTRAINT fk_ordenes_categoria 	FOREIGN KEY (categoriaId) REFERENCES categorias(categoriaId),
    CONSTRAINT fk_ordenes_tecnicos 		FOREIGN KEY (tecnicoId) REFERENCES tecnicos(tecnicoId),
    CONSTRAINT ch_ordenServicio 		CHECK (ordenServicio IN ('Mantenimiento', 'Reparacion')),
    CONSTRAINT ch_ordenGarantiaMeses 	CHECK (ordenGarantiaMeses >= 0),
    CONSTRAINT ch_ordenPrecio 			CHECK (ordenPrecio >= 0),
    CONSTRAINT ch_ordenEstado 			CHECK (ordenEstado IN ('Registrado', 'En Taller', 'Entregable', 'Cerrado'))
);

-- =================================== Tabla Detalles de Ordenes
CREATE TABLE detalleOrdenes(
	detalleOrdenId			INT AUTO_INCREMENT,
    ordenId          		INT NOT NULL,
    repuestoId       		INT NOT NULL,
    repuestoCantidad 		INT NOT NULL,
    repuestoPrecioUnitario  DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_detalleOrdenes 			PRIMARY KEY (detalleOrdenId),
    CONSTRAINT fk_detalleOrdenes_ordenes 	FOREIGN KEY (ordenId) REFERENCES ordenes(ordenId),
    CONSTRAINT fk_detallesOrdenes_repuestos FOREIGN KEY (repuestoId) REFERENCES repuestos(repuestoId),
    CONSTRAINT ch_repuestoCantidad 			CHECK (repuestoCantidad > 0),
    CONSTRAINT ch_repuestoPrecioUnitario 	CHECK (repuestoPrecioUnitario >= 0),
    CONSTRAINT uq_orden_repuesto 			UNIQUE (ordenId,repuestoId)
);

-- =================================== Tabla Usuarios
CREATE TABLE usuarios(
	usuarioId INT AUTO_INCREMENT,
    usuarioNombre VARCHAR(50),
    usuarioClave VARCHAR(50),
    usuarioRol VARCHAR(20),
    CONSTRAINT pk_usuario PRIMARY KEY (usuarioId),
    CONSTRAINT ch_usuarioRol CHECK (usuarioRol IN ('Admin', 'Tecnico', 'Recepcionista')),
    CONSTRAINT uq_usuario UNIQUE (usuarioNombre)
);

-- ============================================================================================================================
-- ======================================================Procesos==============================================================
-- ============================================================================================================================
-- =================================== Cliente
-- Create
DELIMITER //
CREATE PROCEDURE usp_RegistrarCliente(
	IN dni CHAR(8),
    IN nombre VARCHAR(70),
    IN apellidos VARCHAR(70),
    IN numero VARCHAR(10),
    IN email VARCHAR(170)
)
BEGIN
	IF (numero = '' OR numero IS NULL) AND (email = '' OR email IS NULL)THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se puede registrar al cliente sin datos de contacto.';
	END IF;

	IF numero = '' OR numero IS NULL THEN
		SET numero = 'Sin Numero';
	END IF;
    
    IF email = '' OR email IS NULL THEN
		SET email = 'Sin Email';
	END IF;
    
    INSERT INTO clientes(clienteDNI, clienteNombres, clienteApellidos, clienteNumero, clienteEmail)
    VALUES
    (dni, nombre, apellidos, numero, email);
END //
DELIMITER ;

-- Read
-- Vista
CREATE VIEW vw_Clientes AS
SELECT 
	clienteId AS id,
    clienteDNI AS dni,
    clienteNombres AS nombres,
    clienteApellidos AS apellidos,
    clienteNumero AS numero,
    clienteEmail AS email
FROM clientes
WHERE clienteEstado = 'Activo';

-- Lista de Clientes
DELIMITER //
CREATE PROCEDURE usp_ListarClientes()
BEGIN
	SELECT id, dni, nombres, apellidos
    FROM vw_Clientes;
END //
DELIMITER ;

-- Info de Cliente
DELIMITER //
CREATE PROCEDURE usp_InfoCliente(
	IN infoId INT
)
BEGIN
	SELECT id, dni, nombres, apellidos, numero, email
    FROM vw_Clientes
    WHERE id = infoId;
END //
DELIMITER ;

-- Update
DELIMITER //
CREATE PROCEDURE usp_ActualizarCliente(
	IN id INT,
	IN dni CHAR(8),
    IN nombre VARCHAR(70),
    IN apellidos VARCHAR(70),
    IN numero VARCHAR(10),
    IN email VARCHAR(170)
)
BEGIN
	IF (numero = '' OR numero IS NULL) AND (email = '' OR email IS NULL)THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se puede actualizar al cliente sin datos de contacto.';
	END IF;

	IF numero = '' OR numero IS NULL THEN
		SET numero = 'Sin Numero';
	END IF;
    
    IF email = '' OR email IS NULL THEN
		SET email = 'Sin Email';
	END IF;
    
    UPDATE clientes
	SET 
		clienteDNI = dni,
		clienteNombres = nombre,
		clienteApellidos = apellidos,
		clienteNumero = numero,
		clienteEmail = email
	WHERE clienteId = id;		
END //
DELIMITER ;

-- Delete (logico)
DELIMITER //
CREATE PROCEDURE usp_EliminarCliente(
	IN id INT
)
BEGIN
	UPDATE clientes
	SET 
		clienteDNI = NULL,
		clienteNombres = NULL,
		clienteApellidos = NULL,
		clienteNumero = NULL,
		clienteEmail = NULL,
        clienteEstado = 'Borrado'
	WHERE clienteId = id;		
END //
DELIMITER ;

-- =================================== Tabla Repuestos
-- Create
DELIMITER //
CREATE PROCEDURE usp_RegistrarRepuesto(
	IN nombre VARCHAR(80),
    IN stock INT,
    IN precio DECIMAL(10,2)
)
BEGIN
	DECLARE id INT;
    DECLARE cod CHAR(6);

    SELECT COALESCE(MAX(repuestoId), 0) + 1 INTO id FROM repuestos;
    SET cod = CONCAT('RP', LPAD(id, 4, '0'));
    
    INSERT INTO repuestos(repuestoCod, repuestoNombre, repuestoStock, repuestoPrecio)
    VALUES (cod, nombre, stock, precio);
END //
DELIMITER ;

-- Read
-- View
CREATE VIEW vw_Repuestos AS
SELECT
	repuestoId AS id,
    repuestoCod AS codigo,
    repuestoNombre AS nombre,
    repuestoStock AS stock,
    repuestoPrecio AS precio
FROM repuestos
WHERE repuestoEstado = 'Activo';

-- Lista de Repuestos
DELIMITER //
CREATE PROCEDURE usp_ListarRepuestos()
BEGIN
	SELECT id, codigo, nombre, stock
    FROM vw_Repuestos;
END //
DELIMITER ;

-- Listar de Repuestos para Orden
DELIMITER //
CREATE PROCEDURE usp_ListarRepuestosOrden()
BEGIN
	SELECT id, nombre, stock, precio
    FROM vw_Repuestos;
END //
DELIMITER ;
 
-- Info de Repuesto
DELIMITER //
CREATE PROCEDURE usp_InfoRepuesto(
	IN infoId INT
)
BEGIN
	SELECT id, codigo, nombre, stock, precio
    FROM vw_Repuestos
    WHERE id = infoId;
END //
DELIMITER ;

-- Update
DELIMITER //
CREATE PROCEDURE usp_ActualizarRepuesto(
	IN id INT,
    IN nombre VARCHAR(80),
    IN stock INT,
    IN precio DECIMAL(10,2)
)
BEGIN
	UPDATE repuestos
    SET
		repuestoNombre = nombre,
        repuestoStock = stock,
		repuestoPrecio = precio
	WHERE repuestoId = id;
END //
DELIMITER ;

-- Delete (logico)
DELIMITER //
CREATE PROCEDURE usp_EliminarRepuesto(
IN id INT
)
BEGIN
	UPDATE repuestos
    SET
		repuestoEstado = 'Descontinuado'
	WHERE repuestoId = id;
END //
DELIMITER ;

-- =================================== Tabla Categoria
-- Read
DELIMITER //
CREATE PROCEDURE usp_ListarCategorias()
BEGIN
	SELECT
		categoriaId AS id, 
        categoriaNombre AS nombre
    FROM categorias;
END //
DELIMITER ;

-- =================================== Tabla Tecnicos
-- Read
-- View
CREATE VIEW vw_Tecnicos AS
SELECT
	tecnicoId AS id,
    tecnicoNombres AS nombres,
    tecnicoApellidos AS apellidos
FROM tecnicos
WHERE tecnicoEstado = 'Activo';

-- Listar Tecnicos
DELIMITER //
CREATE PROCEDURE usp_ListarTecnicos()
BEGIN
	SELECT id, nombres, apellidos
    FROM vw_Tecnicos;
END //
DELIMITER ;

-- =================================== Tabla Ordenes
-- Create
-- Read
-- Update
-- =================================== Tabla Detalles de Ordenes
-- Create
-- Read
-- Update
-- =================================== Reportes
-- Ingreso por Servicio
-- Productividad de Tecnicos
-- Repuestos Utilizados
-- =================================== Tabla Usuarios
-- Read
DELIMITER //
CREATE PROCEDURE usp_Verificar(
	IN usr VARCHAR(50),
    IN psw VARCHAR(50)
)
BEGIN
	SELECT 
		usuarioId AS id,
		usuarioNombre AS usuario,
        usuarioRol AS rol
	FROM usuarios
    WHERE usuarioNombre = usr
		AND usuarioClave = psw;
END //
DELIMITER ;

-- ============================================================================================================================
-- ========================================================Data================================================================
-- ============================================================================================================================
-- =================================== Clientes
