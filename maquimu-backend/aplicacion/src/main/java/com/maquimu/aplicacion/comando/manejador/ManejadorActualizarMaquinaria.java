package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoActualizarMaquinaria;
import com.maquimu.aplicacion.comando.fabrica.FabricaMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManejadorActualizarMaquinaria {

    private final MaquinariaRepositorio maquinariaRepositorio;
    private final MaquinariaDao maquinariaDao;
    private final FabricaMaquinaria fabricaMaquinaria;

    public ManejadorActualizarMaquinaria(MaquinariaRepositorio maquinariaRepositorio, MaquinariaDao maquinariaDao, FabricaMaquinaria fabricaMaquinaria) {
        this.maquinariaRepositorio = maquinariaRepositorio;
        this.maquinariaDao = maquinariaDao;
        this.fabricaMaquinaria = fabricaMaquinaria;
    }

    @Transactional
    public Maquinaria ejecutar(ComandoActualizarMaquinaria comando) {
        Maquinaria maquinariaExistente = maquinariaDao.buscarPorId(comando.getMaquinariaId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("La maquinaria con ID %d no existe.", comando.getMaquinariaId())));

        // Validar unicidad del serial si ha cambiado y no pertenece a la misma maquinaria
        if (comando.getSerial() != null && !comando.getSerial().equals(maquinariaExistente.getSerial())) {
            if (maquinariaDao.existePorSerial(comando.getSerial())) {
                throw new IllegalArgumentException(String.format("La maquinaria con serial %s ya existe para otra máquina.", comando.getSerial()));
            }
        }

        fabricaMaquinaria.actualizar(maquinariaExistente, comando);
        return maquinariaRepositorio.guardar(maquinariaExistente);
    }
}
