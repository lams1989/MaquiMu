package com.maquimu.aplicacion.maquinaria.consulta.manejador;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;

@Service
public class ManejadorListarMaquinaria {

	private final MaquinariaDao maquinariaDao;

	public ManejadorListarMaquinaria(MaquinariaDao maquinariaDao) {
		this.maquinariaDao = maquinariaDao;
	}

	public List<Maquinaria> ejecutar() {
		return maquinariaDao.listarTodas();
	}
}
