package com.maquimu.aplicacion.factura.consulta.manejador;

import org.springframework.stereotype.Service;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.dominio.factura.puerto.salida.GeneradorFacturaPort;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;

/**
 * Manejador de consulta para descargar el PDF de una factura. Recopila todos
 * los datos necesarios y delega al puerto generador de PDF.
 */
@Service
public class ManejadorDescargarFactura {

	private final FacturaDao facturaDao;
	private final AlquilerDao alquilerDao;
	private final ClienteDao clienteDao;
	private final MaquinariaDao maquinariaDao;
	private final GeneradorFacturaPort generadorFacturaPort;

	public ManejadorDescargarFactura(FacturaDao facturaDao, AlquilerDao alquilerDao, ClienteDao clienteDao,
			MaquinariaDao maquinariaDao, GeneradorFacturaPort generadorFacturaPort) {
		this.facturaDao = facturaDao;
		this.alquilerDao = alquilerDao;
		this.clienteDao = clienteDao;
		this.maquinariaDao = maquinariaDao;
		this.generadorFacturaPort = generadorFacturaPort;
	}

	/**
	 * Genera el PDF de una factura al vuelo.
	 * 
	 * @param facturaId ID de la factura
	 * @return byte[] con el contenido del PDF
	 */
	public byte[] ejecutar(Long facturaId) {
		// 1. Buscar factura
		Factura factura = facturaDao.buscarPorId(facturaId)
				.orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + facturaId));

		// 2. Buscar datos relacionados
		Alquiler alquiler = alquilerDao.buscarPorId(factura.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + factura.getAlquilerId()));

		Cliente cliente = clienteDao.buscarPorId(factura.getClienteId()).orElseThrow(
				() -> new IllegalArgumentException("Cliente no encontrado con ID: " + factura.getClienteId()));

		Maquinaria maquinaria = maquinariaDao.buscarPorId(alquiler.getMaquinariaId()).orElseThrow(
				() -> new IllegalArgumentException("Maquinaria no encontrada con ID: " + alquiler.getMaquinariaId()));

		// 3. Generar PDF
		return generadorFacturaPort.generarFacturaPdf(factura, alquiler, cliente, maquinaria);
	}
}
