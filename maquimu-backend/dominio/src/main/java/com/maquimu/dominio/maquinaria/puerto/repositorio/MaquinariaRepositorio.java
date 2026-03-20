package com.maquimu.dominio.maquinaria.puerto.repositorio;

import com.maquimu.dominio.maquinaria.modelo.Maquinaria;

public interface MaquinariaRepositorio {
	Maquinaria guardar(Maquinaria maquinaria);

	void eliminar(Long id);
}
