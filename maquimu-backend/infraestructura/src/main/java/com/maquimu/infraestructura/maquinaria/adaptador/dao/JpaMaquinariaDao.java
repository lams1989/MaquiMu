package com.maquimu.infraestructura.maquinaria.adaptador.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.maquimu.dominio.maquinaria.modelo.EstadoMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;
import com.maquimu.infraestructura.maquinaria.adaptador.entidad.MaquinariaEntity;
import com.maquimu.infraestructura.maquinaria.adaptador.repositorio.JpaMaquinariaRepository;

@Repository
public class JpaMaquinariaDao implements MaquinariaDao {

	private final JpaMaquinariaRepository jpaMaquinariaRepository;

	public JpaMaquinariaDao(JpaMaquinariaRepository jpaMaquinariaRepository) {
		this.jpaMaquinariaRepository = jpaMaquinariaRepository;
	}

	@Override
	public List<Maquinaria> listarTodas() {
		return jpaMaquinariaRepository.findAll().stream().map(MaquinariaEntity::toMaquinaria)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Maquinaria> buscarPorId(Long id) {
		return jpaMaquinariaRepository.findById(id).map(MaquinariaEntity::toMaquinaria);
	}

	@Override
	public Optional<Maquinaria> buscarPorSerial(String serial) {
		return jpaMaquinariaRepository.findBySerial(serial).map(MaquinariaEntity::toMaquinaria);
	}

	@Override
	public boolean existePorSerial(String serial) {
		return jpaMaquinariaRepository.existsBySerial(serial);
	}

	@Override
	public long contarPorEstado(EstadoMaquinaria estado) {
		return jpaMaquinariaRepository.countByEstado(estado);
	}
}
