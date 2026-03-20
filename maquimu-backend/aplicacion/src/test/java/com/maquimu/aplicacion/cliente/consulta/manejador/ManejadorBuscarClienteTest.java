package com.maquimu.aplicacion.cliente.consulta.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.maquimu.aplicacion.cliente.consulta.ConsultaBuscarCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;

class ManejadorBuscarClienteTest {

	@Mock
	private ClienteDao clienteDao;

	@InjectMocks
	private ManejadorBuscarCliente manejadorBuscarCliente;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void ejecutar_clienteExistente_deberiaRetornarCliente() {
		Long clienteId = 1L;
		Cliente clienteEsperado = Cliente.builder().clienteId(clienteId).nombreCliente("Juan Pérez")
				.identificacion("1234567890").email("juan@example.com").fechaRegistro(LocalDateTime.now()).build();

		when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteEsperado));

		Cliente resultado = manejadorBuscarCliente.ejecutar(new ConsultaBuscarCliente(clienteId));

		assertNotNull(resultado);
		assertEquals(clienteId, resultado.getClienteId());
		assertEquals("Juan Pérez", resultado.getNombreCliente());
		verify(clienteDao, times(1)).buscarPorId(clienteId);
	}

	@Test
	void ejecutar_clienteNoExistente_deberiaLanzarExcepcion() {
		Long clienteId = 99L;

		when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			manejadorBuscarCliente.ejecutar(new ConsultaBuscarCliente(clienteId));
		});

		assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
		verify(clienteDao, times(1)).buscarPorId(clienteId);
	}
}
