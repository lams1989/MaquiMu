package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para rechazar la extensión de un alquiler. DTO usado en el patrón
 * CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRechazarExtension {

	private Long alquilerId;
	private String motivo; // Opcional: motivo del rechazo
}
