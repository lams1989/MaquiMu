package com.xm.autenticacion.servicio.testdatabuilder;

import java.util.HashMap;
import java.util.Map;

public class ClaimsOriginalesTestDataBuilder {

	public static final String NOMBRE_COMPLETO = "NombreCompleto";
	public static final String EMAIL = "Email";
	public static final String ROLES = "Roles";
	public static final String IDENTIFICADOR_USUARIO = "IdUsuario";

	private Map<String, Object> claimsRequeridos;

	public ClaimsOriginalesTestDataBuilder() {
		this.claimsRequeridos = new HashMap<>();
		this.claimsRequeridos.put(NOMBRE_COMPLETO, "user");
		this.claimsRequeridos.put(EMAIL, "user@xm.com.co");
		this.claimsRequeridos.put(ROLES, "admin");
		this.claimsRequeridos.put(IDENTIFICADOR_USUARIO, "123456789");
	}

	public Map<String, Object> build() {
		return this.claimsRequeridos;
	}

}
