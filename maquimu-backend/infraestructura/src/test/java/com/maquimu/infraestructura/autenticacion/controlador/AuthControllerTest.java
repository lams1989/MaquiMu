package com.maquimu.infraestructura.autenticacion.controlador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.ComandoAutenticacion;
import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorAutenticarUsuario;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Mock
	private ManejadorRegistrarUsuario manejadorRegistrarUsuario;

	@Mock
	private ManejadorAutenticarUsuario manejadorAutenticarUsuario;

	@InjectMocks
	private AuthController authController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(new TestExceptionHandler())
				.build();
		objectMapper = new ObjectMapper();
	}

	@RestControllerAdvice
	static class TestExceptionHandler {
		@ExceptionHandler(IllegalArgumentException.class)
		public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
		}

		@ExceptionHandler(BadCredentialsException.class)
		public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales invalidas"));
		}
	}

	@Test
	void register_registroExitoso_deberia200() throws Exception {
		// Arrange
		ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder().nombreCompleto("Juan Perez")
				.email("juan@example.com").password("password123").rol(RolUsuario.OPERARIO).build();

		doNothing().when(manejadorRegistrarUsuario).ejecutar(any(ComandoRegistrarUsuario.class));

		// Act & Assert
		mockMvc.perform(post("/api/maquimu/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(comando))).andExpect(status().isOk())
				.andExpect(jsonPath("$.mensaje").value(
						"Tu cuenta ha sido creada exitosamente. Un operario la revisará en un plazo de 1 a 3 días hábiles."));

		verify(manejadorRegistrarUsuario, times(1)).ejecutar(any(ComandoRegistrarUsuario.class));
	}

	@Test
	void register_emailDuplicado_deberia400() throws Exception {
		// Arrange
		ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder().nombreCompleto("Juan Perez")
				.email("juan@example.com").password("password123").rol(RolUsuario.OPERARIO).build();

		doThrow(new IllegalArgumentException("El email ya esta registrado: juan@example.com"))
				.when(manejadorRegistrarUsuario).ejecutar(any(ComandoRegistrarUsuario.class));

		// Act & Assert
		mockMvc.perform(post("/api/maquimu/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(comando))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("El email ya esta registrado: juan@example.com"));

		verify(manejadorRegistrarUsuario, times(1)).ejecutar(any(ComandoRegistrarUsuario.class));
	}

	@Test
	void login_credencialesValidas_deberia200ConToken() throws Exception {
		// Arrange
		ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder().email("juan@example.com")
				.password("password123").build();

		Usuario usuario = Usuario.builder().id(1L).nombreCompleto("Juan Perez").email("juan@example.com")
				.rol(RolUsuario.OPERARIO).fechaCreacion(LocalDateTime.now()).build();

		ComandoAutenticacion respuesta = ComandoAutenticacion.builder().token("jwt.token.example").usuario(usuario)
				.build();

		when(manejadorAutenticarUsuario.ejecutar(any(ConsultaAutenticarUsuario.class))).thenReturn(respuesta);

		// Act & Assert
		mockMvc.perform(post("/api/maquimu/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(consulta))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("jwt.token.example"))
				.andExpect(jsonPath("$.usuario.nombreCompleto").value("Juan Perez"))
				.andExpect(jsonPath("$.usuario.email").value("juan@example.com"))
				.andExpect(jsonPath("$.usuario.rol").value("OPERARIO"));

		verify(manejadorAutenticarUsuario, times(1)).ejecutar(any(ConsultaAutenticarUsuario.class));
	}

	@Test
	void login_credencialesInvalidas_deberia401() throws Exception {
		// Arrange
		ConsultaAutenticarUsuario consulta = ConsultaAutenticarUsuario.builder().email("juan@example.com")
				.password("wrongPassword").build();

		when(manejadorAutenticarUsuario.ejecutar(any(ConsultaAutenticarUsuario.class)))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		// Act & Assert
		mockMvc.perform(post("/api/maquimu/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(consulta))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Credenciales invalidas"));

		verify(manejadorAutenticarUsuario, times(1)).ejecutar(any(ConsultaAutenticarUsuario.class));
	}
}
