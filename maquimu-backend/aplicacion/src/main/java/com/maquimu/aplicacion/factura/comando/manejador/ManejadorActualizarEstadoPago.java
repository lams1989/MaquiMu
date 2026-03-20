package com.maquimu.aplicacion.factura.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.factura.comando.ComandoActualizarEstadoPago;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.dominio.factura.puerto.repositorio.FacturaRepositorio;

/**
 * Manejador del comando para actualizar el estado de pago de una factura.
 */
@Service
public class ManejadorActualizarEstadoPago {

	private final FacturaDao facturaDao;
	private final FacturaRepositorio facturaRepositorio;

	public ManejadorActualizarEstadoPago(FacturaDao facturaDao, FacturaRepositorio facturaRepositorio) {
		this.facturaDao = facturaDao;
		this.facturaRepositorio = facturaRepositorio;
	}

	@Transactional
	public Factura ejecutar(ComandoActualizarEstadoPago comando) {
		// 1. Buscar la factura
		Factura factura = facturaDao.buscarPorId(comando.getFacturaId()).orElseThrow(
				() -> new IllegalArgumentException("Factura no encontrada con ID: " + comando.getFacturaId()));

		// 2. Parsear y validar el nuevo estado
		EstadoPago nuevoEstado;
		try {
			nuevoEstado = EstadoPago.valueOf(comando.getEstadoPago());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Estado de pago no válido: " + comando.getEstadoPago()
					+ ". Valores permitidos: PENDIENTE, PAGADO, ANULADO");
		}

		// 3. Aplicar transición de dominio
		switch (nuevoEstado) {
		case PAGADO:
			factura.marcarComoPagado();
			break;
		case ANULADO:
			factura.anular();
			break;
		default:
			throw new IllegalArgumentException("No se puede cambiar al estado: " + nuevoEstado);
		}

		// 4. Persistir
		return facturaRepositorio.guardar(factura);
	}
}
