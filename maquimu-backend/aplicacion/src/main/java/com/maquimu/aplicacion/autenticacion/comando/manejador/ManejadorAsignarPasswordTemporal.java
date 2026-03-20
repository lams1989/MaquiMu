package com.maquimu.aplicacion.autenticacion.comando.manejador;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.autenticacion.comando.ComandoAsignarPasswordTemporal;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.servicio.ServicioCorreo;
import com.maquimu.dominio.autenticacion.servicio.ServicioHashing;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorAsignarPasswordTemporal {

	private final UsuarioDao usuarioDao;
	private final UsuarioRepositorio usuarioRepositorio;
	private final ServicioHashing servicioHashing;
	private final ServicioCorreo servicioCorreo;

	@Transactional
	public void ejecutar(Long usuarioId, ComandoAsignarPasswordTemporal comando) {
		Usuario usuario = usuarioDao.buscarPorId(usuarioId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId));

		String hash = servicioHashing.hashear(comando.getPasswordTemporal());
		usuario.resolverRestablecimiento(hash);
		usuarioRepositorio.guardar(usuario);

		// Enviar email con la contraseña temporal
		servicioCorreo.enviarPasswordTemporal(usuario.getEmail(), usuario.getNombreCompleto(),
				comando.getPasswordTemporal());
	}
}
