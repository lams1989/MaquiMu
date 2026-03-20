package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para aprobar un alquiler pendiente. DTO usado en el patrón CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoAprobarAlquiler {

	private Long alquilerId;
	private Long usuarioId; // ID del operario que aprueba
}
