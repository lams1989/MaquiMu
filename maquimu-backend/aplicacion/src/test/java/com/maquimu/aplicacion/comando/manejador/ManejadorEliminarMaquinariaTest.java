package com.maquimu.aplicacion.comando.manejador;

import com.maquimu.aplicacion.comando.ComandoEliminarMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.puerto.repositorio.MaquinariaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorEliminarMaquinariaTest {

    @Mock
    private MaquinariaRepositorio maquinariaRepositorio;
    @Mock
    private MaquinariaDao maquinariaDao;

    @InjectMocks
    private ManejadorEliminarMaquinaria manejadorEliminarMaquinaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_maquinariaNoExistente_deberiaLanzarExcepcion() {
        ComandoEliminarMaquinaria comando = new ComandoEliminarMaquinaria(1L);

        when(maquinariaDao.buscarPorId(comando.getMaquinariaId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorEliminarMaquinaria.ejecutar(comando);
        });

        assertEquals(String.format("La maquinaria con ID %d no existe.", comando.getMaquinariaId()), exception.getMessage());
        verify(maquinariaDao, times(1)).buscarPorId(comando.getMaquinariaId());
        verify(maquinariaRepositorio, never()).eliminar(anyLong());
    }

    @Test
    void ejecutar_maquinariaExistente_deberiaEliminarCorrectamente() {
        ComandoEliminarMaquinaria comando = new ComandoEliminarMaquinaria(1L);
        Maquinaria maquinariaExistente = mock(Maquinaria.class); // Mock de Maquinaria

        when(maquinariaDao.buscarPorId(comando.getMaquinariaId())).thenReturn(Optional.of(maquinariaExistente));
        doNothing().when(maquinariaRepositorio).eliminar(comando.getMaquinariaId());

        manejadorEliminarMaquinaria.ejecutar(comando);

        verify(maquinariaDao, times(1)).buscarPorId(comando.getMaquinariaId());
        verify(maquinariaRepositorio, times(1)).eliminar(comando.getMaquinariaId());
    }
}
