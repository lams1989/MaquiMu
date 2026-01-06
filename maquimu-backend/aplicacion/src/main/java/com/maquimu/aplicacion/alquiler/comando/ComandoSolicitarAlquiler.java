package com.maquimu.aplicacion.alquiler.comando;

import java.time.LocalDateTime;

/**
 * Comando para solicitar un nuevo alquiler.
 * DTO inmutable usado en el patrón CQRS.
 */
public class ComandoSolicitarAlquiler {

    private final Long clienteId;
    private final Long maquinariaId;
    private final LocalDateTime fechaInicio;
    private final LocalDateTime fechaFin;

    public ComandoSolicitarAlquiler(Long clienteId, Long maquinariaId, 
                                     LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.clienteId = clienteId;
        this.maquinariaId = maquinariaId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Long getMaquinariaId() {
        return maquinariaId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
}
