package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorCrearClienteTest {

    @Mock
    private FabricaCliente fabricaCliente;
    @Mock
    private ClienteRepositorio clienteRepositorio;
    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ManejadorCrearCliente manejadorCrearCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_identificacionExistente_deberiaLanzarExcepcion() {
        ComandoCrearCliente comando = ComandoCrearCliente.builder()
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .telefono("3001234567")
                .direccion("Calle 123")
                .build();

        when(clienteDao.existePorIdentificacion("1234567890")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorCrearCliente.ejecutar(comando);
        });

        assertEquals("Ya existe un cliente con la identificación: 1234567890", exception.getMessage());
        verify(clienteDao, times(1)).existePorIdentificacion("1234567890");
        verify(fabricaCliente, never()).crear(any(ComandoCrearCliente.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

    @Test
    void ejecutar_identificacionNoExistente_deberiaCrearClienteCorrectamente() {
        ComandoCrearCliente comando = ComandoCrearCliente.builder()
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .telefono("3001234567")
                .direccion("Calle 123")
                .build();

        Cliente nuevoCliente = Cliente.builder()
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .telefono("3001234567")
                .direccion("Calle 123")
                .fechaRegistro(LocalDateTime.now())
                .build();

        Cliente clienteGuardado = Cliente.builder()
                .clienteId(1L)
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .telefono("3001234567")
                .direccion("Calle 123")
                .fechaRegistro(nuevoCliente.getFechaRegistro())
                .build();

        when(clienteDao.existePorIdentificacion("1234567890")).thenReturn(false);
        when(fabricaCliente.crear(comando)).thenReturn(nuevoCliente);
        when(clienteRepositorio.guardar(nuevoCliente)).thenReturn(clienteGuardado);

        Long clienteId = manejadorCrearCliente.ejecutar(comando);

        assertNotNull(clienteId);
        assertEquals(1L, clienteId);
        verify(clienteDao, times(1)).existePorIdentificacion("1234567890");
        verify(fabricaCliente, times(1)).crear(comando);
        verify(clienteRepositorio, times(1)).guardar(nuevoCliente);
    }

    @Test
    void ejecutar_sinTelefono_deberiaCrearClienteCorrectamente() {
        ComandoCrearCliente comando = ComandoCrearCliente.builder()
                .nombreCliente("María López")
                .identificacion("9876543210")
                .email("maria@example.com")
                .telefono(null)
                .direccion(null)
                .build();

        Cliente nuevoCliente = Cliente.builder()
                .nombreCliente("María López")
                .identificacion("9876543210")
                .email("maria@example.com")
                .fechaRegistro(LocalDateTime.now())
                .build();

        Cliente clienteGuardado = Cliente.builder()
                .clienteId(2L)
                .nombreCliente("María López")
                .identificacion("9876543210")
                .email("maria@example.com")
                .fechaRegistro(nuevoCliente.getFechaRegistro())
                .build();

        when(clienteDao.existePorIdentificacion("9876543210")).thenReturn(false);
        when(fabricaCliente.crear(comando)).thenReturn(nuevoCliente);
        when(clienteRepositorio.guardar(nuevoCliente)).thenReturn(clienteGuardado);

        Long clienteId = manejadorCrearCliente.ejecutar(comando);

        assertNotNull(clienteId);
        assertEquals(2L, clienteId);
    }
}
