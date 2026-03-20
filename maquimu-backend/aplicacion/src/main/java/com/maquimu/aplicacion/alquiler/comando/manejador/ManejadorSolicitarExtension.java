package com.maquimu.aplicacion.alquiler.comando.manejador;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarExtension;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import com.maquimu.dominio.alquiler.servicio.CalculadorCostoAlquiler;
import com.maquimu.dominio.alquiler.servicio.ValidadorDisponibilidadMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;

/**
 * Manejador del comando para solicitar extensión de un alquiler activo.
 * Transición: ACTIVO -> PENDIENTE_EXTENSION. Valida disponibilidad y calcula
 * costo adicional.
 */
@Service
public class ManejadorSolicitarExtension {

	private final AlquilerDao alquilerDao;
	private final AlquilerRepositorio alquilerRepositorio;
	private final MaquinariaDao maquinariaDao;
	private final ValidadorDisponibilidadMaquinaria validadorDisponibilidad;
	private final CalculadorCostoAlquiler calculadorCosto;

	public ManejadorSolicitarExtension(AlquilerDao alquilerDao, AlquilerRepositorio alquilerRepositorio,
			MaquinariaDao maquinariaDao, ValidadorDisponibilidadMaquinaria validadorDisponibilidad,
			CalculadorCostoAlquiler calculadorCosto) {
		this.alquilerDao = alquilerDao;
		this.alquilerRepositorio = alquilerRepositorio;
		this.maquinariaDao = maquinariaDao;
		this.validadorDisponibilidad = validadorDisponibilidad;
		this.calculadorCosto = calculadorCosto;
	}

	@Transactional
	public Alquiler ejecutar(ComandoSolicitarExtension comando) {
		// 1. Buscar el alquiler
		Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

		// 2. Obtener la maquinaria para calcular costo
		Maquinaria maquinaria = maquinariaDao.buscarPorId(alquiler.getMaquinariaId()).orElseThrow(
				() -> new IllegalArgumentException("Maquinaria no encontrada con ID: " + alquiler.getMaquinariaId()));

		// 3. Validar disponibilidad en el rango extendido (excluyendo el propio
		// alquiler)
		boolean disponible = validadorDisponibilidad.estaDisponible(alquiler.getMaquinariaId(), alquiler.getFechaFin(), // desde
																														// la
																														// fecha
																														// fin
																														// actual
				comando.getNuevaFechaFin(), // hasta la nueva fecha fin
				alquiler.getAlquilerId() // excluir este alquiler de la validación
		);

		if (!disponible) {
			throw new IllegalStateException("La maquinaria no está disponible para las fechas solicitadas.");
		}

		// 4. Calcular costo adicional (desde fechaFin actual hasta nuevaFechaFin)
		BigDecimal costoAdicional = calculadorCosto.calcularCostoPorDias(maquinaria, alquiler.getFechaFin(),
				comando.getNuevaFechaFin());

		// 5. Aplicar transición de estado en el dominio
		alquiler.solicitarExtension(comando.getNuevaFechaFin(), costoAdicional);

		// 6. Persistir y retornar
		return alquilerRepositorio.guardar(alquiler);
	}
}
