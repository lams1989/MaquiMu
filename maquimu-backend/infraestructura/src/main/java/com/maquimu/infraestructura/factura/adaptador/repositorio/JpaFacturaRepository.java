package com.maquimu.infraestructura.factura.adaptador.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.infraestructura.factura.adaptador.entidad.FacturaEntity;

@Repository
public interface JpaFacturaRepository extends JpaRepository<FacturaEntity, Long> {
	Optional<FacturaEntity> findByAlquiler_AlquilerId(Long alquilerId);

	List<FacturaEntity> findByCliente_ClienteId(Long clienteId);

	List<FacturaEntity> findByEstadoPago(EstadoPago estadoPago);

	long countByEstadoPago(EstadoPago estadoPago);

	long countByCliente_ClienteIdAndEstadoPago(Long clienteId, EstadoPago estadoPago);
}
