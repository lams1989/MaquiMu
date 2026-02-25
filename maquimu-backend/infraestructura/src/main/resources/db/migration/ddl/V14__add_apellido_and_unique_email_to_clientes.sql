-- V14__add_apellido_and_unique_email_to_clientes.sql
-- Agrega apellido y restricción de unicidad para email en clientes (idempotente)

SET @col_apellido_exists := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'clientes'
      AND COLUMN_NAME = 'apellido'
);

SET @sql_add_apellido := IF(
    @col_apellido_exists = 0,
    'ALTER TABLE clientes ADD COLUMN apellido VARCHAR(100) NULL AFTER nombre_cliente',
    'SELECT 1'
);

PREPARE stmt_add_apellido FROM @sql_add_apellido;
EXECUTE stmt_add_apellido;
DEALLOCATE PREPARE stmt_add_apellido;

SET @email_unique_exists := (
    SELECT COUNT(*)
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'clientes'
      AND COLUMN_NAME = 'email'
      AND NON_UNIQUE = 0
);

SET @sql_add_email_unique := IF(
    @email_unique_exists = 0,
    'ALTER TABLE clientes ADD CONSTRAINT email_UNIQUE UNIQUE (email)',
    'SELECT 1'
);

PREPARE stmt_add_email_unique FROM @sql_add_email_unique;
EXECUTE stmt_add_email_unique;
DEALLOCATE PREPARE stmt_add_email_unique;
