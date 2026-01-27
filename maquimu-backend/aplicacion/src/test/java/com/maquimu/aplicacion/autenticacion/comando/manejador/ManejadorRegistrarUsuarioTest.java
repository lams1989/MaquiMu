package com.maquimu.aplicacion.autenticacion.comando.manejador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.fabrica.FabricaUsuario;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioHashing;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManejadorRegistrarUsuarioTest {

    @Mock
    private UsuarioDao usuarioDao;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @Mock
    private FabricaUsuario fabricaUsuario;

    @Mock
    private FabricaCliente fabricaCliente;

    @Mock
    private ServicioHashing servicioHashing;

    @InjectMocks
    private ManejadorRegistrarUsuario manejadorRegistrarUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_emailUnico_deberiaGuardarUsuario() {
        // Arrange
        ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .password("password123")
                .rol(RolUsuario.OPERARIO)
                .build();

        Usuario usuarioCreado = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);
        when(servicioHashing.hashear(comando.getPassword())).thenReturn("hashedPassword");
        when(fabricaUsuario.crear(comando, "hashedPassword")).thenReturn(usuarioCreado);
        when(usuarioRepositorio.guardar(any(Usuario.class))).thenReturn(usuarioCreado);

        // Act
        manejadorRegistrarUsuario.ejecutar(comando);

        // Assert
        verify(usuarioDao).existePorEmail(comando.getEmail());
        verify(servicioHashing).hashear(comando.getPassword());
        verify(fabricaUsuario).crear(comando, "hashedPassword");
        verify(usuarioRepositorio).guardar(any(Usuario.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

    @Test
    void ejecutar_emailDuplicado_deberiaLanzarExcepcion() {
        // Arrange
        ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .password("password123")
                .rol(RolUsuario.OPERARIO)
                .build();

        when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorRegistrarUsuario.ejecutar(comando);
        });

        assertEquals("El email ya está registrado: juan@example.com", exception.getMessage());
        verify(usuarioDao).existePorEmail(comando.getEmail());
        verify(servicioHashing, never()).hashear(anyString());
        verify(usuarioRepositorio, never()).guardar(any(Usuario.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

    @Test
    void ejecutar_rolCliente_deberiaCrearCliente() {
        // Arrange
        ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .password("password123")
                .rol(RolUsuario.CLIENTE)
                .identificacion("123456789")
                .build();

        Usuario usuarioCreado = Usuario.builder()
                .id(1L)
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.CLIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Cliente clienteCreado = Cliente.builder()
                .clienteId(1L)
                .usuarioId(1L)
                .identificacion("123456789")
                .build();

        when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);
        when(servicioHashing.hashear(comando.getPassword())).thenReturn("hashedPassword");
        when(fabricaUsuario.crear(comando, "hashedPassword")).thenReturn(usuarioCreado);
        when(usuarioRepositorio.guardar(any(Usuario.class))).thenReturn(usuarioCreado);
        when(fabricaCliente.crearDesdeUsuario(usuarioCreado, comando.getIdentificacion())).thenReturn(clienteCreado);

        // Act
        manejadorRegistrarUsuario.ejecutar(comando);

        // Assert
        verify(usuarioDao).existePorEmail(comando.getEmail());
        verify(servicioHashing).hashear(comando.getPassword());
        verify(fabricaUsuario).crear(comando, "hashedPassword");
        verify(usuarioRepositorio).guardar(any(Usuario.class));
        verify(fabricaCliente).crearDesdeUsuario(usuarioCreado, "123456789");
        verify(clienteRepositorio).guardar(clienteCreado);
    }

    @Test
    void ejecutar_rolClienteSinIdentificacion_deberiaLanzarExcepcion() {
        // Arrange
        ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .password("password123")
                .rol(RolUsuario.CLIENTE)
                .identificacion(null)
                .build();

        when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorRegistrarUsuario.ejecutar(comando);
        });

        assertEquals("La identificación es requerida para usuarios con rol CLIENTE", exception.getMessage());
        verify(usuarioDao).existePorEmail(comando.getEmail());
        verify(servicioHashing, never()).hashear(anyString());
        verify(usuarioRepositorio, never()).guardar(any(Usuario.class));
        verify(clienteRepositorio, never()).guardar(any(Cliente.class));
    }

    @Test
    void ejecutar_passwordHasheado_noDeberiaGuardarPlainText() {
        // Arrange
        ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
                .nombreCompleto("Pedro Gomez")
                .email("pedro@example.com")
                .password("password123")
                .rol(RolUsuario.OPERARIO)
                .build();

        Usuario usuarioCreado = Usuario.builder()
                .id(1L)
                .nombreCompleto("Pedro Gomez")
                .email("pedro@example.com")
                .passwordHash("$2a$10$hashedPasswordExample")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);
        when(servicioHashing.hashear(comando.getPassword())).thenReturn("$2a$10$hashedPasswordExample");
        when(fabricaUsuario.crear(comando, "$2a$10$hashedPasswordExample")).thenReturn(usuarioCreado);
        when(usuarioRepositorio.guardar(any(Usuario.class))).thenReturn(usuarioCreado);

        // Act
        manejadorRegistrarUsuario.ejecutar(comando);

        // Assert
        verify(servicioHashing).hashear("password123");
        assertNotEquals("password123", usuarioCreado.getPasswordHash());
        assertTrue(usuarioCreado.getPasswordHash().startsWith("$2a$10$"));
    }
}
