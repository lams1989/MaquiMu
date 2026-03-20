package com.maquimu.aplicacion.autenticacion.consulta.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import com.maquimu.aplicacion.autenticacion.consulta.ComandoAutenticacion;
import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.servicio.ServicioToken;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;

class ManejadorAutenticarUsuarioTest {

	@Mock
	private UsuarioDao usuarioDao;
	@Mock
	private ClienteDao clienteDao;
	@Mock
	private ServicioToken servicioToken;
	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private ManejadorAutenticarUsuario manejadorAutenticarUsuario;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// --- Helpers ---

	private ConsultaAutenticarUsuario crearConsulta(String email, String password) {
		return ConsultaAutenticarUsuario.builder().email(email).password(password).build();
	}

	private Usuario crearUsuario(Long id, String email, RolUsuario rol) {
		return Usuario.builder().id(id).nombreCompleto("Test User").email(email).passwordHash("hashedPass").rol(rol)
				.fechaCreacion(LocalDateTime.now()).build();
	}

	private Authentication crearAuthenticationExitosa(String email) {
		UserDetails userDetails = new User(email, "hashedPass",
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		Authentication auth = mock(Authentication.class);
		when(auth.getPrincipal()).thenReturn(userDetails);
		return auth;
	}

	// --- Tests: Login Exitoso ---

	@Nested
	@DisplayName("Login exitoso")
	class LoginExitoso {

		@Test
		@DisplayName("Debe autenticar un operario y retornar token")
		void ejecutar_operarioValido_deberiaRetornarToken() {
			ConsultaAutenticarUsuario consulta = crearConsulta("operario@maquimu.com", "pass123");
			Usuario usuario = crearUsuario(1L, "operario@maquimu.com", RolUsuario.OPERARIO);
			Authentication auth = crearAuthenticationExitosa("operario@maquimu.com");

			when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
			when(usuarioDao.buscarPorEmail("operario@maquimu.com")).thenReturn(Optional.of(usuario));
			when(servicioToken.generarToken(usuario)).thenReturn("jwt-token-operario");

			ComandoAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

			assertNotNull(respuesta);
			assertEquals("jwt-token-operario", respuesta.getToken());
			assertEquals(usuario, respuesta.getUsuario());
			assertEquals(RolUsuario.OPERARIO, respuesta.getUsuario().getRol());
			// No debe buscar clienteId para un operario
			verify(clienteDao, never()).buscarPorUsuarioId(any());
		}

		@Test
		@DisplayName("Debe autenticar un cliente y asignar clienteId")
		void ejecutar_clienteValido_deberiaRetornarTokenConClienteId() {
			ConsultaAutenticarUsuario consulta = crearConsulta("cliente@correo.com", "pass456");
			Usuario usuario = crearUsuario(2L, "cliente@correo.com", RolUsuario.CLIENTE);
			Cliente cliente = Cliente.builder().clienteId(10L).usuarioId(2L).nombreCliente("Cliente Test")
					.identificacion("9876543210").email("cliente@correo.com").fechaRegistro(LocalDateTime.now())
					.build();
			Authentication auth = crearAuthenticationExitosa("cliente@correo.com");

			when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
			when(usuarioDao.buscarPorEmail("cliente@correo.com")).thenReturn(Optional.of(usuario));
			when(clienteDao.buscarPorUsuarioId(2L)).thenReturn(Optional.of(cliente));
			when(servicioToken.generarToken(usuario)).thenReturn("jwt-token-cliente");

			ComandoAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

			assertNotNull(respuesta);
			assertEquals("jwt-token-cliente", respuesta.getToken());
			assertEquals(10L, respuesta.getUsuario().getClienteId());
			verify(clienteDao).buscarPorUsuarioId(2L);
		}

		@Test
		@DisplayName("Debe autenticar cliente sin registro en tabla clientes (clienteId queda null)")
		void ejecutar_clienteSinRegistroCliente_deberiaDejarClienteIdNull() {
			ConsultaAutenticarUsuario consulta = crearConsulta("nuevo@correo.com", "pass789");
			Usuario usuario = crearUsuario(3L, "nuevo@correo.com", RolUsuario.CLIENTE);
			Authentication auth = crearAuthenticationExitosa("nuevo@correo.com");

			when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
			when(usuarioDao.buscarPorEmail("nuevo@correo.com")).thenReturn(Optional.of(usuario));
			when(clienteDao.buscarPorUsuarioId(3L)).thenReturn(Optional.empty());
			when(servicioToken.generarToken(usuario)).thenReturn("jwt-token-nuevo");

			ComandoAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

			assertNotNull(respuesta);
			assertNull(respuesta.getUsuario().getClienteId());
		}
	}

	// --- Tests: Login Fallido ---

	@Nested
	@DisplayName("Login fallido")
	class LoginFallido {

		@Test
		@DisplayName("Debe lanzar excepción si las credenciales son inválidas")
		void ejecutar_credencialesInvalidas_deberiaLanzarExcepcion() {
			ConsultaAutenticarUsuario consulta = crearConsulta("wrong@maquimu.com", "wrongpass");

			when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
					.thenThrow(new BadCredentialsException("Credenciales inválidas"));

			assertThrows(BadCredentialsException.class, () -> manejadorAutenticarUsuario.ejecutar(consulta));

			verify(usuarioDao, never()).buscarPorEmail(any());
			verify(servicioToken, never()).generarToken(any());
		}

		@Test
		@DisplayName("Debe lanzar excepción si el usuario no existe en BD después de autenticación")
		void ejecutar_usuarioNoEncontradoPostAuth_deberiaLanzarExcepcion() {
			ConsultaAutenticarUsuario consulta = crearConsulta("ghost@maquimu.com", "pass");
			Authentication auth = crearAuthenticationExitosa("ghost@maquimu.com");

			when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
			when(usuarioDao.buscarPorEmail("ghost@maquimu.com")).thenReturn(Optional.empty());

			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
					() -> manejadorAutenticarUsuario.ejecutar(consulta));

			assertEquals("Usuario no encontrado despues de autenticacion", exception.getMessage());
			verify(servicioToken, never()).generarToken(any());
		}
	}

