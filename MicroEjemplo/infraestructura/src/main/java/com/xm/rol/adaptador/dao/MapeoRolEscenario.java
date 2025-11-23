package com.xm.rol.adaptador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xm.infraestructura.jdbc.MapperResult;
import com.xm.rol.modelo.dto.DtoRolEscenario;

public class MapeoRolEscenario implements RowMapper<DtoRolEscenario>, MapperResult{
	
	@Override
	public DtoRolEscenario mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		int rolId = resultSet.getInt("rolID");
		String nombreRol = resultSet.getString("nombreRol");
		int escenarioId = resultSet.getInt("escenarioID");
		String nombreEscenario = resultSet.getString("nombreEscenario");

		return new DtoRolEscenario(rolId,nombreRol,escenarioId,nombreEscenario);
	}

}
