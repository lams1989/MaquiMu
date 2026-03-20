package com.maquimu.aplicacion.factura.comando.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.maquimu.aplicacion.factura.comando.ComandoGenerarFactura;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import com.maquimu.dominio.factura.puerto.repositorio.FacturaRepositorio;

class ManejadorGenerarFacturaTest {

	@Mock
	private AlquilerDao alquilerDao;
	@Mock
	private ClienteDao clienteDao;
	@Mock
	private FacturaDao facturaDao;
	@Mock
	private FacturaRepositorio facturaRepositorio;

	@InjectMocks
	private ManejadorGenerarFactura manejadorGenerarFactura;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void ejecutar_clienteSinTelefonoODireccion_deberiaLanzarExcepcion() {
		Long alquilerId = 10L;
		Long clienteId = 5L;

		ComandoGenerarFactura comando = ComandoGenerarFactura.builder().alquilerId(alquilerId).build();

		Alquiler alquiler = new Alquiler(alquilerId, clienteId, 2L, 1L, LocalDateTime.now().minusDays(3),
				LocalDateTime.now().minusDays(1), new BigDecimal("450000"), EstadoAlquiler.FINALIZADO);

		Cliente clienteIncompleto = Cliente.builder().clienteId(clienteId).nombreCliente("Cliente Prueba")
				.apellido("SinDatos").identificacion("123456789").email("cliente@test.com").telefono("").direccion(null)
				.fechaRegistro(LocalDateTime.now()).build();

		when(alquilerDao.buscarPorId(alquilerId)).thenReturn(Optional.of(alquiler));
		when(facturaDao.buscarPorAlquilerId(alquilerId)).thenReturn(Optional.empty());
		when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteIncompleto));

		IllegalStateException exception = assertThrows(IllegalStateException.class,
				() -> manejadorGenerarFactura.ejecutar(comando));

		assertEquals("Para generar factura el cliente debe tener teléfono y dirección registrados",
				exception.getMessage());
		verify(facturaRepositorio, never()).guardar(org.mockito.ArgumentMatchers.any(Factura.class));
	}

	@Test
	void ejecutar_clienteConDatosCompletos_deberiaGenerarFactura() {
		Long alquilerId = 20L;
		Long clienteId = 8L;

		ComandoGenerarFactura comando = ComandoGenerarFactura.builder().alquilerId(alquilerId).build();

		Alquiler alquiler = new Alquiler(alquilerId, clienteId, 3L, 1L, LocalDateTime.now().minusDays(5),
				LocalDateTime.now().minusDays(2), new BigDecimal("900000"), EstadoAlquiler.FINALIZADO);

		Cliente clienteCompleto = Cliente.builder().clienteId(clienteId).nombreCliente("Cliente").apellido("Completo")
				.identificacion("987654321").email("completo@test.com").telefono("3001234567")
				.direccion("Calle 10 # 20-30").fechaRegistro(LocalDateTime.now()).build();

		Factura facturaGuardada = new Factura(1L, alquilerId, clienteId, LocalDateTime.now().toLocalDate(),
				new BigDecimal("900000"), com.maquimu.dominio.factura.modelo.EstadoPago.PENDIENTE);

		when(alquilerDao.buscarPorId(alquilerId)).thenReturn(Optional.of(alquiler));
		when(facturaDao.buscarPorAlquilerId(alquilerId)).thenReturn(Optional.empty());
		when(clienteDao.buscarPorId(clienteId)).thenReturn(Optional.of(clienteCompleto));
		when(facturaRepositorio.guardar(org.mockito.ArgumentMatchers.any(Factura.class))).thenReturn(facturaGuardada);

		Factura resultado = manejadorGenerarFactura.ejecutar(comando);

		assertNotNull(resultado);
		assertEquals(alquilerId, resultado.getAlquilerId());
		assertEquals(clienteId, resultado.getClienteId());
		verify(facturaRepositorio).guardar(org.mockito.ArgumentMatchers.any(Factura.class));
	}
}
