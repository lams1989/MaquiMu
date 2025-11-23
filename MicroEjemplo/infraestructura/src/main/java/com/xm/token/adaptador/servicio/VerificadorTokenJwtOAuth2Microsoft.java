package com.xm.token.adaptador.servicio;

import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import com.xm.dominio.token.modelo.modelo.TokenVerificado;
import com.xm.dominio.token.puerto.servicio.VerificadorToken;
import com.xm.infraestructura.excepcion.ExcepcionAutenticacion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class VerificadorTokenJwtOAuth2Microsoft implements VerificadorToken {

	private static final String ERROR_VALIDANDO_EL_TOKEN_DE_SIMPLEX_POR_FAVOR_VERIFICA_LA_INFORMACION = "Error validando el token de simplex, por favor verifica la información";
	private static final Logger LOGGER = LogManager.getLogger(VerificadorTokenJwtOAuth2Microsoft.class);
	private static final String ERROR_GENERAL_TOKEN = "Ocurrió un error validando el token";
	private static final int SESGO_DE_RELOJ_SEGUNDOS = 15;

	@Value("${seguridad.microsoft.urlLlavesPublicas}")
	private String urlLlavesPublicas;
	
	@Value("${seguridad.microsoft.clientId}")
	private String clientId;
	

	@Override
	public TokenVerificado ejecutar(String token) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);

			JWKSet jwkSet = JWKSet.load(new URL(urlLlavesPublicas));
			JWK jwk = jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());
			PublicKey publicKey = ((RSAKey) jwk).toPublicKey();

			Jws<Claims> jwsVerificado =
				Jwts.parserBuilder()
						.setSigningKey(publicKey)
						.setAllowedClockSkewSeconds(SESGO_DE_RELOJ_SEGUNDOS)
						.build()
						.parseClaimsJws(token);

			Claims conjuntoClaims = jwsVerificado.getBody();
            validarAudience(conjuntoClaims);   
			return TokenVerificado.valido(conjuntoClaims);

		} catch (JwtException | ParseException | IOException | JOSEException e) {
			LOGGER.error(ExcepcionAutenticacion.MENSAJE_NO_AUTENTICADO, e);
			throw new ExcepcionAutenticacion(ExcepcionAutenticacion.MENSAJE_NO_AUTENTICADO, e);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ExcepcionAutenticacion(ERROR_GENERAL_TOKEN, e);
		}
	}


	private void validarAudience(Claims conjuntoClaims) {
		if(!clientId.equalsIgnoreCase(conjuntoClaims.getAudience())) {
			throw new ExcepcionAutenticacion(ERROR_VALIDANDO_EL_TOKEN_DE_SIMPLEX_POR_FAVOR_VERIFICA_LA_INFORMACION);
		}
	}
}
