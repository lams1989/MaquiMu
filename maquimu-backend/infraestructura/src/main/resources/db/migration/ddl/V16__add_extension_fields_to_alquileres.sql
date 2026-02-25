-- V16: Agregar campos de extensión a alquileres y nuevo estado PENDIENTE_EXTENSION
-- HU #17: Extensión de Alquiler Activo

-- 1. Agregar campos de extensión
ALTER TABLE alquileres
  ADD COLUMN fecha_fin_solicitada DATETIME NULL AFTER fecha_fin,
  ADD COLUMN costo_adicional DECIMAL(12,2) NULL AFTER costo_total;

-- 2. Actualizar ENUM de estado para incluir PENDIENTE_EXTENSION
ALTER TABLE alquileres
  MODIFY COLUMN estado_alquiler
  ENUM('PENDIENTE','APROBADO','RECHAZADO','ACTIVO','FINALIZADO','CANCELADO','PENDIENTE_EXTENSION')
  NOT NULL DEFAULT 'PENDIENTE';
