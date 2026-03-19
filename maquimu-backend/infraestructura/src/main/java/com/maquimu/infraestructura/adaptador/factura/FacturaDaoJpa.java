package com.maquimu.infraestructura.adaptador.factura;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.infraestructura.adaptador.entidad.FacturaEntity;
import com.maquimu.infraestructura.adaptador.repositorio.JpaFacturaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FacturaDaoJpa implements FacturaDao {

    private final JpaFacturaRepository jpaFacturaRepository;

    public FacturaDaoJpa(JpaFacturaRepository jpaFacturaRepository) {
        this.jpaFacturaRepository = jpaFacturaRepository;
    }

    @Override
    public List<Factura> listarTodas() {
        return jpaFacturaRepository.findAll().stream()
                .map(FacturaEntity::toFactura)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Factura> buscarPorId(Long facturaId) {
        return jpaFacturaRepository.findById(facturaId)
                .map(FacturaEntity::toFactura);
    }

    @Override
    public Optional<Factura> buscarPorAlquilerId(Long alquilerId) {
        return jpaFacturaRepository.findByAlquiler_AlquilerId(alquilerId)
                .map(FacturaEntity::toFactura);
    }

    @Override
    public List<Factura> buscarPorClienteId(Long clienteId) {
        return jpaFacturaRepository.findByCliente_ClienteId(clienteId).stream()
                .map(FacturaEntity::toFactura)
                .collect(Collectors.toList());
    }

    @Override
    public List<Factura> listarPorEstadoPago(EstadoPago estadoPago) {
        return jpaFacturaRepository.findByEstadoPago(estadoPago).stream()
                .map(FacturaEntity::toFactura)
                .collect(Collectors.toList());
    }

    @Override
    public long contarPorEstadoPago(EstadoPago estadoPago) {
        return jpaFacturaRepository.countByEstadoPago(estadoPago);
    }

    @Override
    public long contarPorClienteYEstadoPago(Long clienteId, EstadoPago estadoPago) {
        return jpaFacturaRepository.countByCliente_ClienteIdAndEstadoPago(clienteId, estadoPago);
    }
}
