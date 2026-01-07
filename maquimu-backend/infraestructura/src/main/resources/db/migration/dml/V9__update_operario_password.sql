-- V9__update_operario_password.sql
-- Actualiza el password_hash del usuario 'operario@maquimu.com'
-- Hash válido para 'password' (mismo que V8 para idempotencia)
UPDATE usuarios
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE email = 'operario@maquimu.com';