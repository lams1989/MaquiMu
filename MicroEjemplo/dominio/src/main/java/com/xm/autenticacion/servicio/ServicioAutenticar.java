package com.xm.autenticacion.servicio;

import com.xm.autenticacion.modelo.entidad.Usuario;
import com.xm.dominio.sesion.servicio.ServicioAutenticarUsuario;
import com.xm.dominio.sesioncuentatecnica.modelo.dto.DtoAutenticacion;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;

public class ServicioAutenticar {

	private final VerificadorToken verificadorToken;
	private final ServicioAutenticarUsuario servicioAutenticarUsuario;

	public ServicioAutenticar(VerificadorToken verificadorToken, ServicioAutenticarUsuario servicioAutenticarUsuario) {
		this.verificadorToken = verificadorToken;
		this.servicioAutenticarUsuario = servicioAutenticarUsuario;
	}

	public DtoRespuestaAutenticacion ejecutar(Usuario usuario) {
		validarTokenDeAccesoDelUsuario(usuario);

		DtoAutenticacion dtoAutenticacion = new DtoAutenticacion(usuario.getIdObjeto(), usuario.getNombre(),
				usuario.getNombreCompleto(), usuario.getCorreoElectronico());

		return servicioAutenticarUsuario.ejecutar(dtoAutenticacion);
	}

	private void validarTokenDeAccesoDelUsuario(Usuario usuario) {
		verificadorToken.ejecutar(usuario.getTokenDeAcceso());
	}

}
