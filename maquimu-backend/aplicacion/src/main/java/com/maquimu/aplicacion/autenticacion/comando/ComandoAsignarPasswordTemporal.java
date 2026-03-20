package com.maquimu.aplicacion.autenticacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComandoAsignarPasswordTemporal {
	private String passwordTemporal;
}
