package com.xm.escenario.controlador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.xm.PruebaIntegracionBase;

@WebMvcTest(ConsultaControladorEscenario.class)
public class ConsultaControladorEscenarioTest extends PruebaIntegracionBase {

	private static final String END_POINT_CONSULTAR_ESCENARIO = "/escenario/despacho";

	@Test
	void deberiaRefrescarTokenTest() throws Exception {
		MockHttpServletRequestBuilder solicitud = get(END_POINT_CONSULTAR_ESCENARIO)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult resultado = mockMvc.perform(solicitud).andReturn();

		assertThat(resultado.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
