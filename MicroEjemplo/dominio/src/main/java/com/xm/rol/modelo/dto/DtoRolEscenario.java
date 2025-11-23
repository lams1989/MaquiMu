package com.xm.rol.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRolEscenario {
	private int rolId;
	private String nombreRol;
	private int escenarioId;
	private String nombreEscenario;
}
