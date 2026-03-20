package com.maquimu.aplicacion.autenticacion.servicio;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.servicio.ServicioToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class GeneradorJwt implements ServicioToken {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration.ms}")
	private int jwtExpirationMs;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String generarToken(Usuario usuario) {
		return Jwts.builder().setSubject(usuario.getEmail()).claim("userId", usuario.getId())
				.claim("rol", usuario.getRol().name()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
	}

	private Claims validarToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public String extraerEmail(String token) {
		return extraerClaim(token, Claims::getSubject);
	}

	@Override
	public Long obtenerUsuarioIdDelToken(String token) {
		return extraerClaim(token, claims -> claims.get("userId", Long.class));
	}

	public Long extraerUsuarioId(String token) {
		return extraerClaim(token, claims -> claims.get("userId", Long.class));
	}

	public RolUsuario extraerRol(String token) {
		String rol = extraerClaim(token, claims -> claims.get("rol", String.class));
		return RolUsuario.valueOf(rol);
	}

	private <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = validarToken(token);
		return claimsResolver.apply(claims);
	}

	@Override
	public boolean esTokenValido(String token) {
		return !esTokenExpirado(token);
	}

	private boolean esTokenExpirado(String token) {
		return extraerClaim(token, Claims::getExpiration).before(new Date());
	}
}
