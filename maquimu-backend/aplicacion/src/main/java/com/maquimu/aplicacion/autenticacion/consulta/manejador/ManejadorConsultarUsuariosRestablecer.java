package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorConsultarUsuariosRestablecer {

	private final UsuarioDao usuarioDao;

	public List<Usuario> ejecutar() {
		return usuarioDao.buscarPorEstado(EstadoUsuario.RESTABLECER);
	}
}
