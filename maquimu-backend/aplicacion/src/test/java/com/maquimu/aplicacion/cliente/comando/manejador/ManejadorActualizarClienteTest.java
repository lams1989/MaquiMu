package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManejadorActualizarClienteTest {

    @Mock
    private FabricaCliente fabricaCliente;
    @Mock
    private ClienteRepositorio clienteRepositorio;
    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ManejadorActualizarCliente manejadorActualizarCliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_clienteNoExistente_deberiaLanzarExcepcion() {
        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(99L)
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .build();

        when(clienteDao.buscarPorId(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorActualizarCliente.ejecutar(comando);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteDao, times(1)).buscarPorId(99L);
        verify(fabricaCliente, never()).actualizar(any(Cliente.class), any(ComandoActualizarCliente.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

    @Test
    void ejecutar_identificacionCambiadaAExistenteDeOtroCliente_deberiaLanzarExcepcion() {
        Long clienteId = 1L;
        String identificacionExistente = "9876543210";

        Cliente clienteExistente = Cliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .fechaRegistro(LocalDateTime.now())
                .build();

        Cliente otroCliente = Cliente.builder()
                .clienteId(2L)
                .nombreCliente("María López")
                .identificacion(identificacionExistente)
                .email("maria@example.com")
                .fechaRegistro(LocalDateTime.now())
                .build();

        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion(identificacionExistente)
                .email("juan@example.com")
                .build();

        when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteDao.buscarPorIdentificacion(identificacionExistente)).thenReturn(Optional.of(otroCliente));
        when(clienteDao.buscarPorEmail("juan@example.com")).thenReturn(Optional.of(clienteExistente));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorActualizarCliente.ejecutar(comando);
        });

        assertEquals("Ya existe otro cliente con la identificación: " + identificacionExistente, exception.getMessage());
        verify(clienteDao, times(1)).buscarPorId(clienteId);
        verify(clienteDao, times(1)).buscarPorIdentificacion(identificacionExistente);
                verify(clienteDao, never()).buscarPorEmail(anyString());
        verify(fabricaCliente, never()).actualizar(any(Cliente.class), any(ComandoActualizarCliente.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

        @Test
        void ejecutar_emailCambiadoAExistenteDeOtroCliente_deberiaLanzarExcepcion() {
                Long clienteId = 1L;
                String emailExistente = "maria@example.com";

                Cliente clienteExistente = Cliente.builder()
                                .clienteId(clienteId)
                                .nombreCliente("Juan Pérez")
                                .identificacion("1234567890")
                                .email("juan@example.com")
                                .fechaRegistro(LocalDateTime.now())
                                .build();

                Cliente otroCliente = Cliente.builder()
                                .clienteId(2L)
                                .nombreCliente("María López")
                                .identificacion("9876543210")
                                .email(emailExistente)
                                .fechaRegistro(LocalDateTime.now())
                                .build();

                ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                                .clienteId(clienteId)
                                .nombreCliente("Juan Pérez")
                                .identificacion("1234567890")
                                .email(emailExistente)
                                .build();

                when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
                when(clienteDao.buscarPorIdentificacion("1234567890")).thenReturn(Optional.of(clienteExistente));
                when(clienteDao.buscarPorEmail(emailExistente)).thenReturn(Optional.of(otroCliente));

                Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        manejadorActualizarCliente.ejecutar(comando);
                });

                assertEquals("Ya existe otro cliente con el email: " + emailExistente, exception.getMessage());
                verify(clienteDao, times(1)).buscarPorId(clienteId);
                verify(clienteDao, times(1)).buscarPorIdentificacion("1234567890");
                verify(clienteDao, times(1)).buscarPorEmail(emailExistente);
                verify(fabricaCliente, never()).actualizar(any(Cliente.class), any(ComandoActualizarCliente.class));
                verify(clienteRepositorio, never()).guardar(any(Cliente.class));
        }

    @Test
    void ejecutar_mismIdentificacion_deberiaActualizarCorrectamente() {
        Long clienteId = 1L;
        String mismaIdentificacion = "1234567890";
        LocalDateTime fechaOriginal = LocalDateTime.of(2025, 6, 1, 10, 0);

        Cliente clienteExistente = Cliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion(mismaIdentificacion)
                .email("juan@example.com")
                .fechaRegistro(fechaOriginal)
                .build();

        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan P. Modificado")
                .identificacion(mismaIdentificacion)
                .email("juan.nuevo@example.com")
                .telefono("3009999999")
                .direccion("Nueva dirección")
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan P. Modificado")
                .identificacion(mismaIdentificacion)
                .email("juan.nuevo@example.com")
                .telefono("3009999999")
                .direccion("Nueva dirección")
                .fechaRegistro(fechaOriginal)
                .build();

        when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteDao.buscarPorIdentificacion(mismaIdentificacion)).thenReturn(Optional.of(clienteExistente));
        when(clienteDao.buscarPorEmail("juan.nuevo@example.com")).thenReturn(Optional.empty());
        when(fabricaCliente.actualizar(clienteExistente, comando)).thenReturn(clienteActualizado);
        when(clienteRepositorio.guardar(clienteActualizado)).thenReturn(clienteActualizado);

        manejadorActualizarCliente.ejecutar(comando);

        verify(clienteDao, times(1)).buscarPorId(clienteId);
                verify(clienteDao, times(1)).buscarPorIdentificacion(mismaIdentificacion);
                verify(clienteDao, times(1)).buscarPorEmail("juan.nuevo@example.com");
        verify(fabricaCliente, times(1)).actualizar(clienteExistente, comando);
        verify(clienteRepositorio, times(1)).guardar(clienteActualizado);
    }

    @Test
    void ejecutar_identificacionNuevaNoExistente_deberiaActualizarCorrectamente() {
        Long clienteId = 1L;
        String nuevaIdentificacion = "5555555555";
        LocalDateTime fechaOriginal = LocalDateTime.of(2025, 6, 1, 10, 0);

        Cliente clienteExistente = Cliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion("1234567890")
                .email("juan@example.com")
                .fechaRegistro(fechaOriginal)
                .build();

        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion(nuevaIdentificacion)
                .email("juan@example.com")
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Pérez")
                .identificacion(nuevaIdentificacion)
                .email("juan@example.com")
                .fechaRegistro(fechaOriginal)
                .build();

        when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteDao.buscarPorIdentificacion(nuevaIdentificacion)).thenReturn(Optional.empty());
        when(clienteDao.buscarPorEmail("juan@example.com")).thenReturn(Optional.of(clienteExistente));
        when(fabricaCliente.actualizar(clienteExistente, comando)).thenReturn(clienteActualizado);
        when(clienteRepositorio.guardar(clienteActualizado)).thenReturn(clienteActualizado);

        manejadorActualizarCliente.ejecutar(comando);

        verify(clienteDao, times(1)).buscarPorId(clienteId);
        verify(clienteDao, times(1)).buscarPorIdentificacion(nuevaIdentificacion);
                verify(clienteDao, times(1)).buscarPorEmail("juan@example.com");
        verify(fabricaCliente, times(1)).actualizar(clienteExistente, comando);
        verify(clienteRepositorio, times(1)).guardar(clienteActualizado);
    }
}
