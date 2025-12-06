package com.maquimu.dominio.modelo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Builder
public class Maquinaria {

    private Long maquinariaId;
    private String nombreEquipo;
    private String marca;
    private String modelo;
    private String serial;
    private EstadoMaquinaria estado;
    private BigDecimal tarifaPorDia;
    private BigDecimal tarifaPorHora;
    private String descripcion;

    // Constructor para creación, establece el estado inicial
    public Maquinaria(Long maquinariaId, String nombreEquipo, String marca, String modelo, String serial, EstadoMaquinaria estado, BigDecimal tarifaPorDia, BigDecimal tarifaPorHora, String descripcion) {
        this.maquinariaId = maquinariaId;
        this.nombreEquipo = Optional.ofNullable(nombreEquipo).orElseThrow(() -> new IllegalArgumentException("El nombre del equipo no puede ser nulo."));
        this.marca = Optional.ofNullable(marca).orElseThrow(() -> new IllegalArgumentException("La marca no puede ser nula."));
        this.modelo = Optional.ofNullable(modelo).orElseThrow(() -> new IllegalArgumentException("El modelo no puede ser nulo."));
        this.serial = Optional.ofNullable(serial).orElseThrow(() -> new IllegalArgumentException("El serial no puede ser nulo."));
        this.estado = Optional.ofNullable(estado).orElse(EstadoMaquinaria.DISPONIBLE); // Estado inicial por defecto
        this.tarifaPorDia = Optional.ofNullable(tarifaPorDia).orElseThrow(() -> new IllegalArgumentException("La tarifa por día no puede ser nula."));
        this.tarifaPorHora = Optional.ofNullable(tarifaPorHora).orElseThrow(() -> new IllegalArgumentException("La tarifa por hora no puede ser nula."));
        this.descripcion = descripcion;

        validarTarifasPositivas();
    }

    // Constructor para Builder de Lombok
    public Maquinaria(String nombreEquipo, String marca, String modelo, String serial, EstadoMaquinaria estado, BigDecimal tarifaPorDia, BigDecimal tarifaPorHora, String descripcion) {
        this(null, nombreEquipo, marca, modelo, serial, estado, tarifaPorDia, tarifaPorHora, descripcion);
    }

    // Constructor para Builder de Lombok con ID
    public Maquinaria(Long maquinariaId, String nombreEquipo, String marca, String modelo, String serial, BigDecimal tarifaPorDia, BigDecimal tarifaPorHora, String descripcion) {
        this(maquinariaId, nombreEquipo, marca, modelo, serial, EstadoMaquinaria.DISPONIBLE, tarifaPorDia, tarifaPorHora, descripcion);
    }

    private void validarTarifasPositivas() {
        if (this.tarifaPorDia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tarifa por día debe ser un valor positivo.");
        }
        if (this.tarifaPorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tarifa por hora debe ser un valor positivo.");
        }
    }

    public void alquilar() {
        if (this.estado != EstadoMaquinaria.DISPONIBLE) {
            throw new IllegalStateException("La maquinaria no está disponible para ser alquilada.");
        }
        this.estado = EstadoMaquinaria.ALQUILADO;
    }

    public void devolver() {
        if (this.estado != EstadoMaquinaria.ALQUILADO && this.estado != EstadoMaquinaria.EN_MANTENIMIENTO) {
            throw new IllegalStateException("La maquinaria no puede ser devuelta desde su estado actual.");
        }
        this.estado = EstadoMaquinaria.DISPONIBLE;
    }
}
