package com.maquimu.aplicacion.consulta.manejador;

import com.maquimu.aplicacion.consulta.ConsultaListarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManejadorListarMaquinaria {

    private final MaquinariaDao maquinariaDao;

    public ManejadorListarMaquinaria(MaquinariaDao maquinariaDao) {
        this.maquinariaDao = maquinariaDao;
    }

    public List<Maquinaria> ejecutar(ConsultaListarMaquinaria consulta) {
        return maquinariaDao.listarTodas();
    }
}
