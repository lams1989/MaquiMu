package com.xm.autenticacion.servicio;

import static com.xm.core.BasePrueba.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xm.autenticacion.modelo.entidad.RefrescarToken;
import com.xm.autenticacion.servicio.testdatabuilder.DtoRespuestaAutenticacionTestDataBuilder;
import com.xm.autenticacion.servicio.testdatabuilder.RefrescarTokenTestDataBuilder;
import com.xm.dominio.excepcion.ExcepcionDeProceso;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;
import com.xm.dominio.token.modelo.enums.ClaimsEnum;
import com.xm.dominio.token.modelo.modelo.TokenVerificado;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;
import com.xm.dominio.token.servicio.ServicioCrearTokenAplicacion;

@ExtendWith(MockitoExtension.class)
class ServicioRefrescarTokenTest {

	public static final String ERROR_TOKEN_DE_REFRESCO_EXPIRED = "El tiempo del token de refresco ha expirado.";
	public static final String ERROR_TOKEN_DE_REFRESCO = "No es un token de refresco válido.";
	public static final String EXP = "exp";
	public static final String OFFLINE_ACCESS = "offline_access";
	public static final Long TOKEN_SIN_EXPIRAR = 4727109720L;
	public static final Long TOKEN_EXPIRARADO = 727109720L;

	@Mock
	private ServicioCrearTokenAplicacion servicioCrearTokenAplicacion;
	@Mock
	private VerificadorToken verificadorToken;

	@InjectMocks
	private ServicioRefrescarToken servicioRefrescar;

	@Test
	void deberiaRefrescarTokenTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();
		Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimsEnum.Constantes.CLAIM_REFRESH_TOKEN, OFFLINE_ACCESS);
		claims.put(EXP, TOKEN_SIN_EXPIRAR);
		TokenVerificado tokenVerificado = TokenVerificado.valido(claims);
		DtoRespuestaAutenticacion dtoRespuestaAutenticacion = new DtoRespuestaAutenticacionTestDataBuilder()
				.construir();

		when(verificadorToken.ejecutar(anyString())).thenReturn(tokenVerificado);
		when(servicioCrearTokenAplicacion.ejecutar(anyString(), anyMap())).thenReturn(dtoRespuestaAutenticacion);

		DtoRespuestaAutenticacion resultado = servicioRefrescar.ejecutar(refrescarToken);

		verify(verificadorToken).ejecutar(anyString());
		verify(servicioCrearTokenAplicacion).ejecutar(anyString(), anyMap());

		assertEquals(dtoRespuestaAutenticacion.getTokenDeAcceso(), resultado.getTokenDeAcceso());
		assertEquals(dtoRespuestaAutenticacion.getTokenDeRefresco(), resultado.getTokenDeRefresco());
	}

	@Test
	void deberiaLanzarExcepcionPorTokenDeRefrescoInvalidoTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();
		TokenVerificado tokenVerificado = TokenVerificado.invalido(ERROR_TOKEN_DE_REFRESCO);

		when(verificadorToken.ejecutar(anyString())).thenReturn(tokenVerificado);

		assertThrows(() -> servicioRefrescar.ejecutar(refrescarToken), ExcepcionDeProceso.class,
				ERROR_TOKEN_DE_REFRESCO);

		verifyNoInteractions(servicioCrearTokenAplicacion);
	}

	@Test
	void deberiaLanzarExcepcionPorTokenDeRefrescoInvalidoSinClaimRefrescoTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();
		Map<String, Object> claims = new HashMap<>();
		TokenVerificado tokenVerificado = TokenVerificado.valido(claims);

		when(verificadorToken.ejecutar(anyString())).thenReturn(tokenVerificado);

		assertThrows(() -> servicioRefrescar.ejecutar(refrescarToken), ExcepcionDeProceso.class,
				ERROR_TOKEN_DE_REFRESCO);

		verifyNoInteractions(servicioCrearTokenAplicacion);
	}

	@Test
	void deberiaLanzarExcepcionPorTokenDeRefrescoInvalidoSinClaimExpiracionTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();
		Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimsEnum.Constantes.CLAIM_REFRESH_TOKEN, OFFLINE_ACCESS);
		TokenVerificado tokenVerificado = TokenVerificado.valido(claims);

		when(verificadorToken.ejecutar(anyString())).thenReturn(tokenVerificado);

		assertThrows(() -> servicioRefrescar.ejecutar(refrescarToken), ExcepcionDeProceso.class,
				ERROR_TOKEN_DE_REFRESCO);

		verifyNoInteractions(servicioCrearTokenAplicacion);
	}

	@Test
	void deberiaLanzarExcepcionPorTokenDeRefrescoExpiradoTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();
		Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimsEnum.Constantes.CLAIM_REFRESH_TOKEN, OFFLINE_ACCESS);
		claims.put(EXP, TOKEN_EXPIRARADO);
		TokenVerificado tokenVerificado = TokenVerificado.valido(claims);

		when(verificadorToken.ejecutar(anyString())).thenReturn(tokenVerificado);

		assertThrows(() -> servicioRefrescar.ejecutar(refrescarToken), ExcepcionDeProceso.class,
				ERROR_TOKEN_DE_REFRESCO_EXPIRED);

		verifyNoInteractions(servicioCrearTokenAplicacion);
	}
}
