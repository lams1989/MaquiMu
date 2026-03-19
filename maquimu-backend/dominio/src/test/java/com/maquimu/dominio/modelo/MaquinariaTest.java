package com.maquimu.dominio.modelo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MaquinariaTest {

    @Test
    void crearMaquinaria_conValoresValidos_deberiaCrearseCorrectamente() {
        Maquinaria maquinaria = new Maquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        assertNotNull(maquinaria);
        assertEquals("Excavadora", maquinaria.getNombreEquipo());
        assertEquals(EstadoMaquinaria.DISPONIBLE, maquinaria.getEstado());
        assertEquals(BigDecimal.valueOf(1000), maquinaria.getTarifaPorDia());
    }

    @Test
    void crearMaquinaria_conTarifaPorDiaCero_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Maquinaria("Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                    BigDecimal.ZERO, BigDecimal.valueOf(150), "Excavadora de orugas");
        });
        assertEquals("La tarifa por día debe ser un valor positivo.", exception.getMessage());
    }

    @Test
    void crearMaquinaria_conTarifaPorHoraNegativa_deberiaLanzarExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Maquinaria("Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                    BigDecimal.valueOf(1000), BigDecimal.valueOf(-10), "Excavadora de orugas");
        });
        assertEquals("La tarifa por hora debe ser un valor positivo.", exception.getMessage());
    }

    @Test
    void alquilarMaquinaria_estadoDisponible_deberiaCambiarAAlquilado() {
        Maquinaria maquinaria = new Maquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        maquinaria.alquilar();
        assertEquals(EstadoMaquinaria.ALQUILADO, maquinaria.getEstado());
    }

    @Test
    void alquilarMaquinaria_estadoNoDisponible_deberiaLanzarExcepcion() {
        Maquinaria maquinaria = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.ALQUILADO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        Exception exception = assertThrows(IllegalStateException.class, maquinaria::alquilar);
        assertEquals("La maquinaria no está disponible para ser alquilada.", exception.getMessage());
    }

    @Test
    void devolverMaquinaria_estadoAlquilado_deberiaCambiarADisponible() {
        Maquinaria maquinaria = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.ALQUILADO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        maquinaria.devolver();
        assertEquals(EstadoMaquinaria.DISPONIBLE, maquinaria.getEstado());
    }

    @Test
    void devolverMaquinaria_estadoEnMantenimiento_deberiaCambiarADisponible() {
        Maquinaria maquinaria = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.EN_MANTENIMIENTO,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        maquinaria.devolver();
        assertEquals(EstadoMaquinaria.DISPONIBLE, maquinaria.getEstado());
    }

    @Test
    void devolverMaquinaria_estadoPendiente_deberiaLanzarExcepcion() {
        Maquinaria maquinaria = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        Exception exception = assertThrows(IllegalStateException.class, maquinaria::devolver);
        assertEquals("La maquinaria no puede ser devuelta desde su estado actual.", exception.getMessage());
    }
}
