package com.maquimu.dominio.factura.puerto.dao;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (lectura) para consultar facturas.
 */
public interface FacturaDao {
    List<Factura> listarTodas();
    Optional<Factura> buscarPorId(Long facturaId);
    Optional<Factura> buscarPorAlquilerId(Long alquilerId);
    List<Factura> buscarPorClienteId(Long clienteId);
    List<Factura> listarPorEstadoPago(EstadoPago estadoPago);
    long contarPorEstadoPago(EstadoPago estadoPago);
}
