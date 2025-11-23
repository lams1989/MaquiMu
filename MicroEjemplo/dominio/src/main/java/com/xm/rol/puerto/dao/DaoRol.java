package com.xm.rol.puerto.dao;

import java.util.List;

import com.xm.rol.modelo.dto.DtoRolEscenario;

public interface DaoRol {

	/**
	 * Permite traer información de un rol consultando por nombre
	 * 
	 * @return Rol
	 */
	DtoRolEscenario consultarPorNombre(String nombreRol);

	/**
	 * Permite consultar la información de los roles filtrando por una lista de nombres de rol.
	 * @param nombresRoles Nombres de los roles a filtrar.
	 * @return retorna una lista de @{@link DtoRolEscenario}
	 */
	List<DtoRolEscenario> consultarPorNombres(List<String> nombresRoles);
}
