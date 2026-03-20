package com.maquimu.aplicacion.dashboard.consulta.manejador;

import org.springframework.stereotype.Service;

import com.maquimu.aplicacion.dashboard.consulta.ConsultaDashboardCliente;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;

/**
 * Manejador que agrega datos filtrados por cliente para construir el dashboard
 * del cliente.
 */
@Service
public class ManejadorDashboardCliente {

	private final AlquilerDao alquilerDao;
	private final FacturaDao facturaDao;

	public ManejadorDashboardCliente(AlquilerDao alquilerDao, FacturaDao facturaDao) {
		this.alquilerDao = alquilerDao;
		this.facturaDao = facturaDao;
	}

	/**
	 * Ejecuta la consulta del dashboard del cliente y retorna los KPIs.
	 *
	 * @param clienteId ID del cliente autenticado
	 * @return DTO con alquileres activos y facturas pendientes del cliente
	 */
	public ConsultaDashboardCliente ejecutar(Long clienteId) {
		long alquileresActivos = alquilerDao.contarPorClienteYEstado(clienteId, EstadoAlquiler.ACTIVO);
		long facturasPendientes = facturaDao.contarPorClienteYEstadoPago(clienteId, EstadoPago.PENDIENTE);

		return ConsultaDashboardCliente.builder().alquileresActivos(alquileresActivos)
				.facturasPendientes(facturasPendientes).build();
	}
}
