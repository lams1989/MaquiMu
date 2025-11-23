package com.xm.aplicacionpermitida.controlador.consulta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.xm.PruebaIntegracionBase;

@WebMvcTest(ConsultaControladorAplicacionPermitida.class)
class ConsultaControladorAplicacionPermitidaTest extends PruebaIntegracionBase {

	private static final String END_POINT_CONSULTAR_POR_ESTADO = "/aplicaciones-permitidas?estado=%b";
	private static final String END_POINT_CONSULTAR_POR_CLIENTE_ID_TENAND_ID_ESTADO = "/aplicaciones-permitidas/clienteId/%s/tenantId/%s?estado=%b";

	private static final String ATRIBUTO_APLICACION_PERMITIDA_ID = "aplicacionPermitidaId";
	private static final String ATRIBUTO_NOMBRE = "nombre";
	private static final String ATRIBUTO_CLIENTE_ID_JWT = "clienteIdJwt";
	private static final String ATRIBUTO_TIPO_INTEGRACION = "tipoIntegracion";
	private static final String ATRIBUTO_TENANT_ID = "tenantId";
	private static final String ATRIBUTO_AMBIENTE = "ambiente";
	private static final String ATRIBUTO_ESTADO = "estado";

	private static final int POSICION_INICIAL = 0;
	private static final boolean ESTADO_ACTIVO = true;

	@Test
	void consultarPorEstadoTest() throws Exception {

		MvcResult resultado = mockMvc.perform(get(String.format(END_POINT_CONSULTAR_POR_ESTADO, ESTADO_ACTIVO))
				.contentType(MediaType.APPLICATION_JSON)).andReturn();

		assertThat(resultado.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

		JsonNode jsonResponse = objectMapper.readTree(resultado.getResponse().getContentAsByteArray());
		assertThat(jsonResponse.isArray()).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).isObject()).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_APLICACION_PERMITIDA_ID)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_NOMBRE)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_CLIENTE_ID_JWT)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_TIPO_INTEGRACION)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_TENANT_ID)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_AMBIENTE)).isTrue();
		assertThat(jsonResponse.get(POSICION_INICIAL).has(ATRIBUTO_ESTADO)).isTrue();

		assertTrue(jsonResponse.get(POSICION_INICIAL).get(ATRIBUTO_ESTADO).asBoolean());
	}

	@Test
	void consultarPorClientIdTenantIdYEstadoTest() throws Exception {

		MvcResult resultado = mockMvc.perform(get(String.format(END_POINT_CONSULTAR_POR_CLIENTE_ID_TENAND_ID_ESTADO,
				ATRIBUTO_CLIENTE_ID_JWT, ATRIBUTO_TENANT_ID, ESTADO_ACTIVO)).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertThat(resultado.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

		JsonNode jsonResponse = objectMapper.readTree(resultado.getResponse().getContentAsByteArray());
		assertThat(jsonResponse.isObject()).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_APLICACION_PERMITIDA_ID)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_NOMBRE)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_CLIENTE_ID_JWT)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_TIPO_INTEGRACION)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_TENANT_ID)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_AMBIENTE)).isTrue();
		assertThat(jsonResponse.has(ATRIBUTO_ESTADO)).isTrue();

		assertEquals(ATRIBUTO_CLIENTE_ID_JWT, jsonResponse.get(ATRIBUTO_CLIENTE_ID_JWT).asText());
		assertEquals(ATRIBUTO_TENANT_ID, jsonResponse.get(ATRIBUTO_TENANT_ID).asText());
		assertTrue(jsonResponse.get(ATRIBUTO_ESTADO).asBoolean());
	}

}