-- V13: Add cliente_id column to facturas table for fast client-level queries
ALTER TABLE `facturas`
    ADD COLUMN `cliente_id` BIGINT NOT NULL AFTER `alquiler_id`,
    ADD INDEX `fk_facturas_clientes_idx` (`cliente_id` ASC),
    ADD CONSTRAINT `fk_facturas_clientes`
        FOREIGN KEY (`cliente_id`)
        REFERENCES `clientes` (`cliente_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

-- Add unique constraint to prevent duplicate invoices per rental
ALTER TABLE `facturas`
    ADD CONSTRAINT `uq_facturas_alquiler` UNIQUE (`alquiler_id`);
