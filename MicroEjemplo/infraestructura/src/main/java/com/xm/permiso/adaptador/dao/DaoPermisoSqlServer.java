package com.xm.permiso.adaptador.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xm.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.xm.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.xm.permiso.modelo.dto.DtoPermiso;
import com.xm.permiso.puerto.dao.DaoPermiso;

@Repository
public class DaoPermisoSqlServer implements DaoPermiso {

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

	@SqlStatement(namespace = "permiso", value = "consultarPorRoles")
	private static String sqlConsultarPorRoles;

	public DaoPermisoSqlServer(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}

	@Override
	public List<DtoPermiso> consultarPorRoles(List<String> roles) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("roles", roles);
		return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlConsultarPorRoles,
				paramSource, new MapeoPermiso());
	}

}
