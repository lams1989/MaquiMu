-- V8__insert_dummy_data.sql
-- Inserción de un usuario OPERARIO
-- Contraseña 'password' hasheada con BCrypt (puedes generar la tuya propia si necesitas una específica)
-- Ejemplo de hash para 'password': $2a$10$T1K7.XmQ7.E9R2Z0q.D0.u/t/z.W.7Z0.W.9Z0.W.1Z0.W.7Z0.W.1Z0.W.1Z0.W.1Z0
INSERT INTO usuarios (nombre_completo, email, password_hash, rol) VALUES
('Operario de Prueba', 'operario@maquimu.com', '$2a$10$T1K7.XmQ7.E9R2Z0q.D0.u/t/z.W.7Z0.W.9Z0.W.9Z0.W.1Z0.W.1Z0.W.1Z0.W.1Z0.W.1Z0', 'OPERARIO');

-- Inserción de datos de maquinaria
INSERT INTO maquinaria (nombre_equipo, marca, modelo, serial, tarifa_por_dia, tarifa_por_hora, estado_maquinaria, descripcion) VALUES
('Excavadora Caterpillar 320D', 'Caterpillar', '320D', 'EXC-CAT-001', 1000.00, 150.00, 'DISPONIBLE', 'Excavadora de orugas de uso general.'),
('Retroexcavadora JCB 3CX', 'JCB', '3CX', 'RET-JCB-002', 800.00, 120.00, 'ALQUILADO', 'Retroexcavadora versátil para trabajos ligeros.'),
('Cargador Frontal Komatsu WA380', 'Komatsu', 'WA380', 'CAR-KOM-003', 1200.00, 180.00, 'EN_MANTENIMIENTO', 'Cargador frontal para movimientos de tierra.'),
('Camión Volquete Volvo FMX', 'Volvo', 'FMX', 'CAM-VOL-004', 1500.00, 200.00, 'DISPONIBLE', 'Camión volquete de gran capacidad.');
