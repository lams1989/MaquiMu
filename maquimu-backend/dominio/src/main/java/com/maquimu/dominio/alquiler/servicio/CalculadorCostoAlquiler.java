package com.maquimu.dominio.alquiler.servicio;

import com.maquimu.dominio.modelo.Maquinaria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Servicio de dominio para calcular el costo de alquileres.
 * Reutilizable en múltiples casos de uso (HU 06, 07, 08, 09).
 */
public class CalculadorCostoAlquiler {

    /**
     * Calcula el costo estimado basado en días completos.
     * Fase 1: Cálculo simple por días.
     * 
     * @param maquinaria La maquinaria a alquilar
     * @param fechaInicio Fecha de inicio del alquiler
     * @param fechaFin Fecha de fin del alquiler
     * @return Costo total estimado
     */
    public BigDecimal calcularCostoPorDias(Maquinaria maquinaria, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (maquinaria == null || fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Maquinaria y fechas son obligatorios");
        }
        
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        long dias = ChronoUnit.DAYS.between(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
        
        // Mínimo 1 día
        if (dias < 1) {
            dias = 1;
        }

        return maquinaria.getTarifaPorDia().multiply(BigDecimal.valueOf(dias));
    }

    /**
     * Calcula el costo mixto con días y horas adicionales.
     * Fase 2 (HU 09): El operario puede ajustar días y horas cobrados.
     * 
     * @param maquinaria La maquinaria alquilada
     * @param diasCobrados Número de días a cobrar
     * @param horasCobradas Número de horas adicionales a cobrar
     * @return Costo total final
     */
    public BigDecimal calcularCostoMixto(Maquinaria maquinaria, int diasCobrados, int horasCobradas) {
        if (maquinaria == null) {
            throw new IllegalArgumentException("Maquinaria es obligatoria");
        }
        
        if (diasCobrados < 0 || horasCobradas < 0) {
            throw new IllegalArgumentException("Los días y horas deben ser valores positivos");
        }

        BigDecimal costoDias = maquinaria.getTarifaPorDia().multiply(BigDecimal.valueOf(diasCobrados));
        BigDecimal costoHoras = maquinaria.getTarifaPorHora().multiply(BigDecimal.valueOf(horasCobradas));

        return costoDias.add(costoHoras);
    }
}
