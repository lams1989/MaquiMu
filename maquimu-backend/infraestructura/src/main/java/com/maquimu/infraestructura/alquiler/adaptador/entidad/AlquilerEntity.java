package com.maquimu.infraestructura.alquiler.adaptador.entidad;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;

import com.maquimu.infraestructura.cliente.adaptador.entidad.ClienteEntity;
import com.maquimu.infraestructura.maquinaria.adaptador.entidad.MaquinariaEntity;
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
@Table(name = "alquileres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlquilerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alquiler_id")
	private Long alquilerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private ClienteEntity cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maquinaria_id", nullable = false)
	private MaquinariaEntity maquinaria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity usuario;

	@Column(name = "fecha_inicio", nullable = false)
	private LocalDateTime fechaInicio;

	@Column(name = "fecha_fin", nullable = false)
	private LocalDateTime fechaFin;

	@Column(name = "costo_total", nullable = false)
	private BigDecimal costoTotal;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_alquiler", nullable = false)
	private EstadoAlquiler estado;

	@Column(name = "motivo_rechazo", length = 500)
	private String motivoRechazo;

	@Column(name = "fecha_fin_solicitada")
	private LocalDateTime fechaFinSolicitada;

	@Column(name = "costo_adicional")
	private BigDecimal costoAdicional;

	public static AlquilerEntity fromAlquiler(Alquiler alquiler, ClienteEntity clienteEntity,
			MaquinariaEntity maquinariaEntity,
			com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity usuarioEntity) {
		AlquilerEntity entity = new AlquilerEntity();
		entity.setAlquilerId(alquiler.getId());
		entity.setCliente(clienteEntity);
		entity.setMaquinaria(maquinariaEntity);
		entity.setUsuario(usuarioEntity);
		entity.setFechaInicio(alquiler.getFechaInicio());
		entity.setFechaFin(alquiler.getFechaFin());
		entity.setCostoTotal(alquiler.getCostoTotal());
		entity.setEstado(alquiler.getEstado());
		entity.setMotivoRechazo(alquiler.getMotivoRechazo());
		entity.setFechaFinSolicitada(alquiler.getFechaFinSolicitada());
		entity.setCostoAdicional(alquiler.getCostoAdicional());
		return entity;
	}

	public Alquiler toAlquiler() {
		return new Alquiler(alquilerId, cliente.getClienteId(), maquinaria.getMaquinariaId(),
				usuario != null ? usuario.getId().longValue() : null, fechaInicio, fechaFin, costoTotal, estado,
				motivoRechazo, fechaFinSolicitada, costoAdicional);
	}
}
