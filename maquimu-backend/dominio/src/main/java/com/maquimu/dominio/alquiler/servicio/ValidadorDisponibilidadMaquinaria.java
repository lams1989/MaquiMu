package com.maquimu.dominio.alquiler.servicio;

import java.time.LocalDateTime;
import java.util.List;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;

/**
 * Servicio de dominio para validar disponibilidad de maquinaria. Reutilizable
 * en múltiples casos de uso (HU 06, 07, 08, 09).
 */
public class ValidadorDisponibilidadMaquinaria {

	private final AlquilerDao alquilerDao;

	public ValidadorDisponibilidadMaquinaria(AlquilerDao alquilerDao) {
		this.alquilerDao = alquilerDao;
	}

	/**
	 * Verifica si una maquinaria está disponible en un rango de fechas. Una
	 * maquinaria NO está disponible si tiene alquileres en estado APROBADO, ACTIVO
	 * o PENDIENTE_EXTENSION que se solapan con el rango de fechas solicitado.
	 * 
	 * @param maquinariaId ID de la maquinaria a verificar
	 * @param fechaInicio  Fecha de inicio del período solicitado
	 * @param fechaFin     Fecha de fin del período solicitado
	 * @return true si está disponible, false si no
	 */
	public boolean estaDisponible(Long maquinariaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		return estaDisponible(maquinariaId, fechaInicio, fechaFin, null);
	}

	/**
	 * Verifica si una maquinaria está disponible en un rango de fechas, excluyendo
	 * opcionalmente un alquiler específico (para extensiones).
	 */
	public boolean estaDisponible(Long maquinariaId, LocalDateTime fechaInicio, LocalDateTime fechaFin,
			Long excluirAlquilerId) {
		if (maquinariaId == null || fechaInicio == null || fechaFin == null) {
			throw new IllegalArgumentException("MaquinariaId y fechas son obligatorios");
		}

		// Buscar alquileres que se solapan con el rango de fechas
		List<Alquiler> alquileresEnRango = alquilerDao.buscarPorMaquinariaYRangoFechas(maquinariaId, fechaInicio,
				fechaFin);

		// Verificar si alguno de los alquileres está en estado que bloquea
		// disponibilidad
		for (Alquiler alquiler : alquileresEnRango) {
			// Excluir el propio alquiler si se especifica (para extensiones)
			if (excluirAlquilerId != null && excluirAlquilerId.equals(alquiler.getAlquilerId())) {
				continue;
			}
			if (bloqueaDisponibilidad(alquiler.getEstado())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Determina si un estado de alquiler bloquea la disponibilidad de la
	 * maquinaria. Estados que bloquean: APROBADO, ACTIVO, PENDIENTE_EXTENSION
	 * Estados que NO bloquean: PENDIENTE, FINALIZADO, CANCELADO, RECHAZADO
	 */
	private boolean bloqueaDisponibilidad(EstadoAlquiler estado) {
		return estado == EstadoAlquiler.APROBADO || estado == EstadoAlquiler.ACTIVO
				|| estado == EstadoAlquiler.PENDIENTE_EXTENSION;
	}
}
