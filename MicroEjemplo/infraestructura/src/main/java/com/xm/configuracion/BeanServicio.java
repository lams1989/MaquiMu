package com.xm.configuracion;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xm.autenticacion.servicio.ServicioAutenticar;
import com.xm.autenticacion.servicio.ServicioRefrescarToken;
import com.xm.dominio.sesion.servicio.ServicioAutenticarUsuario;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;
import com.xm.dominio.token.servicio.ServicioCrearTokenAplicacion;
import com.xm.escenario.servicio.ServicioValidarEscenario;
import com.xm.rol.puerto.dao.DaoRol;

@Configuration
public class BeanServicio {

	@Bean
	public ServicioAutenticar servicioAutenticar(
			@Qualifier("verificadorTokenJwtOAuth2Microsoft") VerificadorToken verificadorToken,
			ServicioAutenticarUsuario servicioAutenticarUsuario) {
		return new ServicioAutenticar(verificadorToken, servicioAutenticarUsuario);
	}

	@Bean
	public ServicioRefrescarToken servicioRefrescarToken(ServicioCrearTokenAplicacion servicioCrearTokenAplicacion,
			VerificadorToken verificadorToken) {
		return new ServicioRefrescarToken(servicioCrearTokenAplicacion, verificadorToken);
	}

	@Bean
	public ServicioValidarEscenario servicioValidarEscenario(DaoRol daoRol) {
		return new ServicioValidarEscenario(daoRol);
	}
}
