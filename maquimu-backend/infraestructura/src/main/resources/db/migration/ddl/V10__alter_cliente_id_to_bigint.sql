-- V10__alter_cliente_id_to_bigint.sql
-- Actualiza cliente_id de INT a BIGINT para compatibilidad con Long de Java

-- 1. Eliminar la FK de alquileres
ALTER TABLE alquileres DROP FOREIGN KEY fk_alquileres_clientes;

-- 2. Modificar columna en tabla alquileres
ALTER TABLE alquileres MODIFY COLUMN cliente_id BIGINT NOT NULL;

-- 3. Modificar columna en tabla clientes
ALTER TABLE clientes MODIFY COLUMN cliente_id BIGINT NOT NULL AUTO_INCREMENT;

-- 4. Recrear la FK
ALTER TABLE alquileres ADD CONSTRAINT fk_alquileres_clientes 
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id);
