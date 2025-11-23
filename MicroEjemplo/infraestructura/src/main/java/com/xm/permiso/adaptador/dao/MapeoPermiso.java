package com.xm.permiso.adaptador.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.xm.infraestructura.jdbc.MapperResult;
import com.xm.permiso.modelo.dto.DtoPermiso;

public class MapeoPermiso implements RowMapper<DtoPermiso>, MapperResult {

	@Override
	public DtoPermiso mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		int permisoId = resultSet.getInt("permisoID");
		String nombre = resultSet.getString("nombre");
		String codigo = resultSet.getString("codigo");
		boolean estado = resultSet.getBoolean("estado");
		return new DtoPermiso(permisoId, nombre, codigo, estado);
	}
}
