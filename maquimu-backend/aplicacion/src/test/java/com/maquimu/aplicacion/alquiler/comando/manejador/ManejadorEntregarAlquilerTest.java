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

import com.maquimu.aplicacion.alquiler.comando.ComandoEntregarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.alquiler.puerto.repositorio.AlquilerRepositorio;
import com.maquimu.dominio.maquinaria.modelo.EstadoMaquinaria;
import com.maquimu.dominio.maquinaria.modelo.Maquinaria;
import com.maquimu.dominio.maquinaria.puerto.dao.MaquinariaDao;
import com.maquimu.dominio.maquinaria.puerto.repositorio.MaquinariaRepositorio;

class ManejadorEntregarAlquilerTest {

	@Mock
	private AlquilerDao alquilerDao;
	@Mock
	private AlquilerRepositorio alquilerRepositorio;
	@Mock
	private MaquinariaDao maquinariaDao;
	@Mock
	private MaquinariaRepositorio maquinariaRepositorio;

	@InjectMocks
	private ManejadorEntregarAlquiler manejadorEntregarAlquiler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private Maquinaria crearMaquinariaDisponible() {
		return Maquinaria.builder().maquinariaId(20L).nombreEquipo("Excavadora").marca("CAT").modelo("320D")
				.serial("SN123").estado(EstadoMaquinaria.DISPONIBLE).tarifaPorDia(new BigDecimal("100000"))
				.tarifaPorHora(new BigDecimal("15000")).descripcion("Excavadora pesada").build();
	}

	@Test
	void ejecutar_alquilerAprobadoYMaquinariaDisponible_deberiaEntregarCorrectamente() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.APROBADO);

		Maquinaria maquinaria = crearMaquinariaDisponible();

		ComandoEntregarAlquiler comando = ComandoEntregarAlquiler.builder().alquilerId(1L).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(maquinariaDao.buscarPorId(20L)).thenReturn(Optional.of(maquinaria));
		when(alquilerRepositorio.guardar(any(Alquiler.class))).thenAnswer(i -> i.getArgument(0));
		when(maquinariaRepositorio.guardar(any(Maquinaria.class))).thenAnswer(i -> i.getArgument(0));

		Alquiler resultado = manejadorEntregarAlquiler.ejecutar(comando);

		assertEquals(EstadoAlquiler.ACTIVO, resultado.getEstado());
		assertEquals(EstadoMaquinaria.ALQUILADO, maquinaria.getEstado());
		verify(maquinariaRepositorio, times(1)).guardar(any(Maquinaria.class));
		verify(alquilerRepositorio, times(1)).guardar(any(Alquiler.class));
	}

	@Test
	void ejecutar_alquilerNoExistente_deberiaLanzarExcepcion() {
		ComandoEntregarAlquiler comando = ComandoEntregarAlquiler.builder().alquilerId(999L).build();

		when(alquilerDao.buscarPorId(999L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			manejadorEntregarAlquiler.ejecutar(comando);
		});

		verify(maquinariaRepositorio, never()).guardar(any());
		verify(alquilerRepositorio, never()).guardar(any());
	}

	@Test
	void ejecutar_alquilerNoEnAprobado_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.PENDIENTE);

		Maquinaria maquinaria = crearMaquinariaDisponible();

		ComandoEntregarAlquiler comando = ComandoEntregarAlquiler.builder().alquilerId(1L).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(maquinariaDao.buscarPorId(20L)).thenReturn(Optional.of(maquinaria));

		assertThrows(IllegalStateException.class, () -> {
			manejadorEntregarAlquiler.ejecutar(comando);
		});

		verify(maquinariaRepositorio, never()).guardar(any());
		verify(alquilerRepositorio, never()).guardar(any());
	}

	@Test
	void ejecutar_maquinariaNoDisponible_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.APROBADO);

		Maquinaria maquinaria = Maquinaria.builder().maquinariaId(20L).nombreEquipo("Excavadora").marca("CAT")
				.modelo("320D").serial("SN123").estado(EstadoMaquinaria.ALQUILADO)
				.tarifaPorDia(new BigDecimal("100000")).tarifaPorHora(new BigDecimal("15000"))
				.descripcion("Excavadora pesada").build();

		ComandoEntregarAlquiler comando = ComandoEntregarAlquiler.builder().alquilerId(1L).build();

		when(alquilerDao.buscarPorId(1L)).thenReturn(Optional.of(alquiler));
		when(maquinariaDao.buscarPorId(20L)).thenReturn(Optional.of(maquinaria));

		assertThrows(IllegalStateException.class, () -> {
			manejadorEntregarAlquiler.ejecutar(comando);
		});

		verify(maquinariaRepositorio, never()).guardar(any());
		verify(alquilerRepositorio, never()).guardar(any());
	}
}
