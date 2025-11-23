package com.xm.autenticacion.controlador.consulta.testdatabuilder;

import java.util.ArrayList;
import java.util.List;

import com.xm.infraestructura.sesion.modelo.dto.DtoGrupoGraph;

public class DtoGrupoGraphTestDataBuilder {
	private String nombre;

	public DtoGrupoGraphTestDataBuilder() {
		this.nombre = "SO-Redespacho";
	}

	public DtoGrupoGraph construir() {
		return new DtoGrupoGraph(nombre);
	}

	public DtoGrupoGraphTestDataBuilder conNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public List<DtoGrupoGraph> construirLista(List<String> nombres) {
		var gruposGraph = new ArrayList<DtoGrupoGraph>();
		nombres.forEach(nombreActual -> gruposGraph
				.add(new DtoGrupoGraphTestDataBuilder().conNombre(nombreActual).construir()));
		return gruposGraph;
	}
}
