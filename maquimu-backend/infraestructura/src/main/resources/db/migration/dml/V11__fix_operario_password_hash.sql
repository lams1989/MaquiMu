-- V11__fix_operario_password_hash.sql
-- Corrige el hash de contraseña del operario con un BCrypt válido
-- Hash BCrypt válido para la contraseña 'password'
UPDATE usuarios
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE email = 'operario@maquimu.com';
