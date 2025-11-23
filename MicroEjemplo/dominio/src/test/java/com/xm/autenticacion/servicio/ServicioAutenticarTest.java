package com.xm.autenticacion.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.xm.autenticacion.modelo.entidad.Usuario;
import com.xm.autenticacion.servicio.testdatabuilder.DtoRespuestaAutenticacionTestDataBuilder;
import com.xm.autenticacion.servicio.testdatabuilder.UsuarioTestDataBuilder;
import com.xm.dominio.sesion.servicio.ServicioAutenticarUsuario;
import com.xm.dominio.sesioncuentatecnica.modelo.dto.DtoAutenticacion;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;
import com.xm.dominio.token.modelo.modelo.TokenVerificado;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;

class ServicioAutenticarTest {

	private static final String ISS = "iss";
	private static final String SIMPLEX = "Simplex";
	private static final String AUDIENCE = "audience";

	@Captor
	private ArgumentCaptor<DtoAutenticacion> captorAutenticacion;
	private VerificadorToken verificadorToken;
	private ServicioAutenticarUsuario servicioAutenticarUsuario;
	private ServicioAutenticar servicioAutenticar;

	@BeforeEach
	void setUp() {
		verificadorToken = mock(VerificadorToken.class);
		servicioAutenticarUsuario = mock(ServicioAutenticarUsuario.class);

		servicioAutenticar = new ServicioAutenticar(verificadorToken, servicioAutenticarUsuario);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void debeEjecutarLaAutenticacionComoUsuarioCorrectamenteTest() {

		Usuario usuario = new UsuarioTestDataBuilder().construir();
		DtoRespuestaAutenticacion dtoRespuestaAutenticacion = new DtoRespuestaAutenticacionTestDataBuilder().construir();

		when(verificadorToken.ejecutar(anyString()))
				.thenReturn(TokenVerificado.valido(Map.of(ISS, SIMPLEX, AUDIENCE, SIMPLEX.toLowerCase())));

		when(servicioAutenticarUsuario.ejecutar(Mockito.any(DtoAutenticacion.class)))
				.thenReturn(dtoRespuestaAutenticacion);

		DtoRespuestaAutenticacion respuestaAutenticacion = servicioAutenticar.ejecutar(usuario);

		verify(verificadorToken).ejecutar(anyString());
		verify(servicioAutenticarUsuario).ejecutar(captorAutenticacion.capture());

		assertEquals(dtoRespuestaAutenticacion.getTokenDeAcceso(), respuestaAutenticacion.getTokenDeAcceso());
		assertEquals(dtoRespuestaAutenticacion.getTokenDeRefresco(), respuestaAutenticacion.getTokenDeRefresco());
		assertEquals(usuario.getIdObjeto(), captorAutenticacion.getValue().getIdObjeto());
	}

}
