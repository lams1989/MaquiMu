package com.maquimu.aplicacion.consulta.manejador;

import com.maquimu.aplicacion.consulta.ConsultaBuscarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.springframework.stereotype.Service;

@Service
public class ManejadorBuscarMaquinaria {

    private final MaquinariaDao maquinariaDao;

    public ManejadorBuscarMaquinaria(MaquinariaDao maquinariaDao) {
        this.maquinariaDao = maquinariaDao;
    }

    public Maquinaria ejecutar(ConsultaBuscarMaquinaria consulta) {
        return maquinariaDao.buscarPorId(consulta.getMaquinariaId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("La maquinaria con ID %d no existe.", consulta.getMaquinariaId())));
    }
}
