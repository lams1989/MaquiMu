package com.maquimu.aplicacion.alquiler.comando.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;

class ManejadorRechazarAlquilerTest {

	@Mock
	private AlquilerDao alquilerDao;
	@Mock
	private AlquilerRepositorio alquilerRepositorio;

	@InjectMocks
	private ManejadorRechazarAlquiler manejadorRechazarAlquiler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void ejecutar_alquilerPendienteConMotivo_deberiaRechazarCorrectamente() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.PENDIENTE);

		ComandoRechazarAlquiler comando = ComandoRechazarAlquiler.builder().alquilerId(1L)
				.motivoRechazo("Maquinaria no disponible").build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(alquilerRepositorio.guardar(any(Alquiler.class))).thenAnswer(i -> i.getArgument(0));

		Alquiler resultado = manejadorRechazarAlquiler.ejecutar(comando);

		assertEquals(EstadoAlquiler.RECHAZADO, resultado.getEstado());
		assertEquals("Maquinaria no disponible", resultado.getMotivoRechazo());
		verify(alquilerRepositorio, times(1)).guardar(any(Alquiler.class));
	}

	@Test
	void ejecutar_alquilerPendienteSinMotivo_deberiaRechazarCorrectamente() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.PENDIENTE);

		ComandoRechazarAlquiler comando = ComandoRechazarAlquiler.builder().alquilerId(1L).motivoRechazo(null).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(alquilerRepositorio.guardar(any(Alquiler.class))).thenAnswer(i -> i.getArgument(0));

		Alquiler resultado = manejadorRechazarAlquiler.ejecutar(comando);

		assertEquals(EstadoAlquiler.RECHAZADO, resultado.getEstado());
		assertNull(resultado.getMotivoRechazo());
	}

	@Test
	void ejecutar_alquilerNoExistente_deberiaLanzarExcepcion() {
		ComandoRechazarAlquiler comando = ComandoRechazarAlquiler.builder().alquilerId(999L).build();

		when(alquilerDao.buscarPorId(999L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			manejadorRechazarAlquiler.ejecutar(comando);
		});

		verify(alquilerRepositorio, never()).guardar(any());
	}

	@Test
	void ejecutar_alquilerNoEnPendiente_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.APROBADO);

		ComandoRechazarAlquiler comando = ComandoRechazarAlquiler.builder().alquilerId(1L).motivoRechazo("Motivo")
				.build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));

		assertThrows(IllegalStateException.class, () -> {
			manejadorRechazarAlquiler.ejecutar(comando);
		});

		verify(alquilerRepositorio, never()).guardar(any());
	}
}
