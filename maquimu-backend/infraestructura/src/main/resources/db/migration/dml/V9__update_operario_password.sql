-- V9__update_operario_password.sql
-- Actualiza el password_hash del usuario 'operario@maquimu.com'
-- El hash corresponde a la contraseña 'password' generada con BCryptPasswordEncoder de Spring Security.
UPDATE usuarios
SET password_hash = '$2a$10$22n9vB8M.3QzM4c72k74kO8q2/5eL5c7k8k4c7k4k7c7k.O8i0'
WHERE email = 'operario@maquimu.com';