package com.maquimu.aplicacion.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO con los KPIs del dashboard del cliente.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardClienteDTO {
    private long alquileresActivos;
    private long facturasPendientes;
}
