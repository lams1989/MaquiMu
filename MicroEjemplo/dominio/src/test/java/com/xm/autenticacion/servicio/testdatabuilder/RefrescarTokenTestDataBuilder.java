package com.xm.autenticacion.servicio.testdatabuilder;

import com.xm.autenticacion.modelo.entidad.RefrescarToken;

public class RefrescarTokenTestDataBuilder {

	private String tokenDeAcceso;
	private String tokenDeRefresco;

	public RefrescarTokenTestDataBuilder() {
		this.tokenDeAcceso = "access";
		this.tokenDeRefresco = "refresh";
	}

	public RefrescarTokenTestDataBuilder conTokenAcceso(String tokenAcceso) {
		this.tokenDeAcceso = tokenAcceso;
		return this;
	}

	public RefrescarTokenTestDataBuilder conTokenRefresco(String tokenRefresco) {
		this.tokenDeRefresco = tokenRefresco;
		return this;
	}

	public RefrescarToken construir() {
		return new RefrescarToken(tokenDeAcceso, tokenDeRefresco);
	}
}
