package com.maquimu.aplicacion.autenticacion.comando.manejador;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.autenticacion.comando.ComandoSolicitarRestablecimiento;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorSolicitarRestablecimiento {

	private final UsuarioDao usuarioDao;
	private final UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void ejecutar(ComandoSolicitarRestablecimiento comando) {
		Usuario usuario = usuarioDao.buscarPorEmail(comando.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("No existe una cuenta asociada a este email"));

		usuario.solicitarRestablecimiento();
		usuarioRepositorio.guardar(usuario);
	}
}
