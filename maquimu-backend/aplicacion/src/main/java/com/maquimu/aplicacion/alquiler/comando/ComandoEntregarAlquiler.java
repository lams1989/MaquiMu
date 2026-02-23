package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para registrar la entrega de maquinaria.
 * Activa el alquiler y marca la maquinaria como ALQUILADO.
 * DTO usado en el patrón CQRS.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoEntregarAlquiler {

    private Long alquilerId;
}
