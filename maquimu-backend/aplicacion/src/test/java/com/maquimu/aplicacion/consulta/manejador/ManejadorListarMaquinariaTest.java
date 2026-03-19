package com.maquimu.aplicacion.consulta.manejador;

import com.maquimu.aplicacion.consulta.ConsultaListarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorListarMaquinariaTest {

    @Mock
    private MaquinariaDao maquinariaDao;

    @InjectMocks
    private ManejadorListarMaquinaria manejadorListarMaquinaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_deberiaRetornarTodasLasMaquinarias() {
        Maquinaria maquinaria1 = new Maquinaria(
                1L, "Excavadora", "Caterpillar", "320D", "EXC-001",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        Maquinaria maquinaria2 = new Maquinaria(
                2L, "Retroexcavadora", "JCB", "3CX", "RET-002",
                BigDecimal.valueOf(800), BigDecimal.valueOf(120), "Retroexcavadora"
        );
        List<Maquinaria> listaEsperada = Arrays.asList(maquinaria1, maquinaria2);

        when(maquinariaDao.listarTodas()).thenReturn(listaEsperada);

        List<Maquinaria> resultado = manejadorListarMaquinaria.ejecutar(new ConsultaListarMaquinaria());

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(listaEsperada, resultado);
        verify(maquinariaDao, times(1)).listarTodas();
    }

    @Test
    void ejecutar_sinMaquinarias_deberiaRetornarListaVacia() {
        when(maquinariaDao.listarTodas()).thenReturn(Arrays.asList());

        List<Maquinaria> resultado = manejadorListarMaquinaria.ejecutar(new ConsultaListarMaquinaria());

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(maquinariaDao, times(1)).listarTodas();
    }
}
