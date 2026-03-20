package com.maquimu.aplicacion.cliente.consulta.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;

class ManejadorListarClientesTest {

	@Mock
	private ClienteDao clienteDao;

	@InjectMocks
	private ManejadorListarClientes manejadorListarClientes;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void ejecutar_conClientesExistentes_deberiaRetornarLista() {
		List<Cliente> clientesEsperados = Arrays.asList(
				Cliente.builder().clienteId(1L).nombreCliente("Juan Pérez").identificacion("1234567890")
						.email("juan@example.com").fechaRegistro(LocalDateTime.now()).build(),
				Cliente.builder().clienteId(2L).nombreCliente("María López").identificacion("9876543210")
						.email("maria@example.com").fechaRegistro(LocalDateTime.now()).build());

		when(clienteDao.listarTodos()).thenReturn(clientesEsperados);

		List<Cliente> resultado = manejadorListarClientes.ejecutar();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("Juan Pérez", resultado.get(0).getNombreCliente());
		assertEquals("María López", resultado.get(1).getNombreCliente());
		verify(clienteDao, times(1)).listarTodos();
	}

	@Test
	void ejecutar_sinClientes_deberiaRetornarListaVacia() {
		when(clienteDao.listarTodos()).thenReturn(Collections.emptyList());

		List<Cliente> resultado = manejadorListarClientes.ejecutar();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
		verify(clienteDao, times(1)).listarTodos();
	}
}
