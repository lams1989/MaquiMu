package com.maquimu.aplicacion.alquiler.comando.manejador;

import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manejador del comando para aprobar un alquiler.
 * Transición: PENDIENTE -> APROBADO.
 * Asigna el operario que aprueba.
 */
@Service
public class ManejadorAprobarAlquiler {

    private final AlquilerDao alquilerDao;
    private final AlquilerRepositorio alquilerRepositorio;

    public ManejadorAprobarAlquiler(AlquilerDao alquilerDao, AlquilerRepositorio alquilerRepositorio) {
        this.alquilerDao = alquilerDao;
        this.alquilerRepositorio = alquilerRepositorio;
    }

    @Transactional
    public Alquiler ejecutar(ComandoAprobarAlquiler comando) {
        Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId())
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

        alquiler.aprobar(comando.getUsuarioId());

        return alquilerRepositorio.guardar(alquiler);
    }
}
