package com.maquimu.aplicacion.autenticacion.consulta;

import com.maquimu.dominio.autenticacion.modelo.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoAutenticacion {
	private String token;
	private Usuario usuario;
}
