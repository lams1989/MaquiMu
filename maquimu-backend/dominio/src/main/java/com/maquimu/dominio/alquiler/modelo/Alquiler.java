package com.maquimu.dominio.alquiler.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un alquiler de maquinaria.
 * Clase pura de Java sin dependencias de frameworks.
 */
public class Alquiler {

    private Long alquilerId;
    private Long clienteId;
    private Long maquinariaId;
    private Long usuarioId; // Nullable: null cuando está PENDIENTE
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private BigDecimal costoTotal;
    private EstadoAlquiler estado;

    // Constructor vacío
    public Alquiler() {
    }

    // Constructor para crear nueva solicitud (estado PENDIENTE)
    public Alquiler(Long clienteId, Long maquinariaId, LocalDateTime fechaInicio, 
                    LocalDateTime fechaFin, BigDecimal costoTotal) {
        validarFechas(fechaInicio, fechaFin);
        this.clienteId = clienteId;
        this.maquinariaId = maquinariaId;
        this.usuarioId = null; // Sin operario asignado inicialmente
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.estado = EstadoAlquiler.PENDIENTE;
    }

    // Constructor completo
    public Alquiler(Long alquilerId, Long clienteId, Long maquinariaId, Long usuarioId,
                    LocalDateTime fechaInicio, LocalDateTime fechaFin, 
                    BigDecimal costoTotal, EstadoAlquiler estado) {
        this.alquilerId = alquilerId;
        this.clienteId = clienteId;
        this.maquinariaId = maquinariaId;
        this.usuarioId = usuarioId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.estado = estado;
    }

    // Validaciones de negocio
    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }

    // Métodos de transición de estado
    public void aprobar(Long usuarioId) {
        if (this.estado != EstadoAlquiler.PENDIENTE) {
            throw new IllegalStateException("Solo se puede aprobar un alquiler en estado PENDIENTE");
        }
        this.usuarioId = usuarioId;
        this.estado = EstadoAlquiler.APROBADO;
    }

    public void activar() {
        if (this.estado != EstadoAlquiler.APROBADO) {
            throw new IllegalStateException("Solo se puede activar un alquiler en estado APROBADO");
        }
        this.estado = EstadoAlquiler.ACTIVO;
    }

    public void finalizar() {
        if (this.estado != EstadoAlquiler.ACTIVO) {
            throw new IllegalStateException("Solo se puede finalizar un alquiler en estado ACTIVO");
        }
        this.estado = EstadoAlquiler.FINALIZADO;
    }

    public void cancelar() {
        if (this.estado == EstadoAlquiler.FINALIZADO || this.estado == EstadoAlquiler.CANCELADO) {
            throw new IllegalStateException("No se puede cancelar un alquiler ya finalizado o cancelado");
        }
        this.estado = EstadoAlquiler.CANCELADO;
    }

    // Getters y Setters
    public Long getId() {
        return alquilerId;
    }

    public Long getAlquilerId() {
        return alquilerId;
    }

    public void setAlquilerId(Long alquilerId) {
        this.alquilerId = alquilerId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getMaquinariaId() {
        return maquinariaId;
    }

    public void setMaquinariaId(Long maquinariaId) {
        this.maquinariaId = maquinariaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }
}
