package com.maquimu.aplicacion.autenticacion.comando.manejador;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorCambiarRol {

	private final UsuarioDao usuarioDao;
	private final UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void ejecutar(Long usuarioId, String nuevoRol) {
		Usuario usuario = usuarioDao.buscarPorId(usuarioId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId));

		RolUsuario rolNuevo;
		try {
			rolNuevo = RolUsuario.valueOf(nuevoRol);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Rol no válido: " + nuevoRol);
		}

		usuario.cambiarRol(rolNuevo);
		usuarioRepositorio.guardar(usuario);
	}
}
