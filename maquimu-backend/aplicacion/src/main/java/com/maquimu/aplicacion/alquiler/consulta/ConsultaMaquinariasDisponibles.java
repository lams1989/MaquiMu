package com.maquimu.aplicacion.alquiler.consulta;

import java.time.LocalDateTime;

/**
 * Consulta para obtener maquinarias disponibles en un rango de fechas.
 */
public class ConsultaMaquinariasDisponibles {

	private final LocalDateTime fechaInicio;
	private final LocalDateTime fechaFin;

	public ConsultaMaquinariasDisponibles(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		validarFechas(fechaInicio, fechaFin);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		if (fechaInicio == null || fechaFin == null) {
			throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
		}
		if (fechaInicio.isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser anterior a hoy");
		}
		if (fechaFin.isBefore(fechaInicio)) {
			throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
		}
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
}
