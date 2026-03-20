package com.maquimu.infraestructura.maquinaria.adaptador.repositorio;

import org.springframework.stereotype.Repository;

import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.repositorio.MaquinariaRepositorio;
import com.maquimu.infraestructura.maquinaria.adaptador.entidad.MaquinariaEntity;

@Repository
public class JpaMaquinariaRepositorio implements MaquinariaRepositorio {

	private final JpaMaquinariaRepository jpaMaquinariaRepository;

	public JpaMaquinariaRepositorio(JpaMaquinariaRepository jpaMaquinariaRepository) {
		this.jpaMaquinariaRepository = jpaMaquinariaRepository;
	}

	@Override
	public Maquinaria guardar(Maquinaria maquinaria) {
		MaquinariaEntity maquinariaEntity = MaquinariaEntity.fromMaquinaria(maquinaria);
		return jpaMaquinariaRepository.save(maquinariaEntity).toMaquinaria();
	}

	@Override
	public void eliminar(Long id) {
		jpaMaquinariaRepository.deleteById(id);
	}
}
