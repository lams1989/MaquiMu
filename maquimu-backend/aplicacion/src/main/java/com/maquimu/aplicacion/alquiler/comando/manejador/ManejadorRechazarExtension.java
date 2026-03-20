package com.maquimu.aplicacion.alquiler.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarExtension;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;

/**
 * Manejador del comando para rechazar extensión de un alquiler. Transición:
 * PENDIENTE_EXTENSION -> ACTIVO (sin cambios en fechas/costo).
 */
@Service
public class ManejadorRechazarExtension {

	private final AlquilerDao alquilerDao;
	private final AlquilerRepositorio alquilerRepositorio;

	public ManejadorRechazarExtension(AlquilerDao alquilerDao, AlquilerRepositorio alquilerRepositorio) {
		this.alquilerDao = alquilerDao;
		this.alquilerRepositorio = alquilerRepositorio;
	}

	@Transactional
	public Alquiler ejecutar(ComandoRechazarExtension comando) {
		Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

		alquiler.rechazarExtension(comando.getMotivo());

		return alquilerRepositorio.guardar(alquiler);
	}
}
