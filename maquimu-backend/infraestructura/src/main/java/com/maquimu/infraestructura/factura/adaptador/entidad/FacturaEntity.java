package com.maquimu.infraestructura.factura.adaptador.entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;

import com.maquimu.infraestructura.alquiler.adaptador.entidad.AlquilerEntity;
import com.maquimu.infraestructura.cliente.adaptador.entidad.ClienteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "factura_id")
	private Long facturaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alquiler_id", nullable = false)
	private AlquilerEntity alquiler;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private ClienteEntity cliente;

	@Column(name = "fecha_emision", nullable = false)
	private LocalDate fechaEmision;

	@Column(name = "monto", nullable = false, precision = 12, scale = 2)
	private BigDecimal monto;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_pago", nullable = false, columnDefinition = "varchar(50)")
	private EstadoPago estadoPago;

	public static FacturaEntity fromFactura(Factura factura, AlquilerEntity alquilerEntity,
			ClienteEntity clienteEntity) {
		FacturaEntity entity = new FacturaEntity();
		entity.setFacturaId(factura.getFacturaId());
		entity.setAlquiler(alquilerEntity);
		entity.setCliente(clienteEntity);
		entity.setFechaEmision(factura.getFechaEmision());
		entity.setMonto(factura.getMonto());
		entity.setEstadoPago(factura.getEstadoPago());
		return entity;
	}

	public Factura toFactura() {
		return new Factura(facturaId, alquiler.getAlquilerId(), cliente.getClienteId(), fechaEmision, monto,
				estadoPago);
	}
}
