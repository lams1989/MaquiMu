-- V4__alter_maquinaria_id_to_bigint.sql
-- Drop the foreign key constraint first
ALTER TABLE alquileres DROP FOREIGN KEY fk_alquileres_maquinaria;

-- Alter the column type in the 'maquinaria' table
ALTER TABLE maquinaria MODIFY COLUMN maquinaria_id BIGINT AUTO_INCREMENT;

-- Alter the column type in the 'alquileres' table (foreign key column)
ALTER TABLE alquileres MODIFY COLUMN maquinaria_id BIGINT NOT NULL;

-- Re-add the foreign key constraint
ALTER TABLE alquileres ADD CONSTRAINT fk_alquileres_maquinaria
    FOREIGN KEY (maquinaria_id) REFERENCES maquinaria (maquinaria_id)
    ON DELETE RESTRICT ON UPDATE CASCADE;
