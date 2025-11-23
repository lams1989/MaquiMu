package com.xm.rol.controlador.testdatabuilder;

import com.xm.rol.modelo.dto.DtoRolEscenario;

import java.util.List;

public class DtoRolEscenarioTestDataBuilder {
    private int rolId;
    private String nombreRol;
    private int escenarioId;
    private String nombreEscenario;

    public DtoRolEscenarioTestDataBuilder() {
        rolId = 1;
        nombreRol = "SO-RedespachoAdmin";
        escenarioId = 2;
        nombreEscenario = "Redespacho";
    }
    public DtoRolEscenario construir() {
        return new DtoRolEscenario(rolId, nombreRol, escenarioId, nombreEscenario);
    }
    public List<DtoRolEscenario> construirLista() {
        return List.of(construir());
    }
}
