package com.maquimu.aplicacion.dashboard;

import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.springframework.stereotype.Service;

/**
 * Manejador que agrega datos de multiples dominios para construir el dashboard del operario.
 */
@Service
public class ManejadorDashboardOperario {

    private final MaquinariaDao maquinariaDao;
    private final AlquilerDao alquilerDao;
    private final FacturaDao facturaDao;

    public ManejadorDashboardOperario(MaquinariaDao maquinariaDao,
                                       AlquilerDao alquilerDao,
                                       FacturaDao facturaDao) {
        this.maquinariaDao = maquinariaDao;
        this.alquilerDao = alquilerDao;
        this.facturaDao = facturaDao;
    }

    /**
     * Ejecuta la consulta del dashboard y retorna los KPIs.
     */
    public DashboardOperarioDTO ejecutar() {
        long maquinariaDisponible = maquinariaDao.contarPorEstado(EstadoMaquinaria.DISPONIBLE);
        long alquileresActivos = alquilerDao.contarPorEstado(EstadoAlquiler.ACTIVO);
        long facturasPendientes = facturaDao.contarPorEstadoPago(EstadoPago.PENDIENTE);

        return DashboardOperarioDTO.builder()
                .maquinariaDisponible(maquinariaDisponible)
                .alquileresActivos(alquileresActivos)
                .facturasPendientes(facturasPendientes)
                .build();
    }
}
