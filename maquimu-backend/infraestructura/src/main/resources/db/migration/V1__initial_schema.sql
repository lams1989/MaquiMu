-- =====================================================
-- Migración Inicial - Esquema Base de Datos MaquiMu
-- =====================================================

-- Tabla de usuarios (autenticación)
CREATE TABLE IF NOT EXISTS usuarios (
    usuario_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('OPERARIO', 'CLIENTE') NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_cliente VARCHAR(100) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_identificacion (identificacion),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de maquinaria
CREATE TABLE IF NOT EXISTS maquinaria (
    maquinaria_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_equipo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    serial VARCHAR(50) NOT NULL UNIQUE,
    estado ENUM('DISPONIBLE', 'ALQUILADO', 'EN_MANTENIMIENTO') NOT NULL DEFAULT 'DISPONIBLE',
    tarifa_por_dia DECIMAL(10,2) NOT NULL,
    tarifa_por_hora DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    INDEX idx_serial (serial),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de alquileres
CREATE TABLE IF NOT EXISTS alquileres (
    alquiler_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    maquinaria_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    tipo_tarifa ENUM('POR_DIA', 'POR_HORA') NOT NULL,
    cantidad_unidades INT NOT NULL,
    costo_total DECIMAL(10,2),
    estado ENUM('SOLICITADO', 'APROBADO', 'EN_CURSO', 'FINALIZADO', 'CANCELADO') NOT NULL DEFAULT 'SOLICITADO',
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id) ON DELETE RESTRICT,
    FOREIGN KEY (maquinaria_id) REFERENCES maquinaria(maquinaria_id) ON DELETE RESTRICT,
    INDEX idx_cliente (cliente_id),
    INDEX idx_maquinaria (maquinaria_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_inicio (fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de facturas
CREATE TABLE IF NOT EXISTS facturas (
    factura_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alquiler_id BIGINT NOT NULL,
    numero_factura VARCHAR(50) NOT NULL UNIQUE,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado ENUM('PENDIENTE', 'PAGADA', 'ANULADA') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (alquiler_id) REFERENCES alquileres(alquiler_id) ON DELETE RESTRICT,
    INDEX idx_numero_factura (numero_factura),
    INDEX idx_alquiler (alquiler_id),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
