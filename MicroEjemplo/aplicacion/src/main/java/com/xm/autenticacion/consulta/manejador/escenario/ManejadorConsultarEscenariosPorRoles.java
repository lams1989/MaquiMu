package com.xm.autenticacion.consulta.manejador.escenario;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xm.dominio.enums.EscenarioEnum;
import com.xm.escenario.servicio.ServicioValidarEscenario;

@Component
public class ManejadorConsultarEscenariosPorRoles {

	private final ServicioValidarEscenario servicioValidarEscenario;

	public ManejadorConsultarEscenariosPorRoles(ServicioValidarEscenario servicioValidarEscenario) {
		this.servicioValidarEscenario = servicioValidarEscenario;
	}

	public void ejecutar(List<String> roles, String tipoEscenario) {
		this.servicioValidarEscenario.ejecutar(roles, EscenarioEnum.obtenerPorNombre(tipoEscenario));
	}

}
