package com.maquimu.infraestructura.configuracion;

import com.maquimu.aplicacion.autenticacion.servicio.GeneradorJwt;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.infraestructura.autenticacion.adaptador.repositorio.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private GeneradorJwt generadorJwt;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_conTokenValido_deberiaAutenticar() throws ServletException, IOException {
        // Arrange
        String token = "valid.jwt.token";
        String email = "juan@example.com";

        UserDetails userDetails = new User(
                email,
                "hashedPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERARIO"))
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(generadorJwt.extraerEmail(token)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(generadorJwt.esTokenValido(token)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(email, SecurityContextHolder.getContext().getAuthentication().getName());

        verify(generadorJwt).extraerEmail(token);
        verify(userDetailsService).loadUserByUsername(email);
        verify(generadorJwt).esTokenValido(token);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_sinToken_deberiaPermitirRutasPublicas() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(generadorJwt, never()).extraerEmail(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_conTokenInvalido_deberiaRechazar() throws ServletException, IOException {
        // Arrange
        String token = "invalid.jwt.token";
        String email = "juan@example.com";

        UserDetails userDetails = new User(
                email,
                "hashedPassword",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERARIO"))
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(generadorJwt.extraerEmail(token)).thenReturn(email);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(generadorJwt.esTokenValido(token)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(generadorJwt).extraerEmail(token);
        verify(userDetailsService).loadUserByUsername(email);
        verify(generadorJwt).esTokenValido(token);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_sinBearerPrefix_deberiaIgnorar() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(generadorJwt, never()).extraerEmail(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
}
