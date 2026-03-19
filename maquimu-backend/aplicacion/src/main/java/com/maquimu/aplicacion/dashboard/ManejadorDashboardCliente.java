package com.maquimu.aplicacion.dashboard;

import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import org.springframework.stereotype.Service;

/**
 * Manejador que agrega datos filtrados por cliente para construir el dashboard del cliente.
 */
@Service
public class ManejadorDashboardCliente {

    private final AlquilerDao alquilerDao;
    private final FacturaDao facturaDao;

    public ManejadorDashboardCliente(AlquilerDao alquilerDao, FacturaDao facturaDao) {
        this.alquilerDao = alquilerDao;
        this.facturaDao = facturaDao;
    }

    /**
     * Ejecuta la consulta del dashboard del cliente y retorna los KPIs.
     *
     * @param clienteId ID del cliente autenticado
     * @return DTO con alquileres activos y facturas pendientes del cliente
     */
    public DashboardClienteDTO ejecutar(Long clienteId) {
        long alquileresActivos = alquilerDao.contarPorClienteYEstado(clienteId, EstadoAlquiler.ACTIVO);
        long facturasPendientes = facturaDao.contarPorClienteYEstadoPago(clienteId, EstadoPago.PENDIENTE);

        return DashboardClienteDTO.builder()
                .alquileresActivos(alquileresActivos)
                .facturasPendientes(facturasPendientes)
                .build();
    }
}
