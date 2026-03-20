package com.maquimu.infraestructura.maquinaria.adaptador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maquimu.dominio.maquinaria.modelo.EstadoMaquinaria;
import com.maquimu.infraestructura.maquinaria.adaptador.entidad.MaquinariaEntity;

public interface JpaMaquinariaRepository extends JpaRepository<MaquinariaEntity, Long> {
	Optional<MaquinariaEntity> findBySerial(String serial);

	boolean existsBySerial(String serial);

	long countByEstado(EstadoMaquinaria estado);
}
