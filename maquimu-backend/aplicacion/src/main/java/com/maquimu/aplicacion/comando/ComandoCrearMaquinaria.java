package com.maquimu.aplicacion.comando;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

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
