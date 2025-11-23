package com.xm.rol.controlador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.xm.PruebaIntegracionBase;
import com.xm.rol.modelo.dto.DtoRolEscenario;

@WebMvcTest(ConsultaControladorRol.class)
public class ConsultaControladorRolTest extends PruebaIntegracionBase {

	private static final String CAMPO_NOMBRE_ESCENARIO = "nombreEscenario";
	private static final String ESCENARIO_REDESPACHO = "Redespacho";
	private static final String END_POINT_CONSULTAR_POR_NOMBRE = "/roles?nombreRol=";
	private static final String END_POINT_CONSULTAR_POR_NOMBRES = "/roles/nombres?nombresRoles=";
	public static final String ROL_ESCENARIO = "SO-Redespacho";
	public static final int ID = 2;
	private static final int CERO = 0;

	@Test
	void consultarRolPorNombreTest() throws Exception {

		MvcResult result = mockMvc.perform(
				get(END_POINT_CONSULTAR_POR_NOMBRE.concat(ROL_ESCENARIO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

		assertThat(jsonResponse.get(CAMPO_NOMBRE_ESCENARIO).isValueNode()).isTrue();
		assertThat(jsonResponse.get(CAMPO_NOMBRE_ESCENARIO).asText()).contains(ESCENARIO_REDESPACHO);

		var rolEscenario = objectMapper.treeToValue(jsonResponse, DtoRolEscenario.class);
		Assertions.assertEquals(ESCENARIO_REDESPACHO, rolEscenario.getNombreEscenario());
		Assertions.assertEquals(ID, rolEscenario.getEscenarioId());
		Assertions.assertEquals(ID, rolEscenario.getRolId());
		Assertions.assertEquals(ROL_ESCENARIO, rolEscenario.getNombreRol());
	}

	@Test
	void consultarRolPorNombresTest() throws Exception {

		MvcResult result = mockMvc.perform(
				get(END_POINT_CONSULTAR_POR_NOMBRES.concat(ROL_ESCENARIO)).contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

		JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

		assertThat(jsonResponse.isArray()).isTrue();

		assertThat(jsonResponse.get(CERO).get(CAMPO_NOMBRE_ESCENARIO).isValueNode()).isTrue();
		assertThat(jsonResponse.get(CERO).get(CAMPO_NOMBRE_ESCENARIO).asText()).contains(ESCENARIO_REDESPACHO);
	}
}
