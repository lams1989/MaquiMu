package com.xm.autenticacion.consulta.manejador.rol;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xm.rol.modelo.dto.DtoRolEscenario;
import com.xm.rol.puerto.dao.DaoRol;

@Component
public class ManejadorConsultarRolPorNombre {

	private final DaoRol daoRol;

	public ManejadorConsultarRolPorNombre(DaoRol daoRol) {
		this.daoRol = daoRol;
	}

	public DtoRolEscenario ejecutar(String nombreRol) {
		return this.daoRol.consultarPorNombre(nombreRol);
	}

	public List<DtoRolEscenario> ejecutar(List<String> nombresRoles) {
		return this.daoRol.consultarPorNombres(nombresRoles);
	}

}
