package com.xm.autenticacion.servicio.testdatabuilder;

import java.util.ArrayList;
import java.util.List;

import com.xm.rol.modelo.dto.DtoRolEscenario;

public class DtoRolEscenarioTestDataBuilder {
	public static final int INDICE_INICAL = 1;
	private int rolId;
	private String nombreRol;
	private int escenarioId;
	private String nombreEscenario;

	public DtoRolEscenarioTestDataBuilder() {
		this.rolId = 1;
		this.nombreRol = "SO - Despacho";
		this.escenarioId = 1;
		this.nombreEscenario = "Despacho";
	}

	public DtoRolEscenario construir() {
		return new DtoRolEscenario(rolId, nombreRol, escenarioId, nombreEscenario);
	}

	public DtoRolEscenarioTestDataBuilder conNombre(String nombre) {
		this.nombreRol = nombre;
		return this;
	}

	public DtoRolEscenarioTestDataBuilder conRolId(int rolId) {
		this.rolId = rolId;
		return this;
	}

	public List<DtoRolEscenario> construirLista(int listaItems) {
		var rolesEscenario = new ArrayList<DtoRolEscenario>();
		for (int indice = INDICE_INICAL; indice <= listaItems; indice++) {
			rolesEscenario.add(new DtoRolEscenarioTestDataBuilder().construir());
		}
		return rolesEscenario;
	}
}
