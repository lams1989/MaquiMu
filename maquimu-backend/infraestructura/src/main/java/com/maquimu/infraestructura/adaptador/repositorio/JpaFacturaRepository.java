package com.maquimu.infraestructura.adaptador.repositorio;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.infraestructura.adaptador.entidad.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFacturaRepository extends JpaRepository<FacturaEntity, Long> {
    Optional<FacturaEntity> findByAlquiler_AlquilerId(Long alquilerId);
    List<FacturaEntity> findByCliente_ClienteId(Long clienteId);
    List<FacturaEntity> findByEstadoPago(EstadoPago estadoPago);
    long countByEstadoPago(EstadoPago estadoPago);
}
