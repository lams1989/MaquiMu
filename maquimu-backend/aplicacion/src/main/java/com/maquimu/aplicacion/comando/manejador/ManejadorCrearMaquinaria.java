package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoCrearMaquinaria;
import com.maquimu.aplicacion.comando.fabrica.FabricaMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManejadorCrearMaquinaria {

    private final MaquinariaRepositorio maquinariaRepositorio;
    private final MaquinariaDao maquinariaDao;
    private final FabricaMaquinaria fabricaMaquinaria;

    public ManejadorCrearMaquinaria(MaquinariaRepositorio maquinariaRepositorio, MaquinariaDao maquinariaDao, FabricaMaquinaria fabricaMaquinaria) {
        this.maquinariaRepositorio = maquinariaRepositorio;
        this.maquinariaDao = maquinariaDao;
        this.fabricaMaquinaria = fabricaMaquinaria;
    }

    @Transactional
    public Maquinaria ejecutar(ComandoCrearMaquinaria comando) {
        if (maquinariaDao.existePorSerial(comando.getSerial())) {
            throw new IllegalArgumentException(String.format("La maquinaria con serial %s ya existe.", comando.getSerial()));
        }
        Maquinaria maquinaria = fabricaMaquinaria.crear(comando);
        return maquinariaRepositorio.guardar(maquinaria);
    }
}
