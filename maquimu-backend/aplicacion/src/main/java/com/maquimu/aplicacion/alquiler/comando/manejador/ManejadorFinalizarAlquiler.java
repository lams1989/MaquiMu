package com.maquimu.aplicacion.alquiler.comando.manejador;

import com.maquimu.aplicacion.alquiler.comando.ComandoFinalizarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manejador del comando para finalizar un alquiler activo.
 * Transición compleja transaccional:
 * - Alquiler: ACTIVO -> FINALIZADO
 * - Maquinaria: ALQUILADO -> DISPONIBLE
 */
@Service
public class ManejadorFinalizarAlquiler {

    private final AlquilerDao alquilerDao;
    private final AlquilerRepositorio alquilerRepositorio;
    private final MaquinariaDao maquinariaDao;
    private final MaquinariaRepositorio maquinariaRepositorio;

    public ManejadorFinalizarAlquiler(AlquilerDao alquilerDao,
                                       AlquilerRepositorio alquilerRepositorio,
                                       MaquinariaDao maquinariaDao,
                                       MaquinariaRepositorio maquinariaRepositorio) {
        this.alquilerDao = alquilerDao;
        this.alquilerRepositorio = alquilerRepositorio;
        this.maquinariaDao = maquinariaDao;
        this.maquinariaRepositorio = maquinariaRepositorio;
    }

    @Transactional
    public Alquiler ejecutar(ComandoFinalizarAlquiler comando) {
        // 1. Cargar el alquiler
        Alquiler alquiler = alquilerDao.buscarPorId(comando.getAlquilerId())
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + comando.getAlquilerId()));

        // 2. Cargar la maquinaria asociada
        Maquinaria maquinaria = maquinariaDao.buscarPorId(alquiler.getMaquinariaId())
                .orElseThrow(() -> new IllegalArgumentException("Maquinaria no encontrada con ID: " + alquiler.getMaquinariaId()));

        // 3. Ejecutar transiciones de dominio
        alquiler.finalizar();      // ACTIVO -> FINALIZADO
        maquinaria.devolver();     // ALQUILADO -> DISPONIBLE

        // 4. Persistir ambos cambios en la misma transacción
        maquinariaRepositorio.guardar(maquinaria);
        return alquilerRepositorio.guardar(alquiler);
    }
}
