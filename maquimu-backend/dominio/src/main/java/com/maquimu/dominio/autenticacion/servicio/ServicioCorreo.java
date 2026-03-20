package com.maquimu.dominio.autenticacion.servicio;

public interface ServicioCorreo {
	void enviarPasswordTemporal(String destinatario, String nombreUsuario, String passwordTemporal);
}
