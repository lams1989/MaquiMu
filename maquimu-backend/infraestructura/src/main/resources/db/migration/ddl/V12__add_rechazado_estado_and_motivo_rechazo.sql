-- V12: Agregar estado RECHAZADO al enum de estado_alquiler y campo motivo_rechazo
-- HU 07: Gestionar Alquileres (Operario)

-- 1. Agregar 'RECHAZADO' al enum de estado_alquiler
ALTER TABLE alquileres 
    MODIFY COLUMN estado_alquiler ENUM('PENDIENTE', 'APROBADO', 'RECHAZADO', 'ACTIVO', 'FINALIZADO', 'CANCELADO') NOT NULL DEFAULT 'PENDIENTE';

-- 2. Agregar columna motivo_rechazo (opcional, solo cuando estado es RECHAZADO)
ALTER TABLE alquileres 
    ADD COLUMN motivo_rechazo VARCHAR(500) NULL AFTER estado_alquiler;
