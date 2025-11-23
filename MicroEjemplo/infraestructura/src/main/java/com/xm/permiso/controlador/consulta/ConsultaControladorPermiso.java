package com.xm.permiso.controlador.consulta;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xm.autenticacion.consulta.manejador.permiso.ManejadorConsultarPorRoles;
import com.xm.permiso.modelo.dto.DtoPermiso;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/permisos")
@Tag(name = "Controlador consulta autenticacion")
public class ConsultaControladorPermiso {

	private final ManejadorConsultarPorRoles manejadorConsultarPorRoles;

	public ConsultaControladorPermiso(ManejadorConsultarPorRoles manejadorConsultarPorRoles) {
		this.manejadorConsultarPorRoles = manejadorConsultarPorRoles;
	}

	@GetMapping
	@Cacheable(value = "rolesCache", key = "#roles")
	@Operation(summary = "Consultar Autenticacion")
	public List<DtoPermiso> consultarAutenticacion(@RequestParam List<String> roles) {
		return manejadorConsultarPorRoles.ejecutar(roles);
	}

}
