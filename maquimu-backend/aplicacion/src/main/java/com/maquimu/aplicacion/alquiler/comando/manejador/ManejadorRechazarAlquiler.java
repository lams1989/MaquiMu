package com.maquimu.aplicacion.alquiler.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;

/**
 * Manejador del comando para rechazar un alquiler. Transición: PENDIENTE ->
 * RECHAZADO. Permite registrar un motivo de rechazo opcional.
 */
@Service
public class ManejadorRechazarAlquiler {

	private final AlquilerDao alquilerDao;
	private final AlquilerRepositorio alquilerRepositorio;

	public ManejadorRechazarAlquiler(AlquilerDao alquilerDao, AlquilerRepositorio alquilerRepositorio) {
		this.alquilerDao = alquilerDao;
		this.alquilerRepositorio = alquilerRepositorio;
	}

	@Transactional
	public Alquiler ejecutar(ComandoRechazarAlquiler comando) {
		Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

		alquiler.rechazar(comando.getMotivoRechazo());

		return alquilerRepositorio.guardar(alquiler);
	}
}
