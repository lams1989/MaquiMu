package com.xm.aplicacionpermitida.controlador.consulta;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xm.aplicacionpermitida.consulta.manejador.ManejadorConsultarAplicacionesPermitidasPorClientIdTenantIdYEstado;
import com.xm.aplicacionpermitida.consulta.manejador.ManejadorConsultarAplicacionesPermitidasPorEstado;
import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aplicaciones-permitidas")
@RequiredArgsConstructor
@Tag(name = "Aplicación permitida - Consulta")
public class ConsultaControladorAplicacionPermitida {

	private final ManejadorConsultarAplicacionesPermitidasPorEstado manejadorConsultarAplicacionesPermitidasPorEstado;
	private final ManejadorConsultarAplicacionesPermitidasPorClientIdTenantIdYEstado manejadorConsultarAplicacionesPermitidasPorClientIdTenantIdYEstado;

	@GetMapping
	@Operation(summary = "Consultar aplicaciones permitidas por estado")
	public List<DtoAplicacionPermitida> consultarPorEstado(
			@RequestParam(value = "estado", required = true) boolean estado) {
		return this.manejadorConsultarAplicacionesPermitidasPorEstado.ejecutar(estado);
	}

	@GetMapping(value = "/clienteId/{clienteId}/tenantId/{tenantId}")
	@Operation(summary = "Consultar aplicación permitida por clientId, tenantId y estado")
	public DtoAplicacionPermitida consultarPorClientIdTenantIdYEstado(@PathVariable String clienteId,
			@PathVariable String tenantId, @RequestParam(value = "estado", required = true) boolean estado) {
		return this.manejadorConsultarAplicacionesPermitidasPorClientIdTenantIdYEstado.ejecutar(clienteId, tenantId,
				estado);
	}
}
