package com.maquimu.aplicacion.autenticacion.comando.manejador;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorAprobarUsuario {

	private final UsuarioDao usuarioDao;
	private final UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void ejecutar(Long usuarioId) {
		Usuario usuario = usuarioDao.buscarPorId(usuarioId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId));

		usuario.aprobar();
		usuarioRepositorio.guardar(usuario);
	}
}
