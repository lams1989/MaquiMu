package com.maquimu.dominio.alquiler.modelo;

/**
 * Estados posibles de un alquiler en su ciclo de vida. Máquina de estados:
 * PENDIENTE -> APROBADO -> ACTIVO -> FINALIZADO PENDIENTE -> RECHAZADO ACTIVO
 * -> PENDIENTE_EXTENSION -> ACTIVO (aprobada/rechazada) -> CANCELADO
 */
public enum EstadoAlquiler {
	PENDIENTE, // Solicitud creada por cliente, esperando aprobación
	APROBADO, // Aprobado por operario, esperando inicio
	RECHAZADO, // Rechazado por operario
	ACTIVO, // Alquiler en curso
	FINALIZADO, // Alquiler completado exitosamente
	CANCELADO, // Alquiler cancelado
	PENDIENTE_EXTENSION // Cliente solicitó extensión, esperando aprobación del operario
}
