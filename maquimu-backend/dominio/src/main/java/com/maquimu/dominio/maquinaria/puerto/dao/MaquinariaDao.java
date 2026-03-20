package com.maquimu.dominio.maquinaria.puerto.dao;

import java.util.List;
import java.util.Optional;

import com.maquimu.dominio.maquinaria.modelo.EstadoMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;

public interface MaquinariaDao {
	List<Maquinaria> listarTodas();

	Optional<Maquinaria> buscarPorId(Long id);

	Optional<Maquinaria> buscarPorSerial(String serial);

	boolean existePorSerial(String serial);

	long contarPorEstado(EstadoMaquinaria estado); // Added for HU 11
}
