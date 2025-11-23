package com.maquimu.backend.domain.model;

import java.math.BigDecimal;

public class Maquinaria {
    private Long id;
    private String nombre;
    private String marca;
    private String modelo;
    private String serial;
    private String estado; // DISPONIBLE, ALQUILADO, MANTENIMIENTO
    private BigDecimal tarifaDia;
    private BigDecimal tarifaHora;
    private String descripcion;

    public Maquinaria() {
    }

    public Maquinaria(Long id, String nombre, String marca, String modelo, String serial, String estado,
            BigDecimal tarifaDia, BigDecimal tarifaHora, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.serial = serial;
        this.estado = estado;
        this.tarifaDia = tarifaDia;
        this.tarifaHora = tarifaHora;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
