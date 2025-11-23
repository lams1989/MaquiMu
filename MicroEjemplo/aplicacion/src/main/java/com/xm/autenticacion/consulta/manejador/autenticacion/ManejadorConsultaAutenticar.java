package com.xm.autenticacion.consulta.manejador.autenticacion;

import org.springframework.stereotype.Component;

import com.xm.autenticacion.consulta.ComandoAutenticacion;
import com.xm.autenticacion.consulta.fabrica.FabricaCredencialesUsuario;
import com.xm.autenticacion.modelo.entidad.Usuario;
import com.xm.autenticacion.servicio.ServicioAutenticar;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorConsultaAutenticar {

	private final FabricaCredencialesUsuario fabricaCredencialesUsuario;
	private final ServicioAutenticar servicioAutenticar;

	public DtoRespuestaAutenticacion ejecutar(ComandoAutenticacion comando) {
		Usuario credencialesUsuario = fabricaCredencialesUsuario.ejecutar(comando);
        return servicioAutenticar.ejecutar(credencialesUsuario);
    }
}
