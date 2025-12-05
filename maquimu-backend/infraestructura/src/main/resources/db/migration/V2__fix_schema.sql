-- Corregir tipo de dato de usuario_id a BIGINT
ALTER TABLE usuarios MODIFY COLUMN usuario_id BIGINT AUTO_INCREMENT;
