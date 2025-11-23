package com.xm.autenticacion.controlador.consulta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.xm.PruebaIntegracionBase;
import com.xm.autenticacion.consulta.ComandoAutenticacion;
import com.xm.autenticacion.consulta.ComandoRefrescarToken;
import com.xm.autenticacion.controlador.consulta.testdatabuilder.ComandoAutenticacionTestDataBuilder;
import com.xm.autenticacion.controlador.consulta.testdatabuilder.ComandoRefrescarTokenTestDataBuilder;
import com.xm.dominio.token.modelo.modelo.TokenVerificado;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;

@WebMvcTest(ConsultaControladorAutenticacion.class)
class ConsultaControladorAutenticacionTest extends PruebaIntegracionBase {

    private static final String CAMPO_TOKEN = "tokenDeAcceso";
    private static final String CAMPO_REFRESH_TOKEN = "tokenDeRefresco";
    private static final String ID_OBJETO_TECNICO = "471d579d-09b0-4965-85b4-916309c13f54";
    private static final String NOMBRE_TECNICO = "BG1499";
    private static final String CUENTA_TECNICA_MDC = "CUENTA TECNICA MDC";
    private static final String CORREO_ELECTRONICO_TECNICO = "BG1499@XM.com.co";

	private static final String URL_AUTENTICACION = "/autenticacion";
    private static final String URL_REFRESCAR_TOKEN = "/actualizarToken";

	@MockitoBean
	@Qualifier("verificadorTokenJwtOAuth2Microsoft")
	private VerificadorToken verificadorToken;

    @Test
    void deberiaRefrescarTokenTest() throws Exception {
        ComandoRefrescarToken comandoRefrescarToken = new ComandoRefrescarTokenTestDataBuilder().construir();

        MvcResult result = mockMvc
				.perform(post(URL_AUTENTICACION.concat(URL_REFRESCAR_TOKEN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comandoRefrescarToken)))
                .andExpect(status().isOk()).andReturn();

        JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

        assertThat(jsonResponse.isObject()).isTrue();
        assertThat(jsonResponse.has(CAMPO_TOKEN)).isTrue();
        assertThat(jsonResponse.has(CAMPO_REFRESH_TOKEN)).isTrue();
    }

    @Test
    void deberiaGenerarTokenUsuarioTest() throws Exception {
		ComandoAutenticacion comandoAutenticacion = new ComandoAutenticacionTestDataBuilder().construir();

		TokenVerificado tokenVerificado = TokenVerificado.valido(new HashMap<>());
		when(verificadorToken.ejecutar(comandoAutenticacion.getTokenDeAcceso())).thenReturn(tokenVerificado);

        MvcResult result = mockMvc
				.perform(post(URL_AUTENTICACION).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comandoAutenticacion)))
                .andExpect(status().isOk()).andReturn();

        JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

        assertThat(jsonResponse.isObject()).isTrue();
        assertThat(jsonResponse.has(CAMPO_TOKEN)).isTrue();
        assertThat(jsonResponse.has(CAMPO_REFRESH_TOKEN)).isTrue();

		verify(verificadorToken).ejecutar(comandoAutenticacion.getTokenDeAcceso());
    }

    @Test
    void deberiaGenerarTokenTecnicoTest() throws Exception {
        ComandoAutenticacion comandoAutenticacion = new ComandoAutenticacionTestDataBuilder()
                .conIdObjeto(ID_OBJETO_TECNICO)
                .conNombre(NOMBRE_TECNICO)
                .conNombreCompleto(CUENTA_TECNICA_MDC)
                .conCorreoElectronico(CORREO_ELECTRONICO_TECNICO)
                .construir();

		TokenVerificado tokenVerificado = TokenVerificado.valido(new HashMap<>());
		when(verificadorToken.ejecutar(comandoAutenticacion.getTokenDeAcceso())).thenReturn(tokenVerificado);

        MvcResult result = mockMvc
				.perform(post(URL_AUTENTICACION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comandoAutenticacion)))
                .andExpect(status().isOk()).andReturn();

        JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

        assertThat(jsonResponse.isObject()).isTrue();
        assertThat(jsonResponse.has(CAMPO_TOKEN)).isTrue();
        assertThat(jsonResponse.has(CAMPO_REFRESH_TOKEN)).isTrue();

		verify(verificadorToken).ejecutar(comandoAutenticacion.getTokenDeAcceso());

    }

}
