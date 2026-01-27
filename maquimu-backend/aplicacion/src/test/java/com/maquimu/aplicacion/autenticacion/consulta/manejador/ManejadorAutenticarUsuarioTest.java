package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.RespuestaAutenticacion;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.servicio.ServicioToken;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ManejadorAutenticarUsuarioTest {

    @Mock
    private UsuarioDao usuarioDao;

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ServicioToken servicioToken;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ManejadorAutenticarUsuario manejadorAutenticarUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_credencialesValidas_deberiaRetornarToken() {
        // Arrange
        ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder()
                .email("juan@example.com")
                .password("password123")
                .build();

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        UserDetails userDetails = new User(
                "juan@example.com",
                "hashedPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERARIO"))
        );

        String expectedToken = "jwt.token.example";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(usuarioDao.buscarPorEmail("juan@example.com")).thenReturn(Optional.of(usuario));
        when(servicioToken.generarToken(usuario)).thenReturn(expectedToken);

        // Act
        RespuestaAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

        // Assert
        assertNotNull(respuesta);
        assertEquals(expectedToken, respuesta.getToken());
        assertNotNull(respuesta.getUsuario());
        assertEquals("Juan Perez", respuesta.getUsuario().getNombreCompleto());
        assertEquals("juan@example.com", respuesta.getUsuario().getEmail());
        assertEquals(RolUsuario.OPERARIO, respuesta.getUsuario().getRol());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDao).buscarPorEmail("juan@example.com");
        verify(servicioToken).generarToken(usuario);
        verify(clienteDao, never()).buscarPorUsuarioId(anyLong());
    }

    @Test
    void ejecutar_credencialesInvalidas_deberiaLanzarExcepcion() {
        // Arrange
        ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder()
                .email("juan@example.com")
                .password("wrongPassword")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            manejadorAutenticarUsuario.ejecutar(consulta);
        });

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDao, never()).buscarPorEmail(anyString());
        verify(servicioToken, never()).generarToken(any(Usuario.class));
    }

    @Test
    void ejecutar_emailNoExiste_deberiaLanzarExcepcion() {
        // Arrange
        ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder()
                .email("noexiste@example.com")
                .password("password123")
                .build();

        UserDetails userDetails = new User(
                "noexiste@example.com",
                "hashedPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERARIO"))
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(usuarioDao.buscarPorEmail("noexiste@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manejadorAutenticarUsuario.ejecutar(consulta);
        });

        assertEquals("Usuario no encontrado despues de autenticacion", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDao).buscarPorEmail("noexiste@example.com");
        verify(servicioToken, never()).generarToken(any(Usuario.class));
    }

    @Test
    void ejecutar_rolCliente_deberiaIncluirClienteId() {
        // Arrange
        ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder()
                .email("maria@example.com")
                .password("password123")
                .build();

        Usuario usuario = Usuario.builder()
                .id(2L)
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.CLIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Cliente cliente = Cliente.builder()
                .clienteId(100L)
                .usuarioId(2L)
                .identificacion("123456789")
                .build();

        UserDetails userDetails = new User(
                "maria@example.com",
                "hashedPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );

        String expectedToken = "jwt.token.cliente";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(usuarioDao.buscarPorEmail("maria@example.com")).thenReturn(Optional.of(usuario));
        when(clienteDao.buscarPorUsuarioId(2L)).thenReturn(Optional.of(cliente));
        when(servicioToken.generarToken(usuario)).thenReturn(expectedToken);

        // Act
        RespuestaAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

        // Assert
        assertNotNull(respuesta);
        assertEquals(expectedToken, respuesta.getToken());
        assertNotNull(respuesta.getUsuario());
        assertEquals(100L, respuesta.getUsuario().getClienteId());
        assertEquals(RolUsuario.CLIENTE, respuesta.getUsuario().getRol());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioDao).buscarPorEmail("maria@example.com");
        verify(clienteDao).buscarPorUsuarioId(2L);
        verify(servicioToken).generarToken(usuario);
    }
}
