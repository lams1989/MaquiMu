package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para finalizar un alquiler activo.
 * Finaliza el alquiler y devuelve la maquinaria a DISPONIBLE.
 * DTO usado en el patrón CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoFinalizarAlquiler {

    private Long alquilerId;
}
