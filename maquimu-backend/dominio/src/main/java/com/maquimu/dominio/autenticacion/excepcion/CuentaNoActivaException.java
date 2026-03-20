package com.maquimu.dominio.autenticacion.excepcion;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;

public class CuentaNoActivaException extends RuntimeException {

	private final EstadoUsuario estado;
	private final String motivoRechazo;

	public CuentaNoActivaException(EstadoUsuario estado, String mensaje) {
		this(estado, mensaje, null);
	}

	public CuentaNoActivaException(EstadoUsuario estado, String mensaje, String motivoRechazo) {
		super(mensaje);
		this.estado = estado;
		this.motivoRechazo = motivoRechazo;
	}

	public EstadoUsuario getEstado() {
		return estado;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}
}
