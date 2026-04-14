package com.maquimu.aplicacion.alquiler.comando.manejador;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import com.maquimu.dominio.alquiler.servicio.ServicioCalculadorCostoAlquiler;
import com.maquimu.dominio.alquiler.servicio.ServicioValidadorDisponibilidadMaquinaria;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;

/**
 * Manejador de aplicación que orquesta la lógica de gestión de alquileres.
 * Coordina servicios de dominio, DAOs y repositorios.
 */
@Service
public class ManejadorGestionAlquiler {

	private final AlquilerRepositorio alquilerRepositorio;
	private final ClienteDao clienteDao;
	private final MaquinariaDao maquinariaDao;
	private final ServicioCalculadorCostoAlquiler calculadorCosto;
	private final ServicioValidadorDisponibilidadMaquinaria validadorDisponibilidad;

	public ManejadorGestionAlquiler(AlquilerRepositorio alquilerRepositorio, ClienteDao clienteDao,
			MaquinariaDao maquinariaDao, ServicioCalculadorCostoAlquiler calculadorCosto,
			ServicioValidadorDisponibilidadMaquinaria validadorDisponibilidad) {
		this.alquilerRepositorio = alquilerRepositorio;
		this.clienteDao = clienteDao;
		this.maquinariaDao = maquinariaDao;
		this.calculadorCosto = calculadorCosto;
		this.validadorDisponibilidad = validadorDisponibilidad;
	}

	/**
	 * Procesa una solicitud de alquiler. Valida cliente, maquinaria,
	 * disponibilidad, calcula costo y persiste.
	 * 
	 * @param comando Datos de la solicitud
	 * @return Alquiler creado con estado PENDIENTE
	 */
	public Alquiler solicitarAlquiler(ComandoSolicitarAlquiler comando) {
		// 1. Validar que el cliente existe
		clienteDao.buscarPorId(comando.getClienteId()).orElseThrow(
				() -> new IllegalArgumentException("Cliente no encontrado con ID: " + comando.getClienteId()));

		// 2. Obtener y validar que la maquinaria existe
		Maquinaria maquinaria = maquinariaDao.buscarPorId(comando.getMaquinariaId()).orElseThrow(
				() -> new IllegalArgumentException("Maquinaria no encontrada con ID: " + comando.getMaquinariaId()));

		// 3. Validar disponibilidad de la maquinaria en el rango de fechas
		boolean disponible = validadorDisponibilidad.estaDisponible(comando.getMaquinariaId(), comando.getFechaInicio(),
				comando.getFechaFin());

		if (!disponible) {
			throw new IllegalStateException("La maquinaria no está disponible en las fechas seleccionadas");
		}

		// 4. Calcular costo estimado
		BigDecimal costoTotal = calculadorCosto.calcularCostoPorDias(maquinaria, comando.getFechaInicio(),
				comando.getFechaFin());

		// 5. Crear alquiler con estado PENDIENTE
		Alquiler alquiler = new Alquiler(comando.getClienteId(), comando.getMaquinariaId(), comando.getFechaInicio(),
				comando.getFechaFin(), costoTotal);

		// 6. Persistir y retornar
		return alquilerRepositorio.guardar(alquiler);
	}
}
