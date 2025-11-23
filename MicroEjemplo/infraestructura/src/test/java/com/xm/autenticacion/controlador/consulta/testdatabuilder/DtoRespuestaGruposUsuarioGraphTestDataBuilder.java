package com.xm.autenticacion.controlador.consulta.testdatabuilder;

import java.util.List;

import com.xm.infraestructura.sesion.modelo.dto.DtoGrupoGraph;
import com.xm.infraestructura.sesion.modelo.dto.DtoRespuestaGruposUsuarioGraph;

public class DtoRespuestaGruposUsuarioGraphTestDataBuilder {

	private String contextoOData;
	private String siguienteEnlace;

	public DtoRespuestaGruposUsuarioGraphTestDataBuilder() {
		this.contextoOData = "https://graph.microsoft.com/v1.0/$metadata#groups(displayName)";
		this.siguienteEnlace = null;
	}

	public DtoRespuestaGruposUsuarioGraph contruir(List<DtoGrupoGraph> listaNombres) {
		return new DtoRespuestaGruposUsuarioGraph(contextoOData, siguienteEnlace, listaNombres);
	}
}
