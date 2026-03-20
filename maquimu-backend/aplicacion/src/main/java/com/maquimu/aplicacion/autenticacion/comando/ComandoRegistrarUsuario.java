package com.maquimu.aplicacion.autenticacion.comando;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoRegistrarUsuario {
	private String nombre;
	private String apellido;
	private String nombreCompleto;
	private String email;
	private String password;
	private RolUsuario rol;
	private String identificacion; // Requerido para usuarios con rol CLIENTE

	public String getNombreNormalizado() {
		if (nombre != null && !nombre.isBlank()) {
			return nombre.trim();
		}
		String[] partes = separarNombreCompleto();
		return partes[0];
	}

	public String getApellidoNormalizado() {
		if (apellido != null && !apellido.isBlank()) {
			return apellido.trim();
		}
		String[] partes = separarNombreCompleto();
		return partes[1];
	}

	public String getNombreCompletoNormalizado() {
		String nombreNormalizado = getNombreNormalizado();
		String apellidoNormalizado = getApellidoNormalizado();

		if (nombreNormalizado == null || nombreNormalizado.isBlank()) {
			return null;
		}

		if (apellidoNormalizado == null || apellidoNormalizado.isBlank()) {
			return nombreNormalizado;
		}

		return (nombreNormalizado + " " + apellidoNormalizado).trim();
	}

	private String[] separarNombreCompleto() {
		if (nombreCompleto == null || nombreCompleto.isBlank()) {
			return new String[] { null, null };
		}

		String[] partes = nombreCompleto.trim().split("\\s+", 2);
		String nombreParte = partes.length > 0 ? partes[0] : null;
		String apellidoParte = partes.length > 1 ? partes[1] : null;

		return new String[] { nombreParte, apellidoParte };
	}
}
