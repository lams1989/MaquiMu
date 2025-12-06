package com.maquimu.aplicacion.consulta.manejador;

import com.maquimu.aplicacion.consulta.ConsultaBuscarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorBuscarMaquinariaTest {

    @Mock
    private MaquinariaDao maquinariaDao;

    @InjectMocks
    private ManejadorBuscarMaquinaria manejadorBuscarMaquinaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_maquinariaExistente_deberiaRetornarMaquinaria() {
        Long idMaquinaria = 1L;
        ConsultaBuscarMaquinaria consulta = new ConsultaBuscarMaquinaria(idMaquinaria);
        Maquinaria maquinariaEsperada = new Maquinaria(
                idMaquinaria, "Excavadora", "Caterpillar", "320D", "EXC-001",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        when(maquinariaDao.buscarPorId(idMaquinaria)).thenReturn(Optional.of(maquinariaEsperada));

        Maquinaria resultado = manejadorBuscarMaquinaria.ejecutar(consulta);

        assertNotNull(resultado);
        assertEquals(maquinariaEsperada, resultado);
        verify(maquinariaDao, times(1)).buscarPorId(idMaquinaria);
    }

    @Test
    void ejecutar_maquinariaNoExistente_deberiaLanzarExcepcion() {
        Long idMaquinaria = 99L;
        ConsultaBuscarMaquinaria consulta = new ConsultaBuscarMaquinaria(idMaquinaria);

        when(maquinariaDao.buscarPorId(idMaquinaria)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorBuscarMaquinaria.ejecutar(consulta);
        });

        assertEquals(String.format("La maquinaria con ID %d no existe.", idMaquinaria), exception.getMessage());
        verify(maquinariaDao, times(1)).buscarPorId(idMaquinaria);
    }
}
