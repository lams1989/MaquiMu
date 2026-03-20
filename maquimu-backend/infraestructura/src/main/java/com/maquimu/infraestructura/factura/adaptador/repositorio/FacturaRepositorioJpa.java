package com.maquimu.infraestructura.factura.adaptador.repositorio;

import org.springframework.stereotype.Component;

import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.repositorio.FacturaRepositorio;
import com.maquimu.infraestructura.alquiler.adaptador.entidad.AlquilerEntity;
import com.maquimu.infraestructura.cliente.adaptador.entidad.ClienteEntity;
import com.maquimu.infraestructura.factura.adaptador.entidad.FacturaEntity;
import com.maquimu.infraestructura.alquiler.adaptador.repositorio.JpaAlquilerRepository;
import com.maquimu.infraestructura.cliente.adaptador.repositorio.JpaClienteRepository;

@Component
public class FacturaRepositorioJpa implements FacturaRepositorio {

	private final JpaFacturaRepository jpaFacturaRepository;
	private final JpaAlquilerRepository jpaAlquilerRepository;
	private final JpaClienteRepository jpaClienteRepository;

	public FacturaRepositorioJpa(JpaFacturaRepository jpaFacturaRepository, JpaAlquilerRepository jpaAlquilerRepository,
			JpaClienteRepository jpaClienteRepository) {
		this.jpaFacturaRepository = jpaFacturaRepository;
		this.jpaAlquilerRepository = jpaAlquilerRepository;
		this.jpaClienteRepository = jpaClienteRepository;
	}

	@Override
	public Factura guardar(Factura factura) {
		AlquilerEntity alquilerEntity = jpaAlquilerRepository.findById(factura.getAlquilerId()).orElseThrow(
				() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + factura.getAlquilerId()));

		ClienteEntity clienteEntity = jpaClienteRepository.findById(factura.getClienteId()).orElseThrow(
				() -> new IllegalArgumentException("Cliente no encontrado con ID: " + factura.getClienteId()));

		FacturaEntity entity = FacturaEntity.fromFactura(factura, alquilerEntity, clienteEntity);
		FacturaEntity saved = jpaFacturaRepository.save(entity);
		return saved.toFactura();
	}
}
