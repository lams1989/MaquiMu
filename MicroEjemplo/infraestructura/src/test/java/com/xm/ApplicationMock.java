package com.xm;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.autenticacion.controlador.consulta.testdatabuilder.DtoGrupoGraphTestDataBuilder;
import com.xm.autenticacion.controlador.consulta.testdatabuilder.DtoRespuestaGruposUsuarioGraphTestDataBuilder;
import com.xm.infraestructura.filtro.MockRequestAttributeFilter;
import com.xm.infraestructura.sesion.modelo.dto.DtoRespuestaGruposUsuarioGraph;
import com.xm.rol.controlador.testdatabuilder.DtoRolEscenarioTestDataBuilder;
import com.xm.rol.modelo.dto.DtoRolEscenario;

import jakarta.annotation.PostConstruct;

@ComponentScan("com.xm")
public class ApplicationMock {

	private static final String URL_AUTENTICACION = "https://xmze2msvcseguridadprbappf01.azurewebsites.net/api/Autenticacion";

	private static final String URL_CONSULTAR_GRUPOS = "https://graph.microsoft.com/v1.0/users/%s/memberOf/microsoft.graph.group?$select=displayName";
	private static final String URL_CONSULTAR_ROLES_POR_NOMBRE = "http://localhost:8088/control-acceso/v1/roles/nombres?nombresRoles=SO-Redespacho";

	private static final String URL_INFORMACION_USUARIO = "https://graph.microsoft.com/beta/users/%s/profile";
	private static final String RESPUESTA_INFORMACION_USUARIO_MICROSOFT_GRAPH = "{\"positions\":[{\"allowedAudiences\":\"organization\",\"detail\":{\"company\":{\"address\":{\"postalCode\":\"1499\"}}}}]}";
	private static final String ID_MICROSERVICIO = "30";
	private static final String ID_OBJETO_TECNICO = "471d579d-09b0-4965-85b4-916309c13f54";
	private static final String ID_OBJETO_USUARIO = "c17948b8-2f03-4795-848f-82f52011275c";
	private static final String API_KEY_OCP = "34e6aa77bb90cb24abe19";
	private static final String ROL_SO_REDESPACHO_PRB = "SO-Redespacho_PRB";
	private static final String GRUPO_TODOS_SIN_CORREO_DINAMICO = "Grupo_Todos_SinCorreo-Dinamico";
	private static final String GRUPO_NORMALES = "Normales";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private DataSource dataSourcePruebasDeIntegracion;

	@Value("${spring.flyway.schemas}")
	private String esquemasMigraciones;

	@Value("${spring.flyway.locations}")
	private String localizacionesMigraciones;

	@Value("${auditar.bitacora.urlBitacora}")
	private String urlBitacoraAuditarBct;

	@PostConstruct
	public void realizarMigracionesFlyway() {
		Flyway.configure().dataSource(dataSourcePruebasDeIntegracion).schemas(esquemasMigraciones)
				.locations(localizacionesMigraciones).load().migrate();
	}

	private void mockearRegistroExcepcion(MockRestServiceServer mockServer)
			throws JsonProcessingException, URISyntaxException {

		mockServer.expect(ExpectedCount.manyTimes(), requestTo(new URI(urlBitacoraAuditarBct)))
				.andExpect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(true)));
	}

	private void mockearGrupoRolesGraph(MockRestServiceServer mockServer, String idObjeto)
			throws JsonProcessingException, URISyntaxException {
		List<String> simulacionGruposDirectorioActivo = Arrays.asList(ROL_SO_REDESPACHO_PRB,
				GRUPO_TODOS_SIN_CORREO_DINAMICO, GRUPO_NORMALES);
		var dtoGrupoGraph = new DtoGrupoGraphTestDataBuilder().construirLista(simulacionGruposDirectorioActivo);
		DtoRespuestaGruposUsuarioGraph dtoGruposGraph = new DtoRespuestaGruposUsuarioGraphTestDataBuilder()
				.contruir(dtoGrupoGraph);

		mockServer.expect(ExpectedCount.manyTimes(), requestTo(new URI(String.format(URL_CONSULTAR_GRUPOS, idObjeto))))
				.andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(dtoGruposGraph)));
	}

	private void mockearConsultaRolesPorNombres(MockRestServiceServer mockServer)
			throws JsonProcessingException, URISyntaxException {
		List<DtoRolEscenario> roles = new DtoRolEscenarioTestDataBuilder().construirLista();

		mockServer.expect(ExpectedCount.manyTimes(), requestTo(new URI(URL_CONSULTAR_ROLES_POR_NOMBRE)))
				.andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(roles)));
	}

	private void mockearInformacionUsuario(MockRestServiceServer mockServer, String idObjeto)
			throws URISyntaxException {
		mockServer
				.expect(ExpectedCount.manyTimes(), requestTo(new URI(String.format(URL_INFORMACION_USUARIO, idObjeto))))
				.andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON).body(RESPUESTA_INFORMACION_USUARIO_MICROSOFT_GRAPH));
	}

	@Bean
	public MockRestServiceServer mockServer() throws JsonProcessingException, URISyntaxException {

		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
		mockearRegistroExcepcion(mockServer);
		mockearGrupoRolesGraph(mockServer, ID_OBJETO_TECNICO);
		mockearGrupoRolesGraph(mockServer, ID_OBJETO_USUARIO);
		mockearInformacionUsuario(mockServer, ID_OBJETO_TECNICO);
		mockearInformacionUsuario(mockServer, ID_OBJETO_USUARIO);
		mockearConsultaRolesPorNombres(mockServer);
		return mockServer;
	}

	@Bean
	public MockMvc mockMvc() {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(new MockRequestAttributeFilter()).build();

		System.setProperty("auditar.bitacora.apiKeyOcp", API_KEY_OCP);
		System.setProperty("xm.servicio.autenticacion-autorizacion", URL_AUTENTICACION);
		System.setProperty("idMicroservicio", ID_MICROSERVICIO);

		return mockMvc;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
