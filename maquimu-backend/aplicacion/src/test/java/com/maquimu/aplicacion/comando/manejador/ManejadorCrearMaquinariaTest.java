package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoCrearMaquinaria;
import com.maquimu.aplicacion.comando.fabrica.FabricaMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import com.maquimu.dominio.modelo.EstadoMaquinaria; // Added this import
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorCrearMaquinariaTest {

    @Mock
    private MaquinariaRepositorio maquinariaRepositorio;
    @Mock
    private MaquinariaDao maquinariaDao;
    @Mock
    private FabricaMaquinaria fabricaMaquinaria;

    @InjectMocks
    private ManejadorCrearMaquinaria manejadorCrearMaquinaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_serialExistente_deberiaLanzarExcepcion() {
        ComandoCrearMaquinaria comando = new ComandoCrearMaquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        when(maquinariaDao.existePorSerial(comando.getSerial())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorCrearMaquinaria.ejecutar(comando);
        });

        assertEquals(String.format("La maquinaria con serial %s ya existe.", comando.getSerial()), exception.getMessage());
        verify(maquinariaDao, times(1)).existePorSerial(comando.getSerial());
        verify(fabricaMaquinaria, never()).crear(any(ComandoCrearMaquinaria.class));
        verify(maquinariaRepositorio, never()).guardar(any(Maquinaria.class));
    }

    @Test
    void ejecutar_serialNoExistente_deberiaCrearMaquinariaCorrectamente() {
        ComandoCrearMaquinaria comando = new ComandoCrearMaquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        Maquinaria nuevaMaquinaria = new Maquinaria(
                "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        when(maquinariaDao.existePorSerial(comando.getSerial())).thenReturn(false);
        when(fabricaMaquinaria.crear(comando)).thenReturn(nuevaMaquinaria);
        when(maquinariaRepositorio.guardar(nuevaMaquinaria)).thenReturn(nuevaMaquinaria);

        Maquinaria resultado = manejadorCrearMaquinaria.ejecutar(comando);

        assertNotNull(resultado);
        assertEquals(nuevaMaquinaria, resultado);
        verify(maquinariaDao, times(1)).existePorSerial(comando.getSerial());
        verify(fabricaMaquinaria, times(1)).crear(comando);
        verify(maquinariaRepositorio, times(1)).guardar(nuevaMaquinaria);
    }
}
