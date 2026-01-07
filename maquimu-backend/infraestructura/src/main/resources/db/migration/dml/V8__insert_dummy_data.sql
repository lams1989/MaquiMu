-- V8__insert_dummy_data.sql
-- Inserción de un usuario OPERARIO con contraseña 'password' (hash BCrypt válido)
INSERT INTO usuarios (nombre_completo, email, password_hash, rol) VALUES
('Operario de Prueba', 'operario@maquimu.com', '$2a$10$3i0B1sf5XC6XyDCUm89HmOXbnuMwhQWG4NeCCtjPvP4dI6kZnVoKC', 'OPERARIO');

-- Inserción de datos de maquinaria
INSERT INTO maquinaria (nombre_equipo, marca, modelo, serial, tarifa_por_dia, tarifa_por_hora, estado_maquinaria, descripcion) VALUES
('Excavadora Caterpillar 320D', 'Caterpillar', '320D', 'EXC-CAT-001', 1000.00, 150.00, 'DISPONIBLE', 'Excavadora de orugas de uso general.'),
('Retroexcavadora JCB 3CX', 'JCB', '3CX', 'RET-JCB-002', 800.00, 120.00, 'ALQUILADO', 'Retroexcavadora versátil para trabajos ligeros.'),
('Cargador Frontal Komatsu WA380', 'Komatsu', 'WA380', 'CAR-KOM-003', 1200.00, 180.00, 'EN_MANTENIMIENTO', 'Cargador frontal para movimientos de tierra.'),
('Camión Volquete Volvo FMX', 'Volvo', 'FMX', 'CAM-VOL-004', 1500.00, 200.00, 'DISPONIBLE', 'Camión volquete de gran capacidad.');
