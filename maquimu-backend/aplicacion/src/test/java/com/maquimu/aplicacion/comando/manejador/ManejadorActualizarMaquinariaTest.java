package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoActualizarMaquinaria;
import com.maquimu.aplicacion.comando.fabrica.FabricaMaquinaria;
import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorActualizarMaquinariaTest {

    @Mock
    private MaquinariaRepositorio maquinariaRepositorio;
    @Mock
    private MaquinariaDao maquinariaDao;
    @Mock
    private FabricaMaquinaria fabricaMaquinaria;

    @InjectMocks
    private ManejadorActualizarMaquinaria manejadorActualizarMaquinaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_maquinariaNoExistente_deberiaLanzarExcepcion() {
        ComandoActualizarMaquinaria comando = new ComandoActualizarMaquinaria(
                1L, "Excavadora Mod", null, null, null, null, null, null, null
        );

        when(maquinariaDao.buscarPorId(comando.getMaquinariaId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorActualizarMaquinaria.ejecutar(comando);
        });

        assertEquals(String.format("La maquinaria con ID %d no existe.", comando.getMaquinariaId()), exception.getMessage());
        verify(maquinariaDao, times(1)).buscarPorId(comando.getMaquinariaId());
        verify(fabricaMaquinaria, never()).actualizar(any(Maquinaria.class), any(ComandoActualizarMaquinaria.class));
        verify(maquinariaRepositorio, never()).guardar(any(Maquinaria.class));
    }

    @Test
    void ejecutar_serialCambiaAExistenteDeOtraMaquinaria_deberiaLanzarExcepcion() {
        Long maquinariaId = 1L;
        String serialExistente = "EXC-002";
        Maquinaria maquinariaExistente = new Maquinaria(
                maquinariaId, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );

        ComandoActualizarMaquinaria comando = new ComandoActualizarMaquinaria(
                maquinariaId, null, null, null, serialExistente, null, null, null, null
        );

        when(maquinariaDao.buscarPorId(maquinariaId)).thenReturn(Optional.of(maquinariaExistente));
        when(maquinariaDao.existePorSerial(serialExistente)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorActualizarMaquinaria.ejecutar(comando);
        });

        assertEquals(String.format("La maquinaria con serial %s ya existe para otra máquina.", serialExistente), exception.getMessage());
        verify(maquinariaDao, times(1)).buscarPorId(maquinariaId);
        verify(maquinariaDao, times(1)).existePorSerial(serialExistente);
        verify(fabricaMaquinaria, never()).actualizar(any(Maquinaria.class), any(ComandoActualizarMaquinaria.class));
        verify(maquinariaRepositorio, never()).guardar(any(Maquinaria.class));
    }

    @Test
    void ejecutar_actualizacionExitosa_deberiaGuardarMaquinaria() {
        Long maquinariaId = 1L;
        Maquinaria maquinariaExistente = new Maquinaria(
                maquinariaId, "Excavadora", "Caterpillar", "320D", "EXC-001", EstadoMaquinaria.DISPONIBLE,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(150), "Excavadora de orugas"
        );
        ComandoActualizarMaquinaria comando = new ComandoActualizarMaquinaria(
                maquinariaId, "Excavadora Actualizada", null, null, "EXC-001", "ALQUILADO",
                BigDecimal.valueOf(1100), null, "Descripcion Actualizada"
        );

        when(maquinariaDao.buscarPorId(maquinariaId)).thenReturn(Optional.of(maquinariaExistente));
        when(maquinariaRepositorio.guardar(maquinariaExistente)).thenReturn(maquinariaExistente);
        // Mock the update call on fabricaMaquinaria to return the modified object if needed for assertions
        doAnswer(invocation -> {
            Maquinaria m = invocation.getArgument(0);
            ComandoActualizarMaquinaria c = invocation.getArgument(1);
            m.setNombreEquipo(c.getNombreEquipo());
            m.setEstado(EstadoMaquinaria.valueOf(c.getEstado()));
            m.setTarifaPorDia(c.getTarifaPorDia());
            m.setDescripcion(c.getDescripcion());
            return null;
        }).when(fabricaMaquinaria).actualizar(any(Maquinaria.class), any(ComandoActualizarMaquinaria.class));


        Maquinaria resultado = manejadorActualizarMaquinaria.ejecutar(comando);

        assertNotNull(resultado);
        assertEquals("Excavadora Actualizada", resultado.getNombreEquipo());
        assertEquals(EstadoMaquinaria.ALQUILADO, resultado.getEstado());
        verify(maquinariaDao, times(1)).buscarPorId(maquinariaId);
        verify(maquinariaDao, never()).existePorSerial(anyString()); // Serial no cambió
        verify(fabricaMaquinaria, times(1)).actualizar(maquinariaExistente, comando);
        verify(maquinariaRepositorio, times(1)).guardar(maquinariaExistente);
    }
}
