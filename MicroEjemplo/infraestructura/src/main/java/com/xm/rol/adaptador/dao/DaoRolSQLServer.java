package com.xm.rol.adaptador.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.xm.dominio.excepcion.ExcepcionSinDatos;
import com.xm.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.xm.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.xm.rol.modelo.dto.DtoRolEscenario;
import com.xm.rol.puerto.dao.DaoRol;

@Component
public class DaoRolSQLServer implements DaoRol {

	private static final String NO_SE_ENCONTRO_EL_ROL_CON_EL_NOMBRE = "No se encontro el rol con el nombre: ";
	private static final String NOMBRE_ROL = "nombreRol";
	private static final String NOMBRES = "nombres";

	private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

	@SqlStatement(namespace = "rol", value = "consultarPorNombre")
	private static String sqlConsultarPorNombre;

	@SqlStatement(namespace = "rol", value = "consultarPorNombres")
	private static String sqlConsultarPorNombres;

	public DaoRolSQLServer(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}

	@Override
	public DtoRolEscenario consultarPorNombre(String nombreRol) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(NOMBRE_ROL, nombreRol);

		List<DtoRolEscenario> rolEscenario = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
				.query(sqlConsultarPorNombre, paramSource, new MapeoRolEscenario());
		return rolEscenario.stream().findFirst().orElseThrow(
				() -> new ExcepcionSinDatos(NO_SE_ENCONTRO_EL_ROL_CON_EL_NOMBRE.concat(String.valueOf(nombreRol))));
	}

	@Override
	public List<DtoRolEscenario> consultarPorNombres(List<String> nombresRoles) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue(NOMBRES, nombresRoles);

		return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlConsultarPorNombres,
				paramSource, new MapeoRolEscenario());
	}

}
