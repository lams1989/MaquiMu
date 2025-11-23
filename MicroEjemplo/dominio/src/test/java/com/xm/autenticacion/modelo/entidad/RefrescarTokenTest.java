package com.xm.autenticacion.modelo.entidad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.xm.autenticacion.servicio.testdatabuilder.RefrescarTokenTestDataBuilder;
import com.xm.core.BasePrueba;

public class RefrescarTokenTest {

	private static final String EL_CAMPO_TOKEN_DE_ACCESO_ES_REQUERIDO = "El campo tokenDeAcceso es requerido.";
	private static final String EL_CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO = "El campo tokenDeAcceso no puede ser vacío.";
	private static final String EL_CAMPO_TOKEN_DE_REFRESCO_ES_REQUERIDO = "El campo tokenDeRefresco es requerido.";
	private static final String EL_CAMPO_TOKEN_DE_REFRESCO_NO_PUEDE_SER_VACIO = "El campo tokenDeRefresco no puede ser vacío.";

	@Test
	public void conTokenAccesoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new RefrescarTokenTestDataBuilder().conTokenAcceso(null).construir(),
				RuntimeException.class, EL_CAMPO_TOKEN_DE_ACCESO_ES_REQUERIDO);
	}

	@Test
	public void conTokenAccesoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new RefrescarTokenTestDataBuilder().conTokenAcceso("").construir(),
				RuntimeException.class, EL_CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO);
	}

	@Test
	public void conTokenRefrescoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new RefrescarTokenTestDataBuilder().conTokenRefresco(null).construir(),
				RuntimeException.class, EL_CAMPO_TOKEN_DE_REFRESCO_ES_REQUERIDO);
	}

	@Test
	public void conTokenRefrescoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new RefrescarTokenTestDataBuilder().conTokenRefresco("").construir(),
				RuntimeException.class, EL_CAMPO_TOKEN_DE_REFRESCO_NO_PUEDE_SER_VACIO);
	}

	@Test
	void creacionEntidadCorrectamenteTest() {
		RefrescarToken refrescarToken = new RefrescarTokenTestDataBuilder().construir();

		assertNotNull(refrescarToken);
		assertNotNull(refrescarToken.getTokenDeAcceso());
		assertNotNull(refrescarToken.getTokenDeRefresco());

		assertFalse(refrescarToken.getTokenDeAcceso().isEmpty());
		assertFalse(refrescarToken.getTokenDeRefresco().isEmpty());
	}
}
