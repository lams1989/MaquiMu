package com.xm.aplicacionpermitida.adaptador.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;
import com.xm.aplicacionpermitida.puerto.dao.DaoAplicacionPermitida;
import com.xm.dominio.excepcion.ExcepcionSinDatos;
import com.xm.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.xm.infraestructura.jdbc.sqlstatement.SqlStatement;

@Component
public class DaoAplicacionPermitidaSQLServer implements DaoAplicacionPermitida {

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

	private static final String NO_SE_ENCONTRO_INFORMACION_PARAMETROS_INDICADOS = "No se encontró información en aplicaciones permitidas con los parámetros indicados";

	@Value("${microservicio.ambiente}")
	private String ambiente;

	private static final String PARAMETRO_ESTADO = "estado";
	private static final String PARAMETRO_CLIENTE_ID = "clienteIdJwt";
	private static final String PARAMETRO_TENANT_ID = "tenantId";
	private static final String PARAMETRO_AMBIENTE = "ambiente";

	@SqlStatement(namespace = "aplicacionpermitida.consulta", value = "consultarPorEstado")
	private static String sqlConsultarPorEstado;

	@SqlStatement(namespace = "aplicacionpermitida.consulta", value = "consultarPorClientIdTenantIdYEstado")
	private static String sqlConsultarPorClientIdTenantIdYEstado;

	public DaoAplicacionPermitidaSQLServer(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}

	@Override
	public List<DtoAplicacionPermitida> consultarPorEstado(boolean estado) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(PARAMETRO_ESTADO, estado);
		paramSource.addValue(PARAMETRO_AMBIENTE, ambiente);

		return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlConsultarPorEstado,
				paramSource, new MapeoAplicacionPermitida());
	}

	@Override
	public DtoAplicacionPermitida consultarPorClientIdTenantIdYEstado(String clienteId, String tenantId,
			boolean estado) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(PARAMETRO_CLIENTE_ID, clienteId);
		paramSource.addValue(PARAMETRO_TENANT_ID, tenantId);
		paramSource.addValue(PARAMETRO_ESTADO, estado);
		paramSource.addValue(PARAMETRO_AMBIENTE, ambiente);

		List<DtoAplicacionPermitida> aplicacionesPermitidas = this.customNamedParameterJdbcTemplate
				.getNamedParameterJdbcTemplate()
				.query(sqlConsultarPorClientIdTenantIdYEstado, paramSource, new MapeoAplicacionPermitida());
		return aplicacionesPermitidas.stream().findFirst()
				.orElseThrow(() -> new ExcepcionSinDatos(NO_SE_ENCONTRO_INFORMACION_PARAMETROS_INDICADOS));
	}

}
