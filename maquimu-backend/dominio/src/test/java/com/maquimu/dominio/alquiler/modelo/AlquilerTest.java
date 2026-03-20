package com.maquimu.dominio.alquiler.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class AlquilerTest {

	private Alquiler crearAlquilerPendiente() {
		return new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5),
				new BigDecimal("500000"), EstadoAlquiler.PENDIENTE);
	}

	private Alquiler crearAlquilerAprobado() {
		return new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5),
				new BigDecimal("500000"), EstadoAlquiler.APROBADO);
	}

	private Alquiler crearAlquilerActivo() {
		return new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5),
				new BigDecimal("500000"), EstadoAlquiler.ACTIVO);
	}

	// ===== Tests de creación =====

	@Test
	void crearAlquiler_conSolicitud_deberiaEstablecerEstadoPendiente() {
		Alquiler alquiler = new Alquiler(10L, 20L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5),
				new BigDecimal("500000"));

		assertEquals(EstadoAlquiler.PENDIENTE, alquiler.getEstado());
		assertNull(alquiler.getUsuarioId());
		assertNull(alquiler.getMotivoRechazo());
	}

	@Test
	void crearAlquiler_conFechasInvalidas_deberiaLanzarExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Alquiler(10L, 20L, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(1),
					new BigDecimal("500000"));
		});
	}

	@Test
	void crearAlquiler_conFechasNulas_deberiaLanzarExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Alquiler(10L, 20L, null, null, new BigDecimal("500000"));
		});
	}

	// ===== Tests de aprobar =====

	@Test
	void aprobar_desdePendiente_deberiaTransicionarAAprobado() {
		Alquiler alquiler = crearAlquilerPendiente();

		alquiler.aprobar(100L);

		assertEquals(EstadoAlquiler.APROBADO, alquiler.getEstado());
		assertEquals(100L, alquiler.getUsuarioId());
	}

	@Test
	void aprobar_desdeAprobado_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerAprobado();

		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			alquiler.aprobar(100L);
		});
		assertEquals("Solo se puede aprobar un alquiler en estado PENDIENTE", exception.getMessage());
	}

	@Test
	void aprobar_desdeActivo_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerActivo();

		assertThrows(IllegalStateException.class, () -> {
			alquiler.aprobar(100L);
		});
	}

	// ===== Tests de rechazar =====

	@Test
	void rechazar_desdePendiente_deberiaTransicionarARechazado() {
		Alquiler alquiler = crearAlquilerPendiente();

		alquiler.rechazar("Maquinaria no disponible");

		assertEquals(EstadoAlquiler.RECHAZADO, alquiler.getEstado());
		assertEquals("Maquinaria no disponible", alquiler.getMotivoRechazo());
	}

	@Test
	void rechazar_desdePendienteSinMotivo_deberiaTransicionarARechazado() {
		Alquiler alquiler = crearAlquilerPendiente();

		alquiler.rechazar(null);

		assertEquals(EstadoAlquiler.RECHAZADO, alquiler.getEstado());
		assertNull(alquiler.getMotivoRechazo());
	}

	@Test
	void rechazar_desdeAprobado_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerAprobado();

		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			alquiler.rechazar("Motivo");
		});
		assertEquals("Solo se puede rechazar un alquiler en estado PENDIENTE", exception.getMessage());
	}

	@Test
	void rechazar_desdeActivo_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerActivo();

		assertThrows(IllegalStateException.class, () -> {
			alquiler.rechazar("Motivo");
		});
	}

	// ===== Tests de activar =====

	@Test
	void activar_desdeAprobado_deberiaTransicionarAActivo() {
		Alquiler alquiler = crearAlquilerAprobado();

		alquiler.activar();

		assertEquals(EstadoAlquiler.ACTIVO, alquiler.getEstado());
	}

	@Test
	void activar_desdePendiente_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerPendiente();

		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			alquiler.activar();
		});
		assertEquals("Solo se puede activar un alquiler en estado APROBADO", exception.getMessage());
	}

	@Test
	void activar_desdeActivo_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerActivo();

		assertThrows(IllegalStateException.class, () -> {
			alquiler.activar();
		});
	}

	// ===== Tests de finalizar =====

	@Test
	void finalizar_desdeActivo_deberiaTransicionarAFinalizado() {
		Alquiler alquiler = crearAlquilerActivo();

		alquiler.finalizar();

		assertEquals(EstadoAlquiler.FINALIZADO, alquiler.getEstado());
	}

	@Test
	void finalizar_desdePendiente_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerPendiente();

		assertThrows(IllegalStateException.class, () -> {
			alquiler.finalizar();
		});
	}

	@Test
	void finalizar_desdeAprobado_deberiaLanzarExcepcion() {
		Alquiler alquiler = crearAlquilerAprobado();

		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			alquiler.finalizar();
		});
		assertEquals("Solo se puede finalizar un alquiler en estado ACTIVO", exception.getMessage());
	}

	// ===== Tests de cancelar =====

	@Test
	void cancelar_desdePendiente_deberiaTransicionarACancelado() {
		Alquiler alquiler = crearAlquilerPendiente();

		alquiler.cancelar();

		assertEquals(EstadoAlquiler.CANCELADO, alquiler.getEstado());
	}

	@Test
	void cancelar_desdeAprobado_deberiaTransicionarACancelado() {
		Alquiler alquiler = crearAlquilerAprobado();

		alquiler.cancelar();

		assertEquals(EstadoAlquiler.CANCELADO, alquiler.getEstado());
	}

	@Test
	void cancelar_desdeFinalizado_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, 100L, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.FINALIZADO);

		assertThrows(IllegalStateException.class, () -> {
			alquiler.cancelar();
		});
	}

	@Test
	void cancelar_desdeCancelado_deberiaLanzarExcepcion() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.CANCELADO);

		assertThrows(IllegalStateException.class, () -> {
			alquiler.cancelar();
		});
	}

	// ===== Test del constructor completo con motivoRechazo =====

	@Test
	void constructorCompleto_conMotivoRechazo_deberiaEstablecerMotivoRechazo() {
		Alquiler alquiler = new Alquiler(1L, 10L, 20L, null, LocalDateTime.now().plusDays(1),
				LocalDateTime.now().plusDays(5), new BigDecimal("500000"), EstadoAlquiler.RECHAZADO,
				"No hay disponibilidad");

		assertEquals(EstadoAlquiler.RECHAZADO, alquiler.getEstado());
		assertEquals("No hay disponibilidad", alquiler.getMotivoRechazo());
	}

	// ===== Test de flujo completo =====

	@Test
	void flujoCompleto_pendienteAFinalizado_deberiaTransicionarCorrectamente() {
		Alquiler alquiler = new Alquiler(10L, 20L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5),
				new BigDecimal("500000"));

		assertEquals(EstadoAlquiler.PENDIENTE, alquiler.getEstado());

		alquiler.aprobar(100L);
		assertEquals(EstadoAlquiler.APROBADO, alquiler.getEstado());
		assertEquals(100L, alquiler.getUsuarioId());

		alquiler.activar();
		assertEquals(EstadoAlquiler.ACTIVO, alquiler.getEstado());

		alquiler.finalizar();
		assertEquals(EstadoAlquiler.FINALIZADO, alquiler.getEstado());
	}
}
