package com.xm.autenticacion.modelo.entidad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.xm.autenticacion.servicio.testdatabuilder.UsuarioTestDataBuilder;
import com.xm.core.BasePrueba;

class UsuarioTest {

	private static final String CAMPO_ID_OBJETO_REQUERIDO = "El campo idObjeto es requerido.";
	private static final String CAMPO_NOMBRE_REQUERIDO = "El campo nombre es requerido.";
	private static final String CAMPO_NOMBRE_COMPLETO_REQUERIDO = "El nombreCompleto idObjeto es requerido.";
	private static final String CAMPO_CORREO_ELECTRONICO_REQUERIDO = "El campo correoElectronico es requerido.";
	private static final String CAMPO_TOKEN_DE_ACCESO_REQUERIDO = "El campo tokenDeAcceso es requerido.";
	private static final String CAMPO_ID_OBJETO_NO_PUEDE_SER_VACIO = "El campo idObjeto no puede ser vacío.";
	private static final String CAMPO_NOMBRE_NO_PUEDE_SER_VACIO = "El campo nombre no puede ser vacío.";
	private static final String CAMPO_NOMBRE_COMPLETO_NO_PUEDE_SER_VACIO = "El nombreCompleto idObjeto no puede ser vacío.";
	private static final String CAMPO_CORREO_ELECTRONICO_NO_PUEDE_SER_VACIO = "El campo correoElectronico no puede ser vacío.";
	private static final String CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO = "El campo tokenDeAcceso no puede ser vacío.";

	@Test
	void conIdObjetoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conIdObjeto(null).construir(),
				RuntimeException.class, CAMPO_ID_OBJETO_REQUERIDO);
	}

	@Test
	void conIdObjetoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conIdObjeto("").construir(), RuntimeException.class,
				CAMPO_ID_OBJETO_NO_PUEDE_SER_VACIO);
	}

	@Test
	void conNombreNuloErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conNombre(null).construir(), RuntimeException.class,
				CAMPO_NOMBRE_REQUERIDO);
	}

	@Test
	void conNombreVacioErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conNombre("").construir(), RuntimeException.class,
				CAMPO_NOMBRE_NO_PUEDE_SER_VACIO);
	}

	@Test
	void conNombreCompletoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conNombreCompleto(null).construir(),
				RuntimeException.class, CAMPO_NOMBRE_COMPLETO_REQUERIDO);
	}

	@Test
	void conNombreCompletoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conNombreCompleto("").construir(),
				RuntimeException.class, CAMPO_NOMBRE_COMPLETO_NO_PUEDE_SER_VACIO);
	}

	@Test
	void conCorreoElectronicoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conCorreoElectronico(null).construir(),
				RuntimeException.class, CAMPO_CORREO_ELECTRONICO_REQUERIDO);
	}

	@Test
	void conCorreoElectronicoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conCorreoElectronico("").construir(),
				RuntimeException.class, CAMPO_CORREO_ELECTRONICO_NO_PUEDE_SER_VACIO);
	}

	@Test
	void conTokenDeAccesoNuloErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conTokenDeAcceso(null).construir(),
				RuntimeException.class, CAMPO_TOKEN_DE_ACCESO_REQUERIDO);
	}

	@Test
	void conTokenDeAccesoVacioErrorTest() {
		BasePrueba.assertThrows(() -> new UsuarioTestDataBuilder().conTokenDeAcceso("").construir(),
				RuntimeException.class, CAMPO_TOKEN_DE_ACCESO_NO_PUEDE_SER_VACIO);
	}

	@Test
	void creacionEntidadCorrectamenteTest() {
		Usuario usuario = new UsuarioTestDataBuilder().construir();

		assertNotNull(usuario);
		assertNotNull(usuario.getIdObjeto());
		assertNotNull(usuario.getNombre());
		assertNotNull(usuario.getNombreCompleto());
		assertNotNull(usuario.getCorreoElectronico());
		assertNotNull(usuario.getTokenDeAcceso());

		assertFalse(usuario.getIdObjeto().isEmpty());
		assertFalse(usuario.getNombre().isEmpty());
		assertFalse(usuario.getNombreCompleto().isEmpty());
		assertFalse(usuario.getCorreoElectronico().isEmpty());
		assertFalse(usuario.getTokenDeAcceso().isEmpty());
	}

}