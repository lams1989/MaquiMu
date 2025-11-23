package com.xm.permiso.puerto.dao;

import java.util.List;

import com.xm.permiso.modelo.dto.DtoPermiso;

public interface DaoPermiso {
	
	/**
     * Permite obtener los permisos por una lista de roles
     * @param roles
     * @return permisos
     */
	List<DtoPermiso> consultarPorRoles(List<String> roles);

}
