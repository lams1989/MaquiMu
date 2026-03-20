package com.maquimu.infraestructura.cliente.adaptador.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maquimu.infraestructura.cliente.adaptador.entidad.ClienteEntity;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
	Optional<ClienteEntity> findByIdentificacion(String identificacion);

	Optional<ClienteEntity> findByEmail(String email);

	Optional<ClienteEntity> findByUsuarioId(Long usuarioId);

	boolean existsByIdentificacion(String identificacion);

	boolean existsByEmail(String email);
}
