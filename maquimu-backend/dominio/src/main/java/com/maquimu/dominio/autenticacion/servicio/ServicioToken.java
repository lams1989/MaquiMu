package com.maquimu.dominio.autenticacion.servicio;

import com.maquimu.dominio.autenticacion.modelo.Usuario;

/**
 * Puerto de servicio para la generación y validación de tokens de
 * autenticación.
 */
public interface ServicioToken {

	/**
	 * Genera un token de autenticación para un usuario.
	 * 
	 * @param usuario El usuario para el cual generar el token.
	 * @return El token de autenticación como una cadena de texto.
	 */
	String generarToken(Usuario usuario);

	/**
	 * Extrae el ID del usuario del token.
	 * 
	 * @param token El token de autenticación.
	 * @return El ID del usuario.
	 */
	Long obtenerUsuarioIdDelToken(String token);

	/**
	 * Valida si un token es válido.
	 * 
	 * @param token El token de autenticación.
	 * @return true si el token es válido, false en caso contrario.
	 */
	boolean esTokenValido(String token);
}