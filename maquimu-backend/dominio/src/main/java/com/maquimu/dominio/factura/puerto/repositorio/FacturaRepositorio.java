package com.maquimu.dominio.factura.puerto.repositorio;

import com.maquimu.dominio.factura.modelo.Factura;

/**
 * Puerto de salida (escritura) para persistir facturas.
 */
public interface FacturaRepositorio {
    Factura guardar(Factura factura);
}
