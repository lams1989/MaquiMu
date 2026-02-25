-- V17: Agregar estado RESTABLECER al enum estado_cuenta de usuarios
-- HU #18: Restablecer y Cambiar Contraseña

ALTER TABLE usuarios
  MODIFY COLUMN estado_cuenta
  ENUM('PENDIENTE_APROBACION', 'ACTIVO', 'RECHAZADO', 'RESTABLECER')
  NOT NULL DEFAULT 'ACTIVO';
