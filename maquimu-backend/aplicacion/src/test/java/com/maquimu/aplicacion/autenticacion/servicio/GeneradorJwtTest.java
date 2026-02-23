package com.maquimu.aplicacion.autenticacion.servicio;

import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorJwtTest {

    private GeneradorJwt generadorJwt;

    // Clave secreta de 64 bytes para HS512 (solo para tests)
    private static final String TEST_SECRET =
            "claveSuperSecretaParaTestsDeMaquiMuQueDebeTenerAlMenos64BytesDeExtension12345678";
    private static final int TEST_EXPIRATION_MS = 86400000; // 24 horas

    @BeforeEach
    void setUp() throws Exception {
        generadorJwt = new GeneradorJwt();
        setField(generadorJwt, "jwtSecret", TEST_SECRET);
        setField(generadorJwt, "jwtExpirationMs", TEST_EXPIRATION_MS);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    // --- Helpers ---

    private Usuario crearUsuario(Long id, String email, RolUsuario rol) {
        return Usuario.builder()
                .id(id)
                .nombreCompleto("Test User")
                .email(email)
                .passwordHash("hashedPass")
                .rol(rol)
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    // --- Tests: Generación de Token ---

    @Nested
    @DisplayName("Generación de token")
    class GeneracionToken {

        @Test
        @DisplayName("Debe generar un token no nulo y no vacío")
        void generarToken_deberiaRetornarTokenValido() {
            Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);

            String token = generadorJwt.generarToken(usuario);

            assertNotNull(token);
            assertFalse(token.isEmpty());
            // Un JWT válido tiene 3 partes separadas por puntos
            assertEquals(3, token.split("\\.").length);
        }

        @Test
        @DisplayName("Debe generar tokens diferentes para usuarios diferentes")
        void generarToken_usuariosDiferentes_deberiaGenerarTokensDiferentes() {
            Usuario usuario1 = crearUsuario(1L, "user1@maquimu.com", RolUsuario.OPERARIO);
            Usuario usuario2 = crearUsuario(2L, "user2@maquimu.com", RolUsuario.CLIENTE);

            String token1 = generadorJwt.generarToken(usuario1);
            String token2 = generadorJwt.generarToken(usuario2);

            assertNotEquals(token1, token2);
        }
    }

    // --- Tests: Extracción de Claims ---

    @Nested
    @DisplayName("Extracción de claims del token")
    class ExtraccionClaims {

        @Test
        @DisplayName("Debe extraer el email (subject) del token")
        void extraerEmail_deberiaRetornarEmailDelUsuario() {
            Usuario usuario = crearUsuario(1L, "operario@maquimu.com", RolUsuario.OPERARIO);
            String token = generadorJwt.generarToken(usuario);

            String email = generadorJwt.extraerEmail(token);

            assertEquals("operario@maquimu.com", email);
        }

        @Test
        @DisplayName("Debe extraer el userId del token")
        void obtenerUsuarioIdDelToken_deberiaRetornarIdDelUsuario() {
            Usuario usuario = crearUsuario(42L, "user@maquimu.com", RolUsuario.CLIENTE);
            String token = generadorJwt.generarToken(usuario);

            Long userId = generadorJwt.obtenerUsuarioIdDelToken(token);

            assertEquals(42L, userId);
        }
    }

    // --- Tests: Validación de Token ---

    @Nested
    @DisplayName("Validación de token")
    class ValidacionToken {

        @Test
        @DisplayName("Debe validar como verdadero un token recién generado")
        void esTokenValido_tokenReciente_deberiaRetornarTrue() {
            Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);
            String token = generadorJwt.generarToken(usuario);

            boolean esValido = generadorJwt.esTokenValido(token);

            assertTrue(esValido);
        }

        @Test
        @DisplayName("Debe lanzar excepción para un token con firma inválida")
        void esTokenValido_firmaInvalida_deberiaLanzarExcepcion() {
            Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);
            String token = generadorJwt.generarToken(usuario);

            // Alterar el último carácter de la firma
            String tokenAlterado = token.substring(0, token.length() - 2) + "XX";

            assertThrows(SignatureException.class,
                    () -> generadorJwt.esTokenValido(tokenAlterado));
        }

        @Test
        @DisplayName("Debe lanzar excepción para un token malformado")
        void esTokenValido_tokenMalformado_deberiaLanzarExcepcion() {
            assertThrows(MalformedJwtException.class,
                    () -> generadorJwt.esTokenValido("token.invalido.aqui"));
        }

        @Test
        @DisplayName("Debe detectar un token expirado")
        void esTokenValido_tokenExpirado_deberiaLanzarExcepcion() throws Exception {
            // Configurar expiración de 0 ms (expira inmediatamente)
            setField(generadorJwt, "jwtExpirationMs", 0);

            Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);
            String tokenExpirado = generadorJwt.generarToken(usuario);

            assertThrows(ExpiredJwtException.class,
                    () -> generadorJwt.esTokenValido(tokenExpirado));
        }
    }

    // --- Tests: Consistencia ---

    @Nested
    @DisplayName("Consistencia de datos")
    class Consistencia {

        @Test
        @DisplayName("Email extraído debe coincidir con el del usuario original")
        void generarYExtraer_deberiaSerConsistente() {
            Usuario usuario = crearUsuario(99L, "consistency@maquimu.com", RolUsuario.CLIENTE);

            String token = generadorJwt.generarToken(usuario);
            String emailExtraido = generadorJwt.extraerEmail(token);
            Long idExtraido = generadorJwt.obtenerUsuarioIdDelToken(token);

            assertEquals(usuario.getEmail(), emailExtraido);
            assertEquals(usuario.getId(), idExtraido);
        }

        @Test
        @DisplayName("Múltiples extracciones del mismo token deben retornar el mismo valor")
        void extraerMultiplesVeces_deberiaRetornarMismoValor() {
            Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);
            String token = generadorJwt.generarToken(usuario);

            String email1 = generadorJwt.extraerEmail(token);
            String email2 = generadorJwt.extraerEmail(token);
            Long id1 = generadorJwt.obtenerUsuarioIdDelToken(token);
            Long id2 = generadorJwt.obtenerUsuarioIdDelToken(token);

            assertEquals(email1, email2);
            assertEquals(id1, id2);
        }
    }
}
