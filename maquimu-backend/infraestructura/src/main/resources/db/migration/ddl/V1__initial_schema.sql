-- -----------------------------------------------------
-- Script para crear la base de datos MaquiMu
-- Basado en el documento: GA6-220501096-AA2-EV03
-- Motor: MySQL
-- -----------------------------------------------------

-- 2. Tabla: usuarios
-- Almacena las cuentas de los empleados
-- -----------------------------------------------------
CREATE TABLE `usuarios` (
  `usuario_id` INT NOT NULL AUTO_INCREMENT,
  `nombre_completo` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `rol` ENUM('OPERARIO', 'CLIENTE') NOT NULL,
  `fecha_creacion` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`usuario_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)
) ENGINE = InnoDB;


-- 3. Tabla: clientes
-- Contiene la información de las empresas que alquilan
-- -----------------------------------------------------
CREATE TABLE `clientes` (
  `cliente_id` INT NOT NULL AUTO_INCREMENT,
  `nombre_cliente` VARCHAR(255) NOT NULL,
  `identificacion` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(20) NULL,
  `email` VARCHAR(100) NOT NULL,
  `direccion` VARCHAR(255) NULL,
  `fecha_registro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cliente_id`),
  UNIQUE INDEX `identificacion_UNIQUE` (`identificacion` ASC)
) ENGINE = InnoDB;


-- 4. Tabla: maquinaria
-- Inventario central de equipos
-- -----------------------------------------------------
CREATE TABLE `maquinaria` (
  `maquinaria_id` INT NOT NULL AUTO_INCREMENT,
  `nombre_equipo` VARCHAR(150) NOT NULL,
  `marca` VARCHAR(50) NULL,
  `serial` VARCHAR(100) NOT NULL,
  `modelo` VARCHAR(50) NULL,
  `tarifa_por_dia` DECIMAL(10,2) NOT NULL,
  `tarifa_por_hora` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`maquinaria_id`),
  UNIQUE INDEX `serial_UNIQUE` (`serial` ASC)
) ENGINE = InnoDB;


-- 5. Tabla: alquileres
-- Tabla transaccional que registra los contratos
-- -----------------------------------------------------
CREATE TABLE `alquileres` (
  `alquiler_id` INT NOT NULL AUTO_INCREMENT,
  `cliente_id` INT NOT NULL,
  `maquinaria_id` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  `fecha_inicio` DATETIME NOT NULL,
  `fecha_fin` DATETIME NOT NULL,
  `costo_total` DECIMAL(12,2) NOT NULL,
  `estado_alquiler` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`alquiler_id`),
  INDEX `fk_alquileres_clientes_idx` (`cliente_id` ASC),
  INDEX `fk_alquileres_maquinaria_idx` (`maquinaria_id` ASC),
  INDEX `fk_alquileres_usuarios_idx` (`usuario_id` ASC),
  CONSTRAINT `fk_alquileres_clientes`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `clientes` (`cliente_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_alquileres_maquinaria`
    FOREIGN KEY (`maquinaria_id`)
    REFERENCES `maquinaria` (`maquinaria_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_alquileres_usuarios`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `usuarios` (`usuario_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB;


-- 6. Tabla: facturas
-- Almacena la información financiera de cada alquiler
-- -----------------------------------------------------
CREATE TABLE `facturas` (
  `factura_id` INT NOT NULL AUTO_INCREMENT,
  `alquiler_id` INT NOT NULL,
  `fecha_emision` DATE NOT NULL,
  `monto` DECIMAL(12,2) NOT NULL,
  `estado_pago` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`factura_id`),
  INDEX `fk_facturas_alquileres_idx` (`alquiler_id` ASC),
  CONSTRAINT `fk_facturas_alquileres`
    FOREIGN KEY (`alquiler_id`)
    REFERENCES `alquileres` (`alquiler_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB;