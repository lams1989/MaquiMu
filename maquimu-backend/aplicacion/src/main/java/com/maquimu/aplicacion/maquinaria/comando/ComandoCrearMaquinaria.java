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
public class ComandoCrearMaquinaria {
	private String nombreEquipo;
	private String marca;
	private String modelo;
	private String serial;
	private BigDecimal tarifaPorDia;
	private BigDecimal tarifaPorHora;
	private String descripcion;
}
