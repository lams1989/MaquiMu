package com.maquimu.backend.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "maquinaria")
public class MaquinariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maquinaria_id")
    private Long id;

    @Column(name = "nombre_equipo", nullable = false, length = 150)
    private String nombre;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "serial", nullable = false, length = 100, unique = true)
    private String serial;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "tarifa_por_dia", nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaDia;

    @Column(name = "tarifa_por_hora", nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaHora;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    public MaquinariaEntity() {
    }

    public MaquinariaEntity(Long id, String nombre, String marca, String serial, String modelo, BigDecimal tarifaDia,
            BigDecimal tarifaHora, String estado, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.serial = serial;
        this.modelo = modelo;
        this.tarifaDia = tarifaDia;
        this.tarifaHora = tarifaHora;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(BigDecimal tarifaDia) {
        this.tarifaDia = tarifaDia;
    }

    public BigDecimal getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(BigDecimal tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
