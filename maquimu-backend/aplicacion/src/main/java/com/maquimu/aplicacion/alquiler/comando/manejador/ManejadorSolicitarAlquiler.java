package com.maquimu.aplicacion.alquiler.comando.manejador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;

/**
 * Manejador del comando para solicitar alquiler. Delega la lógica al
 * ServicioGestionAlquiler.
 */
@Service
public class ManejadorSolicitarAlquiler {

	private final ManejadorGestionAlquiler manejadorGestionAlquiler;

	public ManejadorSolicitarAlquiler(ManejadorGestionAlquiler manejadorGestionAlquiler) {
		this.manejadorGestionAlquiler = manejadorGestionAlquiler;
	}

	@Transactional
	public Alquiler ejecutar(ComandoSolicitarAlquiler comando) {
		return manejadorGestionAlquiler.solicitarAlquiler(comando);
	}
}
