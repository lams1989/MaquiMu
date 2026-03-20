package com.maquimu.infraestructura.alquiler.adaptador.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.infraestructura.alquiler.adaptador.entidad.AlquilerEntity;
import com.maquimu.infraestructura.alquiler.adaptador.repositorio.JpaAlquilerRepository;

@Component
public class AlquilerDaoJpa implements AlquilerDao {

	private final JpaAlquilerRepository jpaAlquilerRepository;

	public AlquilerDaoJpa(JpaAlquilerRepository jpaAlquilerRepository) {
		this.jpaAlquilerRepository = jpaAlquilerRepository;
	}

	@Override
	public Optional<Alquiler> buscarPorId(Long id) {
		return jpaAlquilerRepository.findById(id).map(AlquilerEntity::toAlquiler);
	}

	@Override
	public List<Alquiler> buscarPorCliente(Long clienteId) {
		return jpaAlquilerRepository.findByClienteClienteId(clienteId).stream().map(AlquilerEntity::toAlquiler)
				.collect(Collectors.toList());
	}

	@Override
	public List<Alquiler> buscarPorMaquinariaYRangoFechas(Long maquinariaId, LocalDateTime fechaInicio,
			LocalDateTime fechaFin) {
		return jpaAlquilerRepository.findConflictingRentals(maquinariaId, fechaInicio, fechaFin).stream()
				.map(AlquilerEntity::toAlquiler).collect(Collectors.toList());
	}

	@Override
	public List<Alquiler> listarTodos() {
		return jpaAlquilerRepository.findAll().stream().map(AlquilerEntity::toAlquiler).collect(Collectors.toList());
	}

	@Override
	public List<Alquiler> listarPendientes() {
		return jpaAlquilerRepository.findByEstado(EstadoAlquiler.PENDIENTE).stream().map(AlquilerEntity::toAlquiler)
				.collect(Collectors.toList());
	}

	@Override
	public List<Alquiler> listarPorEstado(EstadoAlquiler estado) {
		return jpaAlquilerRepository.findByEstado(estado).stream().map(AlquilerEntity::toAlquiler)
				.collect(Collectors.toList());
	}

	@Override
	public long contarPorEstado(EstadoAlquiler estado) {
		return jpaAlquilerRepository.countByEstado(estado);
	}

	@Override
	public long contarPorClienteYEstado(Long clienteId, EstadoAlquiler estado) {
		return jpaAlquilerRepository.countByClienteClienteIdAndEstado(clienteId, estado);
	}
}
