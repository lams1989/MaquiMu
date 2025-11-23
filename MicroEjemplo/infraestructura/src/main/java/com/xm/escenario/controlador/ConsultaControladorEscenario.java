package com.xm.escenario.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xm.autenticacion.consulta.manejador.escenario.ManejadorConsultarEscenariosPorRoles;
import com.xm.dominio.ConstantesNegocio;
import com.xm.infraestructura.seguridad.SolicitudContexto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/escenario")
@RequiredArgsConstructor
@Tag(name = "Escenario - Consulta")
public class ConsultaControladorEscenario {

	private static final String ROLES = "roles";

	private final ManejadorConsultarEscenariosPorRoles manejadorConsultarEscenariosPorRoles;

	@GetMapping("/{tipoEscenario}")
	@Operation(summary = "Válida que el token tenga un rol del tipo de escenario")
	public void validarTokenEscenario(@PathVariable String tipoEscenario) {
		List<String> roles = List.of(SolicitudContexto.obtenerDelContexto(ROLES).split(ConstantesNegocio.DELIMITADOR));
		this.manejadorConsultarEscenariosPorRoles.ejecutar(roles, tipoEscenario);
	}

}
