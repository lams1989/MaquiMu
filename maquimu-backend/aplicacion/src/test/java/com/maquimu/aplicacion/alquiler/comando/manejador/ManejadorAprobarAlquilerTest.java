package com.maquimu.aplicacion.alquiler.comando.manejador;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;

class ManejadorAprobarAlquilerTest {

	@Mock
	private AlquilerDao alquilerDao;
	@Mock
	private AlquilerRepositorio alquilerRepositorio;

	@InjectMocks
	private ManejadorAprobarAlquiler manejadorAprobarAlquiler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void ejecutar_alquilerPendiente_deberiaAprobarCorrectamente() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.PENDIENTE);

		ComandoAprobarAlquiler comando = ComandoAprobarAlquiler.builder().alquilerId(1L).usuarioId(100L).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(alquilerRepositorio.guardar(any(Alquiler.class))).thenAnswer(i -> i.getArgument(0));

		Alquiler resultado = manejadorAprobarAlquiler.ejecutar(comando);

		assertEquals(EstadoAlquiler.APROBADO, resultado.getEstado());
		assertEquals(100L, resultado.getUsuarioId());
		verify(alquilerDao, times(1)).buscarPorId(1L);
		verify(alquilerRepositorio, times(1)).guardar(any(Alquiler.class));
	}

	@Test
	void ejecutar_alquilerNoExistente_deberiaLanzarExcepcion() {
		ComandoAprobarAlquiler comando = ComandoAprobarAlquiler.builder().alquilerId(999L).usuarioId(100L).build();

		when(alquilerDao.buscarPorId(999L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			manejadorAprobarAlquiler.ejecutar(comando);
		});

		verify(alquilerRepositorio, never()).guardar(any());
	}

	@Test
	void ejecutar_alquilerNoEnPendiente_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.ACTIVO);

		ComandoAprobarAlquiler comando = ComandoAprobarAlquiler.builder().alquilerId(1L).usuarioId(100L).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));

		assertThrows(IllegalStateException.class, () -> {
			manejadorAprobarAlquiler.ejecutar(comando);
		});

		verify(alquilerRepositorio, never()).guardar(any());
	}
}
