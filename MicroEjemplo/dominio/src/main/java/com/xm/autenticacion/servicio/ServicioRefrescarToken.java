package com.xm.autenticacion.servicio;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

import com.xm.autenticacion.modelo.entidad.RefrescarToken;
import com.xm.dominio.excepcion.ExcepcionDeProceso;
import com.xm.dominio.token.modelo.dto.DtoRespuestaAutenticacion;
import com.xm.dominio.token.modelo.enums.ClaimsEnum;
import com.xm.dominio.token.modelo.modelo.TokenVerificado;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;
import com.xm.dominio.token.servicio.ServicioCrearTokenAplicacion;

public class ServicioRefrescarToken {

	public static final String ERROR_TOKEN_DE_REFRESCO_EXPIRED = "El tiempo del token de refresco ha expirado.";
	public static final String ERROR_TOKEN_DE_REFRESCO = "No es un token de refresco válido.";
	private static final String EXP = "exp";

	private final ServicioCrearTokenAplicacion servicioCrearTokenAplicacion;
	private final VerificadorToken verificadorToken;

	public ServicioRefrescarToken(ServicioCrearTokenAplicacion servicioCrearTokenAplicacion,
			VerificadorToken verificadorToken) {
		this.servicioCrearTokenAplicacion = servicioCrearTokenAplicacion;
		this.verificadorToken = verificadorToken;
	}

	public DtoRespuestaAutenticacion ejecutar(RefrescarToken refrescarToken) {
		validarToken(refrescarToken.getTokenDeRefresco());

		return servicioCrearTokenAplicacion.ejecutar(refrescarToken.getTokenDeAcceso(),
				ClaimsEnum.TOKEN_SIMPLEX_OPERATIVO.getClaimsOrigenYDestino());
	}

	private void validarToken(String token) {
		TokenVerificado tokenVerificado = verificadorToken.ejecutar(token);
		Map<String, Object> claims = tokenVerificado.getAtributos();

		if (!tokenVerificado.isValido() || Objects.isNull(claims.get(ClaimsEnum.Constantes.CLAIM_REFRESH_TOKEN))
				|| Objects.isNull(claims.get(EXP))) {
			throw new ExcepcionDeProceso(ERROR_TOKEN_DE_REFRESCO);
		}
		long exp = ((Number) claims.get(EXP)).longValue();
		LocalDateTime fechaExpiracion = Instant.ofEpochSecond(exp).atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime fechaActual = LocalDateTime.now();
		if (fechaActual.isAfter(fechaExpiracion)) {
			throw new ExcepcionDeProceso(ERROR_TOKEN_DE_REFRESCO_EXPIRED);
		}
	}
}
