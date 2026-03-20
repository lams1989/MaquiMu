package com.maquimu.aplicacion.autenticacion.comando.fabrica;

import org.springframework.stereotype.Component;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;

@Component
public class FabricaUsuario {

	public Usuario crear(ComandoRegistrarUsuario comando, String passwordHash) {
		// Los clientes quedan pendientes de aprobación, los operarios se activan
		// directamente
		EstadoUsuario estado = comando.getRol() == RolUsuario.CLIENTE ? EstadoUsuario.PENDIENTE_APROBACION
				: EstadoUsuario.ACTIVO;

		return Usuario.builder().nombreCompleto(comando.getNombreCompletoNormalizado()).email(comando.getEmail())
				.passwordHash(passwordHash).rol(comando.getRol()).estado(estado).build();
	}
}
