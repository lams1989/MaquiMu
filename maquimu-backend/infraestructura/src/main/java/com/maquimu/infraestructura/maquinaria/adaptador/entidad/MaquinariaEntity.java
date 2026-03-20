package com.maquimu.infraestructura.maquinaria.adaptador.entidad;

import java.math.BigDecimal;

import com.maquimu.dominio.maquinaria.modelo.EstadoMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "maquinaria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaquinariaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maquinaria_id")
	private Long maquinariaId;

	@Column(name = "nombre_equipo", nullable = false)
	private String nombreEquipo;

	@Column(nullable = false)
	private String marca;

	@Column(nullable = false)
	private String modelo;

	@Column(nullable = false, unique = true)
	private String serial;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_maquinaria", nullable = false)
	private EstadoMaquinaria estado;

	@Column(name = "tarifa_por_dia", nullable = false)
	private BigDecimal tarifaPorDia;

	@Column(name = "tarifa_por_hora", nullable = false)
	private BigDecimal tarifaPorHora;

	@Column(columnDefinition = "TEXT")
	private String descripcion;

	public static MaquinariaEntity fromMaquinaria(Maquinaria maquinaria) {
		return new MaquinariaEntity(maquinaria.getMaquinariaId(), maquinaria.getNombreEquipo(), maquinaria.getMarca(),
				maquinaria.getModelo(), maquinaria.getSerial(), maquinaria.getEstado(), maquinaria.getTarifaPorDia(),
				maquinaria.getTarifaPorHora(), maquinaria.getDescripcion());
	}

	public Maquinaria toMaquinaria() {
		return new Maquinaria(maquinariaId, nombreEquipo, marca, modelo, serial, estado, tarifaPorDia, tarifaPorHora,
				descripcion);
	}
}
