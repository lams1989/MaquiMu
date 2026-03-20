package com.maquimu.aplicacion.maquinaria.comando;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComandoActualizarMaquinaria {
	private Long maquinariaId;
	private String nombreEquipo;
	private String marca;
	private String modelo;
	private String serial;
	private String estado; // Estado de la maquinaria (String para poder validar antes de convertir a Enum)
	private BigDecimal tarifaPorDia;
	private BigDecimal tarifaPorHora;
	private String descripcion;
}