	// --- Tests: Interacciones ---

	@Nested
	@DisplayName("Verificación de interacciones")
	class Interacciones {

		@Test
		@DisplayName("Debe llamar a AuthenticationManager con las credenciales correctas")
		void ejecutar_deberiaLlamarAuthManagerConCredencialesCorrectas() {
			ConsultaAutenticarUsuario consulta = crearConsulta("test@maquimu.com", "miPassword");
			Usuario usuario = crearUsuario(1L, "test@maquimu.com", RolUsuario.OPERARIO);
			Authentication auth = crearAuthenticationExitosa("test@maquimu.com");

			when(authenticationManager.authenticate(any())).thenReturn(auth);
			when(usuarioDao.buscarPorEmail(any())).thenReturn(Optional.of(usuario));
			when(servicioToken.generarToken(any())).thenReturn("token");

			manejadorAutenticarUsuario.ejecutar(consulta);

			verify(authenticationManager).authenticate(argThat(authToken -> {
				UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) authToken;
				return "test@maquimu.com".equals(upat.getPrincipal()) && "miPassword".equals(upat.getCredentials());
			}));
		}

		@Test
		@DisplayName("Debe generar token con el usuario correcto")
		void ejecutar_deberiaGenerarTokenConUsuarioCorrecto() {
			ConsultaAutenticarUsuario consulta = crearConsulta("user@maquimu.com", "pass");
			Usuario usuario = crearUsuario(5L, "user@maquimu.com", RolUsuario.OPERARIO);
			Authentication auth = crearAuthenticationExitosa("user@maquimu.com");

			when(authenticationManager.authenticate(any())).thenReturn(auth);
			when(usuarioDao.buscarPorEmail("user@maquimu.com")).thenReturn(Optional.of(usuario));
			when(servicioToken.generarToken(usuario)).thenReturn("token-correcto");

			ComandoAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);

			verify(servicioToken).generarToken(usuario);
			assertEquals("token-correcto", respuesta.getToken());
		}
	}
}
