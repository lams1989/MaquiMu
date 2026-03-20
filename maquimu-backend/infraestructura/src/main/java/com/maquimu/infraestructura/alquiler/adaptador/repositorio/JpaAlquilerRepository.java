package com.maquimu.infraestructura.alquiler.adaptador.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.infraestructura.alquiler.adaptador.entidad.AlquilerEntity;

@Repository
public interface JpaAlquilerRepository extends JpaRepository<AlquilerEntity, Long> {

	List<AlquilerEntity> findByClienteClienteId(Long clienteId);

	List<AlquilerEntity> findByMaquinariaMaquinariaId(Long maquinariaId);

	@Query("SELECT a FROM AlquilerEntity a WHERE a.maquinaria.maquinariaId = :maquinariaId "
			+ "AND a.estado NOT IN (com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.CANCELADO, "
			+ "com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.FINALIZADO, "
			+ "com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.RECHAZADO) "
			+ "AND ((a.fechaInicio BETWEEN :fechaInicio AND :fechaFin) "
			+ "OR (a.fechaFin BETWEEN :fechaInicio AND :fechaFin) "
			+ "OR (a.fechaInicio <= :fechaInicio AND a.fechaFin >= :fechaFin))")
	List<AlquilerEntity> findConflictingRentals(@Param("maquinariaId") Long maquinariaId,
			@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AlquilerEntity a "
			+ "WHERE a.maquinaria.maquinariaId = :maquinariaId "
			+ "AND a.estado NOT IN (com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.CANCELADO, "
			+ "com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.FINALIZADO, "
			+ "com.maquimu.dominio.alquiler.modelo.EstadoAlquiler.RECHAZADO) "
			+ "AND ((a.fechaInicio BETWEEN :fechaInicio AND :fechaFin) "
			+ "OR (a.fechaFin BETWEEN :fechaInicio AND :fechaFin) "
			+ "OR (a.fechaInicio <= :fechaInicio AND a.fechaFin >= :fechaFin))")
	boolean existsConflictingRentals(@Param("maquinariaId") Long maquinariaId,
			@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

	List<AlquilerEntity> findByEstado(EstadoAlquiler estado);

	long countByEstado(EstadoAlquiler estado);

	long countByClienteClienteIdAndEstado(Long clienteId, EstadoAlquiler estado);
}
