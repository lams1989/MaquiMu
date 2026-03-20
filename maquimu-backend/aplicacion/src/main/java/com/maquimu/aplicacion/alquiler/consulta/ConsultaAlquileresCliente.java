package com.maquimu.aplicacion.alquiler.consulta;

/**
 * Consulta para obtener alquileres de un cliente.
 */
public class ConsultaAlquileresCliente {

	private final Long clienteId;

	public ConsultaAlquileresCliente(Long clienteId) {
		if (clienteId == null) {
			throw new IllegalArgumentException("El ID del cliente es obligatorio");
		}
		this.clienteId = clienteId;
	}

	public Long getClienteId() {
		return clienteId;
	}
}
