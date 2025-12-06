package com.maquimu.aplicacion.comando;

import com.maquimu.dominio.modelo.EstadoMaquinaria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

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
