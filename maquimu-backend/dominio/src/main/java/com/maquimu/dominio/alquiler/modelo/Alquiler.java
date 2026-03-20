package com.maquimu.dominio.alquiler.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa un alquiler de maquinaria. Clase pura de
 * Java sin dependencias de frameworks.
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
	private String motivoRechazo; // Nullable: solo cuando estado es RECHAZADO
	private LocalDateTime fechaFinSolicitada; // Nullable: nueva fecha fin solicitada en extensión
	private BigDecimal costoAdicional; // Nullable: costo adicional de la extensión

	// Constructor vacío
	public Alquiler() {
	}

	// Constructor para crear nueva solicitud (estado PENDIENTE)
	public Alquiler(Long clienteId, Long maquinariaId, LocalDateTime fechaInicio, LocalDateTime fechaFin,
			BigDecimal costoTotal) {
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
	public Alquiler(Long alquilerId, Long clienteId, Long maquinariaId, Long usuarioId, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, BigDecimal costoTotal, EstadoAlquiler estado) {
		this.alquilerId = alquilerId;
		this.clienteId = clienteId;
		this.maquinariaId = maquinariaId;
		this.usuarioId = usuarioId;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.costoTotal = costoTotal;
		this.estado = estado;
	}

	// Constructor completo con motivo de rechazo
	public Alquiler(Long alquilerId, Long clienteId, Long maquinariaId, Long usuarioId, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, BigDecimal costoTotal, EstadoAlquiler estado, String motivoRechazo) {
		this.alquilerId = alquilerId;
		this.clienteId = clienteId;
		this.maquinariaId = maquinariaId;
		this.usuarioId = usuarioId;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.costoTotal = costoTotal;
		this.estado = estado;
		this.motivoRechazo = motivoRechazo;
	}

	// Constructor completo con campos de extensión
	public Alquiler(Long alquilerId, Long clienteId, Long maquinariaId, Long usuarioId, LocalDateTime fechaInicio,
			LocalDateTime fechaFin, BigDecimal costoTotal, EstadoAlquiler estado, String motivoRechazo,
			LocalDateTime fechaFinSolicitada, BigDecimal costoAdicional) {
		this.alquilerId = alquilerId;
		this.clienteId = clienteId;
		this.maquinariaId = maquinariaId;
		this.usuarioId = usuarioId;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.costoTotal = costoTotal;
		this.estado = estado;
		this.motivoRechazo = motivoRechazo;
		this.fechaFinSolicitada = fechaFinSolicitada;
		this.costoAdicional = costoAdicional;
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

	public void rechazar(String motivoRechazo) {
		if (this.estado != EstadoAlquiler.PENDIENTE) {
			throw new IllegalStateException("Solo se puede rechazar un alquiler en estado PENDIENTE");
		}
		this.motivoRechazo = motivoRechazo;
		this.estado = EstadoAlquiler.RECHAZADO;
	}

	public void cancelar() {
		if (this.estado == EstadoAlquiler.FINALIZADO || this.estado == EstadoAlquiler.CANCELADO) {
			throw new IllegalStateException("No se puede cancelar un alquiler ya finalizado o cancelado");
		}
		this.estado = EstadoAlquiler.CANCELADO;
	}

	// Métodos de transición para extensión de alquiler
	public void solicitarExtension(LocalDateTime nuevaFechaFin, BigDecimal costoAdicional) {
		if (this.estado != EstadoAlquiler.ACTIVO) {
			throw new IllegalStateException("Solo se puede solicitar extensión de un alquiler en estado ACTIVO");
		}
		if (nuevaFechaFin == null || !nuevaFechaFin.isAfter(this.fechaFin)) {
			throw new IllegalArgumentException("La nueva fecha de fin debe ser posterior a la fecha de fin actual");
		}
		this.fechaFinSolicitada = nuevaFechaFin;
		this.costoAdicional = costoAdicional;
		this.estado = EstadoAlquiler.PENDIENTE_EXTENSION;
	}

	public void aprobarExtension() {
		if (this.estado != EstadoAlquiler.PENDIENTE_EXTENSION) {
			throw new IllegalStateException(
					"Solo se puede aprobar extensión de un alquiler en estado PENDIENTE_EXTENSION");
		}
		this.fechaFin = this.fechaFinSolicitada;
		this.costoTotal = this.costoTotal.add(this.costoAdicional);
		this.fechaFinSolicitada = null;
		this.costoAdicional = null;
		this.motivoRechazo = null;
		this.estado = EstadoAlquiler.ACTIVO;
	}

	public void rechazarExtension(String motivo) {
		if (this.estado != EstadoAlquiler.PENDIENTE_EXTENSION) {
			throw new IllegalStateException(
					"Solo se puede rechazar extensión de un alquiler en estado PENDIENTE_EXTENSION");
		}
		this.fechaFinSolicitada = null;
		this.costoAdicional = null;
		this.motivoRechazo = motivo;
		this.estado = EstadoAlquiler.ACTIVO;
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

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public LocalDateTime getFechaFinSolicitada() {
		return fechaFinSolicitada;
	}

	public void setFechaFinSolicitada(LocalDateTime fechaFinSolicitada) {
		this.fechaFinSolicitada = fechaFinSolicitada;
	}

	public BigDecimal getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(BigDecimal costoAdicional) {
		this.costoAdicional = costoAdicional;
	}
}
