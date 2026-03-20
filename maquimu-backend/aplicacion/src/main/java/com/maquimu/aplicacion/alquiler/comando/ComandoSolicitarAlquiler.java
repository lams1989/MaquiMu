package com.maquimu.aplicacion.alquiler.comando;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para solicitar un nuevo alquiler. DTO usado en el patrón CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoSolicitarAlquiler {

	private Long clienteId;
	private Long maquinariaId;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
}
