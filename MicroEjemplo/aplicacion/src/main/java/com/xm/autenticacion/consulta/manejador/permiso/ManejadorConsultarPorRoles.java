package com.xm.autenticacion.consulta.manejador.permiso;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xm.permiso.modelo.dto.DtoPermiso;
import com.xm.permiso.puerto.dao.DaoPermiso;

@Component
public class ManejadorConsultarPorRoles {

	private final DaoPermiso daoPermiso;

	public ManejadorConsultarPorRoles(DaoPermiso daoPermiso) {
		this.daoPermiso = daoPermiso;
	}

	public List<DtoPermiso> ejecutar(List<String> roles) {
		return this.daoPermiso.consultarPorRoles(roles);
	}

}
