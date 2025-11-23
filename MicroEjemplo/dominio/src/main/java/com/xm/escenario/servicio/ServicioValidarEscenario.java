package com.xm.escenario.servicio;

import java.util.List;

import com.xm.dominio.enums.EscenarioEnum;
import com.xm.dominio.excepcion.ExcepcionAutorizacion;
import com.xm.rol.puerto.dao.DaoRol;

public class ServicioValidarEscenario {
	private final DaoRol daoRol;

	public ServicioValidarEscenario(DaoRol daoRol) {
		this.daoRol = daoRol;
	}

	public void ejecutar(List<String> roles, EscenarioEnum escenario) {
		daoRol.consultarPorNombres(roles).stream()
				.filter(rolEscenario -> rolEscenario.getNombreEscenario().equalsIgnoreCase(escenario.getNombre()))
				.findFirst().orElseThrow(() -> new ExcepcionAutorizacion(ExcepcionAutorizacion.MENSAJE_NO_AUTORIZADO));
	}
}
