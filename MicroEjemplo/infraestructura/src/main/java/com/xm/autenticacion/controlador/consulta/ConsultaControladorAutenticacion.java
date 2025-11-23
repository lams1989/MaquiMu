package com.xm.autenticacion.controlador.consulta;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xm.autenticacion.consulta.ComandoAutenticacion;
import com.xm.autenticacion.consulta.ComandoRefrescarToken;
import com.xm.autenticacion.consulta.manejador.autenticacion.ManejadorConsultaAutenticar;
import com.xm.autenticacion.consulta.manejador.autenticacion.ManejadorConsultaRefrescarToken;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;
import com.xm.infraestructura.aspecto.tasalimite.TasaLimite;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacion")
@Tag(name = "Controlador autenticacion")
@RequiredArgsConstructor
public class ConsultaControladorAutenticacion {

	private final ManejadorConsultaAutenticar manejadorConsultaAutenticar;
	private final ManejadorConsultaRefrescarToken manejadorConsultaRefrescarToken;

	@PostMapping
	@TasaLimite
	@Operation(summary = "Genera el token que identifica la sesión en la aplicación")
	public DtoRespuestaAutenticacion generarToken(@RequestBody ComandoAutenticacion comando) {
		return manejadorConsultaAutenticar.ejecutar(comando);
	}

	@PostMapping("/actualizarToken")
	@TasaLimite
	@Operation(summary = "Refresca el token que identifica la sesión en la aplicación")
	public DtoRespuestaAutenticacion refrescarToken(@RequestBody ComandoRefrescarToken comando) {
		return manejadorConsultaRefrescarToken.ejecutar(comando);
	}
}
