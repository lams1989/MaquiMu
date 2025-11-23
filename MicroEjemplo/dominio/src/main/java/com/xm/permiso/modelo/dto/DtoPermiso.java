package com.xm.permiso.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DtoPermiso {
	private int permisoId;
	private String nombre;
	private String codigo;
	private boolean estado;
}
