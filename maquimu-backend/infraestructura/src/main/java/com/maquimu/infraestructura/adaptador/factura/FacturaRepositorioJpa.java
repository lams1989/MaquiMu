package com.maquimu.infraestructura.adaptador.factura;

import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.repositorio.FacturaRepositorio;
import com.maquimu.infraestructura.adaptador.entidad.AlquilerEntity;
import com.maquimu.infraestructura.adaptador.entidad.ClienteEntity;
import com.maquimu.infraestructura.adaptador.entidad.FacturaEntity;
import com.maquimu.infraestructura.adaptador.repositorio.JpaAlquilerRepository;
import com.maquimu.infraestructura.adaptador.repositorio.JpaClienteRepository;
import com.maquimu.infraestructura.adaptador.repositorio.JpaFacturaRepository;
import org.springframework.stereotype.Component;

@Component
public class FacturaRepositorioJpa implements FacturaRepositorio {

    private final JpaFacturaRepository jpaFacturaRepository;
    private final JpaAlquilerRepository jpaAlquilerRepository;
    private final JpaClienteRepository jpaClienteRepository;

    public FacturaRepositorioJpa(JpaFacturaRepository jpaFacturaRepository,
                                  JpaAlquilerRepository jpaAlquilerRepository,
                                  JpaClienteRepository jpaClienteRepository) {
        this.jpaFacturaRepository = jpaFacturaRepository;
        this.jpaAlquilerRepository = jpaAlquilerRepository;
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Factura guardar(Factura factura) {
        AlquilerEntity alquilerEntity = jpaAlquilerRepository.findById(factura.getAlquilerId())
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + factura.getAlquilerId()));

        ClienteEntity clienteEntity = jpaClienteRepository.findById(factura.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + factura.getClienteId()));

        FacturaEntity entity = FacturaEntity.fromFactura(factura, alquilerEntity, clienteEntity);
        FacturaEntity saved = jpaFacturaRepository.save(entity);
        return saved.toFactura();
    }
}
