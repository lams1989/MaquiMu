package com.maquimu.aplicacion.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO con los KPIs del dashboard del operario.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOperarioDTO {
    private long maquinariaDisponible;
    private long alquileresActivos;
    private long facturasPendientes;
}
