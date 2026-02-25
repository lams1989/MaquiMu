-- V15__add_estado_cuenta_motivo_rechazo_fix_operario.sql
-- HU #16: Aprobación de Registro de Cliente por Operario
-- 1. Agrega columna estado_cuenta a usuarios (DEFAULT ACTIVO para no romper existentes)
-- 2. Agrega columna motivo_rechazo a usuarios
-- 3. Corrige hash de contraseña del super operario (Admin@2026)

ALTER TABLE usuarios
  ADD COLUMN estado_cuenta ENUM('PENDIENTE_APROBACION', 'ACTIVO', 'RECHAZADO')
  NOT NULL DEFAULT 'ACTIVO'
  AFTER rol;

ALTER TABLE usuarios
  ADD COLUMN motivo_rechazo VARCHAR(500) NULL
  AFTER estado_cuenta;

-- Corregir el hash del super operario para que funcione con contraseña 'Admin@2026'
UPDATE usuarios
SET password_hash = '$2a$10$KqHBDsO/aaX5X6nvWXqrruJK4V6TeeoiJEMDCN3hJOjyWI1xN72dC'
WHERE email = 'operario@maquimu.com';
