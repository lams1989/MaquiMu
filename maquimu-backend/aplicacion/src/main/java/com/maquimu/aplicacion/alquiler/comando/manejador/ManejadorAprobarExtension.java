package com.maquimu.aplicacion.alquiler.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarExtension;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;

/**
 * Manejador del comando para aprobar extensión de un alquiler. Transición:
 * PENDIENTE_EXTENSION -> ACTIVO (con fechas y costo actualizados).
 */
@Service
public class ManejadorAprobarExtension {

	private final AlquilerDao alquilerDao;
	private final AlquilerRepositorio alquilerRepositorio;

	public ManejadorAprobarExtension(AlquilerDao alquilerDao, AlquilerRepositorio alquilerRepositorio) {
		this.alquilerDao = alquilerDao;
		this.alquilerRepositorio = alquilerRepositorio;
	}

	@Transactional
	public Alquiler ejecutar(ComandoAprobarExtension comando) {
		Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

		alquiler.aprobarExtension();

		return alquilerRepositorio.guardar(alquiler);
	}
}
