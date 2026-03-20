package com.maquimu.dominio.factura.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad de dominio que representa una factura generada para un alquiler
 * finalizado. Clase pura de Java sin dependencias de frameworks.
 */
public class Factura {

	private Long facturaId;
	private Long alquilerId;
	private Long clienteId;
	private LocalDate fechaEmision;
	private BigDecimal monto;
	private EstadoPago estadoPago;

	public Factura() {
	}

	/**
	 * Constructor para crear una nueva factura (sin ID, asignado por BD).
	 */
	public Factura(Long alquilerId, Long clienteId, BigDecimal monto) {
		validarDatos(alquilerId, clienteId, monto);
		this.alquilerId = alquilerId;
		this.clienteId = clienteId;
		this.fechaEmision = LocalDate.now();
		this.monto = monto;
		this.estadoPago = EstadoPago.PENDIENTE;
	}

	/**
	 * Constructor completo (para reconstrucción desde persistencia).
	 */
	public Factura(Long facturaId, Long alquilerId, Long clienteId, LocalDate fechaEmision, BigDecimal monto,
			EstadoPago estadoPago) {
		this.facturaId = facturaId;
		this.alquilerId = alquilerId;
		this.clienteId = clienteId;
		this.fechaEmision = fechaEmision;
		this.monto = monto;
		this.estadoPago = estadoPago;
	}

	// Validaciones de negocio
	private void validarDatos(Long alquilerId, Long clienteId, BigDecimal monto) {
		if (alquilerId == null) {
			throw new IllegalArgumentException("El ID del alquiler es obligatorio");
		}
		if (clienteId == null) {
			throw new IllegalArgumentException("El ID del cliente es obligatorio");
		}
		if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("El monto debe ser mayor a cero");
		}
	}

	// Métodos de transición de estado
	public void marcarComoPagado() {
		if (this.estadoPago != EstadoPago.PENDIENTE) {
			throw new IllegalStateException("Solo se puede marcar como pagada una factura en estado PENDIENTE");
		}
		this.estadoPago = EstadoPago.PAGADO;
	}

	public void anular() {
		if (this.estadoPago == EstadoPago.ANULADO) {
			throw new IllegalStateException("La factura ya está anulada");
		}
		this.estadoPago = EstadoPago.ANULADO;
	}

	// Getters
	public Long getFacturaId() {
		return facturaId;
	}

	public Long getAlquilerId() {
		return alquilerId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public EstadoPago getEstadoPago() {
		return estadoPago;
	}

	// Setters
	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}

	public void setAlquilerId(Long alquilerId) {
		this.alquilerId = alquilerId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public void setEstadoPago(EstadoPago estadoPago) {
		this.estadoPago = estadoPago;
	}
}
