package com.xm.aplicacionpermitida.puerto.dao;

import java.util.List;

import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;

public interface DaoAplicacionPermitida {

	/**
	 * Permite obtener las aplicaciones permitidas por estado
	 * 
	 * @param estado: Filtro de aplicaciones por estado activo o inactivo
	 * @return Lista DtoAplicacionPermitida
	 */
	List<DtoAplicacionPermitida> consultarPorEstado(boolean estado);

	/**
	 * Permite obtener las aplicaciones permitidas por estado
	 * 
	 * @param clienteId: Filtro de aplicaciones por clienteId
	 * @param tenantId: Filtro de aplicaciones por tenantId
	 * @param estado: Filtro de aplicaciones por estado activo o inactivo
	 * @return Lista DtoAplicacionPermitida
	 */
	DtoAplicacionPermitida consultarPorClientIdTenantIdYEstado(String clienteId, String tenantId, boolean estado);

}
