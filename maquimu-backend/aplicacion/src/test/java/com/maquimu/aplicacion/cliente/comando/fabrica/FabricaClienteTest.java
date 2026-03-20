package com.maquimu.aplicacion.cliente.comando.fabrica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;

class FabricaClienteTest {

	private FabricaCliente fabricaCliente;

	@BeforeEach
	void setUp() {
		fabricaCliente = new FabricaCliente();
	}

	@Test
	void crear_conComandoValido_deberiaCrearClienteConFechaRegistroAutomatica() {
		ComandoCrearCliente comando = ComandoCrearCliente.builder().usuarioId(5L).nombreCliente("Juan Pérez")
				.identificacion("1234567890").telefono("3001234567").email("juan@example.com").direccion("Calle 123")
				.build();

		Cliente cliente = fabricaCliente.crear(comando);

		assertNotNull(cliente);
		assertNull(cliente.getClienteId());
		assertEquals(5L, cliente.getUsuarioId());
		assertEquals("Juan Pérez", cliente.getNombreCliente());
		assertEquals("1234567890", cliente.getIdentificacion());
		assertEquals("3001234567", cliente.getTelefono());
		assertEquals("juan@example.com", cliente.getEmail());
		assertEquals("Calle 123", cliente.getDireccion());
		assertNotNull(cliente.getFechaRegistro());
	}

	@Test
	void crear_sinUsuarioId_deberiaCrearClienteSinAsociacion() {
		ComandoCrearCliente comando = ComandoCrearCliente.builder().usuarioId(null).nombreCliente("María López")
				.identificacion("9876543210").email("maria@example.com").build();

		Cliente cliente = fabricaCliente.crear(comando);

		assertNotNull(cliente);
		assertNull(cliente.getUsuarioId());
		assertEquals("María López", cliente.getNombreCliente());
	}

	@Test
	void actualizar_conComandoValido_deberiaActualizarCamposPreservandoIdYFecha() {
		LocalDateTime fechaOriginal = LocalDateTime.of(2025, 1, 15, 10, 0);
		Cliente clienteExistente = Cliente.builder().clienteId(1L).usuarioId(5L).nombreCliente("Juan Pérez")
				.identificacion("1234567890").telefono("3001234567").email("juan@example.com").direccion("Calle 123")
				.fechaRegistro(fechaOriginal).build();

		ComandoActualizarCliente comando = ComandoActualizarCliente.builder().clienteId(1L)
				.nombreCliente("Juan P. Modificado").identificacion("1234567890").telefono("3009999999")
				.email("juan.nuevo@example.com").direccion("Nueva dirección").build();

		Cliente clienteActualizado = fabricaCliente.actualizar(clienteExistente, comando);

		assertNotNull(clienteActualizado);
		assertEquals(1L, clienteActualizado.getClienteId());
		assertEquals(5L, clienteActualizado.getUsuarioId());
		assertEquals("Juan P. Modificado", clienteActualizado.getNombreCliente());
		assertEquals("1234567890", clienteActualizado.getIdentificacion());
		assertEquals("3009999999", clienteActualizado.getTelefono());
		assertEquals("juan.nuevo@example.com", clienteActualizado.getEmail());
		assertEquals("Nueva dirección", clienteActualizado.getDireccion());
		assertEquals(fechaOriginal, clienteActualizado.getFechaRegistro());
	}

	@Test
	void actualizar_deberiaPreservarUsuarioIdDelClienteExistente() {
		Cliente clienteExistente = Cliente.builder().clienteId(1L).usuarioId(10L).nombreCliente("Original")
				.identificacion("1111111111").email("original@example.com").fechaRegistro(LocalDateTime.now()).build();

		ComandoActualizarCliente comando = ComandoActualizarCliente.builder().clienteId(1L).nombreCliente("Modificado")
				.identificacion("1111111111").email("modificado@example.com").build();

		Cliente clienteActualizado = fabricaCliente.actualizar(clienteExistente, comando);

		assertEquals(10L, clienteActualizado.getUsuarioId());
	}
}
