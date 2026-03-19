package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoEliminarCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorEliminarClienteTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;
    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ManejadorEliminarCliente manejadorEliminarCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_clienteNoExistente_deberiaLanzarExcepcion() {
        ComandoEliminarCliente comando = new ComandoEliminarCliente(99L);

        when(clienteDao.buscarPorId(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorEliminarCliente.ejecutar(comando);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteDao, times(1)).buscarPorId(99L);
        verify(clienteRepositorio, never()).eliminar(anyLong());
    }

    @Test
    void ejecutar_clienteExistente_deberiaEliminarCorrectamente() {
        ComandoEliminarCliente comando = new ComandoEliminarCliente(1L);
        Cliente clienteExistente = mock(Cliente.class);

        when(clienteDao.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));
        doNothing().when(clienteRepositorio).eliminar(1L);

        manejadorEliminarCliente.ejecutar(comando);

        verify(clienteDao, times(1)).buscarPorId(1L);
        verify(clienteRepositorio, times(1)).eliminar(1L);
    }
}
