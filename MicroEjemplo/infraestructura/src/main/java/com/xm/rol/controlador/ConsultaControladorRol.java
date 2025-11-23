package com.xm.rol.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xm.autenticacion.consulta.manejador.rol.ManejadorConsultarRolPorNombre;
import com.xm.rol.modelo.dto.DtoRolEscenario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roles")
@Tag(name = "Rol - Consulta")
public class ConsultaControladorRol {

	private final ManejadorConsultarRolPorNombre manejadorConsultarRolPorNombre;

	public ConsultaControladorRol(ManejadorConsultarRolPorNombre manejadorConsultarRolPorNombre) {
		this.manejadorConsultarRolPorNombre = manejadorConsultarRolPorNombre;
	}

	@GetMapping
	@Operation(summary = "Consultar rol por nombre")
	public DtoRolEscenario consultarPorNombre(@RequestParam(value = "nombreRol", required = true) String nombreRol) {
		return this.manejadorConsultarRolPorNombre.ejecutar(nombreRol);
	}

	@GetMapping("/nombres")
	@Operation(summary = "Consultar roles mediante una lista de nombres")
	public List<DtoRolEscenario> consultarPorNombres(
			@RequestParam(value = "nombresRoles", required = true) List<String> nombresRoles) {
		return this.manejadorConsultarRolPorNombre.ejecutar(nombresRoles);
	}

}
