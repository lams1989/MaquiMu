package com.maquimu.dominio.factura.puerto.dao;

import java.util.List;
import java.util.Optional;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;

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

	/**
	 * Cuenta las facturas de un cliente específico filtradas por estado de pago.
	 */
	long contarPorClienteYEstadoPago(Long clienteId, EstadoPago estadoPago);
}
