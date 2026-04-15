package com.maquimu.aplicacion.autenticacion.comando.manejador;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.fabrica.FabricaUsuario;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.autenticacion.modelo.RolUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.autenticacion.servicio.ServicioHashing;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;

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

	// --- Helper Methods ---

	private ComandoRegistrarUsuario crearComandoOperario() {
		return ComandoRegistrarUsuario.builder().nombre("Juan").apellido("Pérez").email("juan@maquimu.com")
				.password("password123").rol(RolUsuario.OPERARIO).build();
	}

	private ComandoRegistrarUsuario crearComandoCliente() {
		return ComandoRegistrarUsuario.builder().nombre("María").apellido("López").email("maria@correo.com")
				.password("clave456").rol(RolUsuario.CLIENTE).identificacion("1234567890").build();
	}

	private Usuario crearUsuarioGuardado(Long id, String nombre, String email, RolUsuario rol) {
		return Usuario.builder().id(id).nombreCompleto(nombre).email(email).passwordHash("hashedPassword").rol(rol)
				.fechaCreacion(LocalDateTime.now()).build();
	}

	// --- Tests: Validación de Email Único ---

	@Nested
	@DisplayName("Validación de email único")
	class ValidacionEmailUnico {

		@Test
		@DisplayName("Debe lanzar excepción si el email ya está registrado")
		void ejecutar_emailExistente_deberiaLanzarExcepcion() {
			ComandoRegistrarUsuario comando = crearComandoCliente();
			when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(true);

			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
					() -> manejadorRegistrarUsuario.ejecutar(comando));

			assertEquals("El email ya está registrado: maria@correo.com", exception.getMessage());
			verify(usuarioDao).existePorEmail(comando.getEmail());
			verify(servicioHashing, never()).hashear(any());
			verify(fabricaUsuario, never()).crear(any(), any());
			verify(usuarioRepositorio, never()).guardar(any());
		}
	}

	// --- Tests: Registro de CLIENTE (auto-registro fuerza rol CLIENTE) ---

	@Nested
	@DisplayName("Validación de nombre y apellido")
	class ValidacionNombreApellido {

		@Test
		@DisplayName("Debe lanzar excepción si nombre y apellido están vacíos")
		void ejecutar_sinNombreApellido_deberiaLanzarExcepcion() {
			ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder()
					.nombre("").apellido("").email("test@correo.com")
					.password("clave456").rol(RolUsuario.CLIENTE).identificacion("1234567890").build();

			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
					() -> manejadorRegistrarUsuario.ejecutar(comando));

			assertEquals("El nombre y apellido son requeridos", exception.getMessage());
			verify(usuarioRepositorio, never()).guardar(any());
		}
	}

	// --- Tests: Registro de Cliente ---

	@Nested
	@DisplayName("Registro de usuario CLIENTE")
	class RegistroCliente {

		@Test
		@DisplayName("Debe registrar un cliente y crear registro en tabla clientes")
		void ejecutar_clienteValido_deberiaRegistrarYCrearCliente() {
			ComandoRegistrarUsuario comando = crearComandoCliente();
			Usuario usuarioCreado = crearUsuarioGuardado(null, "María López", "maria@correo.com", RolUsuario.CLIENTE);
			Usuario usuarioGuardado = crearUsuarioGuardado(2L, "María López", "maria@correo.com", RolUsuario.CLIENTE);
			Cliente clienteCreado = Cliente.builder().usuarioId(2L).nombreCliente("María").apellido("López")
					.identificacion("1234567890").email("maria@correo.com").fechaRegistro(LocalDateTime.now()).build();

			when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);
			when(servicioHashing.hashear(comando.getPassword())).thenReturn("hashedPassword");
			when(fabricaUsuario.crear(comando, "hashedPassword")).thenReturn(usuarioCreado);
			when(usuarioRepositorio.guardar(usuarioCreado)).thenReturn(usuarioGuardado);
			when(fabricaCliente.crearDesdeUsuario(usuarioGuardado, "1234567890", "María", "López"))
					.thenReturn(clienteCreado);

			assertDoesNotThrow(() -> manejadorRegistrarUsuario.ejecutar(comando));

			verify(fabricaCliente).crearDesdeUsuario(usuarioGuardado, "1234567890", "María", "López");
			verify(clienteRepositorio).guardar(clienteCreado);
		}

		@Test
		@DisplayName("Debe lanzar excepción si el cliente no tiene identificación")
		void ejecutar_clienteSinIdentificacion_deberiaLanzarExcepcion() {
			ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder().nombre("María").apellido("López")
					.email("maria@correo.com").password("clave456").rol(RolUsuario.CLIENTE).identificacion(null)
					.build();

			when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);

			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
					() -> manejadorRegistrarUsuario.ejecutar(comando));

			assertEquals("La identificación es requerida para usuarios con rol CLIENTE", exception.getMessage());
			verify(usuarioRepositorio, never()).guardar(any());
		}

		@Test
		@DisplayName("Debe lanzar excepción si la identificación está en blanco")
		void ejecutar_clienteIdentificacionBlank_deberiaLanzarExcepcion() {
			ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder().nombre("María").apellido("López")
					.email("maria@correo.com").password("clave456").rol(RolUsuario.CLIENTE).identificacion("   ")
					.build();

			when(usuarioDao.existePorEmail(comando.getEmail())).thenReturn(false);

			IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
					() -> manejadorRegistrarUsuario.ejecutar(comando));

			assertEquals("La identificación es requerida para usuarios con rol CLIENTE", exception.getMessage());
		}
	}

	@Test
	@DisplayName("Debe lanzar excepción cuando falta apellido")
	void ejecutar_sinApellido_deberiaLanzarExcepcion() {
		ComandoRegistrarUsuario comando = ComandoRegistrarUsuario.builder().nombre("Juan").apellido(" ")
				.email("juan@maquimu.com").password("password123").rol(RolUsuario.OPERARIO).build();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> manejadorRegistrarUsuario.ejecutar(comando));

		assertEquals("El nombre y apellido son requeridos", exception.getMessage());
		verify(usuarioDao, never()).existePorEmail(any());
	}

	// --- Tests: Flujo de Hashing ---

	@Nested
	@DisplayName("Hashing de password")
	class HashingPassword {

		@Test
		@DisplayName("Debe hashear la contraseña antes de persistir")
		void ejecutar_deberiaHashearPasswordAntesDeGuardar() {
			ComandoRegistrarUsuario comando = crearComandoCliente();
			String passwordHashEsperado = "$2a$10$abcdef123456";
			Usuario usuarioCreado = crearUsuarioGuardado(null, "María López", "maria@correo.com", RolUsuario.CLIENTE);
			Usuario usuarioGuardado = crearUsuarioGuardado(2L, "María López", "maria@correo.com", RolUsuario.CLIENTE);
			Cliente clienteCreado = Cliente.builder().usuarioId(2L).nombreCliente("María").apellido("López")
					.identificacion("1234567890").email("maria@correo.com").fechaRegistro(LocalDateTime.now()).build();

			when(usuarioDao.existePorEmail(any())).thenReturn(false);
			when(servicioHashing.hashear("clave456")).thenReturn(passwordHashEsperado);
			when(fabricaUsuario.crear(eq(comando), eq(passwordHashEsperado))).thenReturn(usuarioCreado);
			when(usuarioRepositorio.guardar(any())).thenReturn(usuarioGuardado);
			when(fabricaCliente.crearDesdeUsuario(usuarioGuardado, "1234567890", "María", "López"))
					.thenReturn(clienteCreado);

			manejadorRegistrarUsuario.ejecutar(comando);

			verify(servicioHashing).hashear("clave456");
			verify(fabricaUsuario).crear(comando, passwordHashEsperado);
		}
	}
}
