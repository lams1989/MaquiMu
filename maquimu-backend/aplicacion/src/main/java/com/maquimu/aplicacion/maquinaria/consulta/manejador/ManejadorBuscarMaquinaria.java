package com.maquimu.aplicacion.maquinaria.consulta.manejador;

import org.springframework.stereotype.Service;

import com.maquimu.aplicacion.maquinaria.consulta.ConsultaBuscarMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;

@Service
public class ManejadorBuscarMaquinaria {

	private final MaquinariaDao maquinariaDao;

	public ManejadorBuscarMaquinaria(MaquinariaDao maquinariaDao) {
		this.maquinariaDao = maquinariaDao;
	}

	public Maquinaria ejecutar(ConsultaBuscarMaquinaria consulta) {
		return maquinariaDao.buscarPorId(consulta.getMaquinariaId()).orElseThrow(() -> new IllegalArgumentException(
				String.format("La maquinaria con ID %d no existe.", consulta.getMaquinariaId())));
	}
}
