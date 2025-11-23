package com.xm.permiso.controlador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.xm.PruebaIntegracionBase;
import com.xm.permiso.controlador.consulta.ConsultaControladorPermiso;

@WebMvcTest(ConsultaControladorPermiso.class)
public class ConsultaControladorPermisoTest extends PruebaIntegracionBase {

    private static final String CAMPO_PERMISO_ID = "permisoId";
    private static final String CAMPO_NOMBRE = "nombre";
    private static final String CAMPO_CODIGO = "codigo";
    private static final String CAMPO_ESTADO = "estado";
    private static final int INDEX = 0;
    private static final String END_POINT_CONSULTAR_PERMISOS_POR_ROL = "/permisos?roles=";

    @Test
    public void consultarPermisosPorRolesTest() throws Exception {

        MvcResult result = mockMvc.perform(get(END_POINT_CONSULTAR_PERMISOS_POR_ROL.concat("SO-RedespachoConsulta"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        JsonNode jsonResponse = objectMapper.readTree(result.getResponse().getContentAsByteArray());

        assertThat(jsonResponse.isArray()).isTrue();
        assertThat(jsonResponse.get(INDEX).isObject()).isTrue();
        assertThat(jsonResponse.get(INDEX).has(CAMPO_PERMISO_ID)).isTrue();
        assertThat(jsonResponse.get(INDEX).has(CAMPO_NOMBRE)).isTrue();
        assertThat(jsonResponse.get(INDEX).has(CAMPO_CODIGO)).isTrue();
        assertThat(jsonResponse.get(INDEX).has(CAMPO_ESTADO)).isTrue();
    }

}
