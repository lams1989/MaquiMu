package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoEliminarMaquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManejadorEliminarMaquinaria {

    private final MaquinariaRepositorio maquinariaRepositorio;
    private final MaquinariaDao maquinariaDao;

    public ManejadorEliminarMaquinaria(MaquinariaRepositorio maquinariaRepositorio, MaquinariaDao maquinariaDao) {
        this.maquinariaRepositorio = maquinariaRepositorio;
        this.maquinariaDao = maquinariaDao;
    }

    @Transactional
    public void ejecutar(ComandoEliminarMaquinaria comando) {
        maquinariaDao.buscarPorId(comando.getMaquinariaId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("La maquinaria con ID %d no existe.", comando.getMaquinariaId())));

        maquinariaRepositorio.eliminar(comando.getMaquinariaId());
    }
}
