package com.maquimu.aplicacion.alquiler.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Comando para solicitar un nuevo alquiler.
 * DTO usado en el patrón CQRS.
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
