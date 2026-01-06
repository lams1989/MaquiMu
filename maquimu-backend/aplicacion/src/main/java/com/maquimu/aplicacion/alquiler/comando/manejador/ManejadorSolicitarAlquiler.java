package com.maquimu.aplicacion.alquiler.comando.manejador;

import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.aplicacion.alquiler.servicio.ServicioGestionAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manejador del comando para solicitar alquiler.
 * Delega la lógica al ServicioGestionAlquiler.
 */
@Service
public class ManejadorSolicitarAlquiler {

    private final ServicioGestionAlquiler servicioGestionAlquiler;

    public ManejadorSolicitarAlquiler(ServicioGestionAlquiler servicioGestionAlquiler) {
        this.servicioGestionAlquiler = servicioGestionAlquiler;
    }

    @Transactional
    public Alquiler ejecutar(ComandoSolicitarAlquiler comando) {
        return servicioGestionAlquiler.solicitarAlquiler(comando);
    }
}
