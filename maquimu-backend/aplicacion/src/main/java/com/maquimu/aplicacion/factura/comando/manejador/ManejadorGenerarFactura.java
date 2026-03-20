package com.maquimu.aplicacion.factura.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.factura.comando.ComandoGenerarFactura;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.dominio.factura.puerto.repositorio.FacturaRepositorio;

/**
 * Manejador del comando para generar una factura. Reglas de negocio: - El
 * alquiler debe existir y estar en estado FINALIZADO. - No puede existir una
 * factura previa para el mismo alquiler.
 */
@Service
public class ManejadorGenerarFactura {

	private final AlquilerDao alquilerDao;
	private final ClienteDao clienteDao;
	private final FacturaDao facturaDao;
	private final FacturaRepositorio facturaRepositorio;

	public ManejadorGenerarFactura(AlquilerDao alquilerDao, ClienteDao clienteDao, FacturaDao facturaDao,
			FacturaRepositorio facturaRepositorio) {
		this.alquilerDao = alquilerDao;
		this.clienteDao = clienteDao;
		this.facturaDao = facturaDao;
		this.facturaRepositorio = facturaRepositorio;
	}

	@Transactional
	public Factura ejecutar(ComandoGenerarFactura comando) {
		// 1. Buscar el alquiler
		Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

		// 2. Validar que el alquiler esté FINALIZADO
		if (alquiler.getEstado() != EstadoAlquiler.FINALIZADO) {
			throw new IllegalStateException("Solo se puede generar factura para alquileres en estado FINALIZADO. "
					+ "Estado actual: " + alquiler.getEstado());
		}

		// 3. Verificar que no exista factura previa
		if (facturaDao.buscarPorAlquilerId(alquiler.getId()).isPresent()) {
			throw new IllegalStateException("Ya existe una factura para el alquiler ID: " + alquiler.getId());
		}

		Cliente cliente = clienteDao.buscarPorId(alquiler.getClienteId()).orElseThrow(
				() -> new IllegalArgumentException("Cliente no encontrado con ID: " + alquiler.getClienteId()));

		if (cliente.getTelefono() == null || cliente.getTelefono().isBlank() || cliente.getDireccion() == null
				|| cliente.getDireccion().isBlank()) {
			throw new IllegalStateException(
					"Para generar factura el cliente debe tener tel\u00e9fono y direcci\u00f3n registrados");
		}

		// 4. Crear la factura
		Factura factura = new Factura(alquiler.getId(), alquiler.getClienteId(), alquiler.getCostoTotal());

		// 5. Persistir
		return facturaRepositorio.guardar(factura);
	}
}
