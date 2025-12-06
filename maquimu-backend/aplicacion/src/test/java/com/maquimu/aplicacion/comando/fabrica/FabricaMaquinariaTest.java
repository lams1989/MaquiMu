package com.maquimu.aplicacion.comando.fabrica;

import com.maquimu.aplicacion.comando.ComandoActualizarMaquinaria;
import com.maquimu.aplicacion.comando.ComandoCrearMaquinaria;
import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FabricaMaquinariaTest {

    private FabricaMaquinaria fabricaMaquinaria;

    @BeforeEach
    void setUp() {
        fabricaMaquinaria = new FabricaMaquinaria();
    }

    @Test
    void crear_conComandoValido_deberiaCrearMaquinariaConEstadoDisponible() {
        ComandoCrearMaquinaria comando = new ComandoCrearMaquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        Maquinaria maquinaria = fabricaMaquinaria.crear(comando);

        assertNotNull(maquinaria);
        assertEquals(comando.getNombreEquipo(), maquinaria.getNombreEquipo());
        assertEquals(EstadoMaquinaria.DISPONIBLE, maquinaria.getEstado());
        assertEquals(comando.getTarifaPorDia(), maquinaria.getTarifaPorDia());
    }

    @Test
    void actualizar_conComandoValido_deberiaActualizarCamposCorrectamente() {
        Maquinaria maquinariaExistente = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        ComandoActualizarMaquinaria comando = new ComandoActualizarMaquinaria(
                1L, "Excavadora Mod", "Cat", "320DL", "EXC-001-MOD", "ALQUILADO",
                BigDecimal.valueOf(1200), BigDecimal.valueOf(180), "Excavadora de orugas modificada"
        );

        Maquinaria maquinariaActualizada = fabricaMaquinaria.actualizar(maquinariaExistente, comando);

        assertNotNull(maquinariaActualizada);
        assertEquals(comando.getNombreEquipo(), maquinariaActualizada.getNombreEquipo());
        assertEquals(EstadoMaquinaria.ALQUILADO, maquinariaActualizada.getEstado());
        assertEquals(comando.getTarifaPorDia(), maquinariaActualizada.getTarifaPorDia());
        assertEquals(comando.getSerial(), maquinariaActualizada.getSerial());
    }

    @Test
    void actualizar_conComandoParcial_deberiaActualizarSoloCamposProporcionados() {
        Maquinaria maquinariaExistente = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        ComandoActualizarMaquinaria comando = new ComandoActualizarMaquinaria(
                1L, null, null, null, null, "EN_MANTENIMIENTO",
                BigDecimal.valueOf(1100), null, "Mantenimiento requerido"
        );

        Maquinaria maquinariaActualizada = fabricaMaquinaria.actualizar(maquinariaExistente, comando);

        assertNotNull(maquinariaActualizada);
        assertEquals("Excavadora", maquinariaActualizada.getNombreEquipo()); // No debería cambiar
        assertEquals(EstadoMaquinaria.EN_MANTENIMIENTO, maquinariaActualizada.getEstado());
        assertEquals(BigDecimal.valueOf(1100), maquinariaActualizada.getTarifaPorDia());
        assertEquals(BigDecimal.valueOf(150), maquinariaActualizada.getTarifaPorHora()); // No debería cambiar
    }
}
