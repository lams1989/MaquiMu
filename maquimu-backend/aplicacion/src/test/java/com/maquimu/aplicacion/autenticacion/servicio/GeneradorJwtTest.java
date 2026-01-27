package com.maquimu.aplicacion.autenticacion.servicio;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorJwtTest {

    private GeneradorJwt generadorJwt;

    private final String jwtSecret = "mySecretKeyForTestingPurposesMustBeAtLeast256BitsLongForHS512Algorithm";
    private final int jwtExpirationMs = 86400000; // 24 horas

    @BeforeEach
    void setUp() {
        generadorJwt = new GeneradorJwt();
        ReflectionTestUtils.setField(generadorJwt, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(generadorJwt, "jwtExpirationMs", jwtExpirationMs);
    }

    @Test
    void generarToken_usuarioValido_deberiaRetornarTokenValido() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        // Act
        String token = generadorJwt.generarToken(usuario);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3); // JWT tiene 3 partes separadas por puntos
    }

    @Test
    void esTokenValido_tokenValido_deberiaRetornarTrue() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        String token = generadorJwt.generarToken(usuario);

        // Act
        boolean esValido = generadorJwt.esTokenValido(token);

        // Assert
        assertTrue(esValido);
    }

    @Test
    void esTokenValido_tokenExpirado_deberiaRetornarFalse() {
        // Arrange - crear generador con expiración de 1 milisegundo
        GeneradorJwt generadorConExpiracionCorta = new GeneradorJwt();
        ReflectionTestUtils.setField(generadorConExpiracionCorta, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(generadorConExpiracionCorta, "jwtExpirationMs", 1);

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        String token = generadorConExpiracionCorta.generarToken(usuario);

        // Esperar a que el token expire
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Act & Assert
        assertThrows(ExpiredJwtException.class, () -> {
            generadorConExpiracionCorta.esTokenValido(token);
        });
    }

    @Test
    void extraerEmail_tokenValido_deberiaRetornarEmail() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        String token = generadorJwt.generarToken(usuario);

        // Act
        String email = generadorJwt.extraerEmail(token);

        // Assert
        assertNotNull(email);
        assertEquals("juan@example.com", email);
    }

    @Test
    void extraerUsuarioId_tokenValido_deberiaRetornarId() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(42L)
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.CLIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();

        String token = generadorJwt.generarToken(usuario);

        // Act
        Long usuarioId = generadorJwt.extraerUsuarioId(token);

        // Assert
        assertNotNull(usuarioId);
        assertEquals(42L, usuarioId);
    }

    @Test
    void extraerRol_tokenValido_deberiaRetornarRol() {
        // Arrange
        Usuario usuarioOperario = Usuario.builder()
                .id(1L)
                .nombreCompleto("Juan Perez")
                .email("juan@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.OPERARIO)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Usuario usuarioCliente = Usuario.builder()
                .id(2L)
                .nombreCompleto("Maria Lopez")
                .email("maria@example.com")
                .passwordHash("hashedPassword")
                .rol(RolUsuario.CLIENTE)
                .fechaCreacion(LocalDateTime.now())
                .build();

        String tokenOperario = generadorJwt.generarToken(usuarioOperario);
        String tokenCliente = generadorJwt.generarToken(usuarioCliente);

        // Act
        RolUsuario rolOperario = generadorJwt.extraerRol(tokenOperario);
        RolUsuario rolCliente = generadorJwt.extraerRol(tokenCliente);

        // Assert
        assertNotNull(rolOperario);
        assertNotNull(rolCliente);
        assertEquals(RolUsuario.OPERARIO, rolOperario);
        assertEquals(RolUsuario.CLIENTE, rolCliente);
    }
}
