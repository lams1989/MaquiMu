package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para rechazar un alquiler pendiente. DTO usado en el patrón CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRechazarAlquiler {

	private Long alquilerId;
	private String motivoRechazo; // Opcional
}
