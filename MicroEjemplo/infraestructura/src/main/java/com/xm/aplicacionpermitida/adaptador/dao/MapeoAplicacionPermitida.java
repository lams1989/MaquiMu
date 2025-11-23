package com.xm.aplicacionpermitida.adaptador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;
import com.xm.infraestructura.jdbc.MapperResult;

public class MapeoAplicacionPermitida implements RowMapper<DtoAplicacionPermitida>, MapperResult {

	@Override
	public DtoAplicacionPermitida mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int aplicacionPermitidaId = resultSet.getInt("aplicacionPermitidaId");
		String nombre = resultSet.getString("nombre");
		String clienteIdJwt = resultSet.getString("clienteIdJwt");
		String tipoIntegracion = resultSet.getString("tipoIntegracion");
		String tenantId = resultSet.getString("tenantId");
		String ambiente = resultSet.getString("ambiente");
		String urlWebhook = resultSet.getString("urlWebhook");
		boolean estado = resultSet.getBoolean("estado");

		return new DtoAplicacionPermitida(aplicacionPermitidaId, nombre, clienteIdJwt, tipoIntegracion, tenantId,
				ambiente, estado, urlWebhook);
	}

}