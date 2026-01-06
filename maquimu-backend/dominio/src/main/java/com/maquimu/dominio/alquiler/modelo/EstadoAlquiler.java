package com.maquimu.dominio.alquiler.modelo;

/**
 * Estados posibles de un alquiler en su ciclo de vida.
 * Máquina de estados:
 * PENDIENTE -> APROBADO -> ACTIVO -> FINALIZADO
 *                                 -> CANCELADO
 */
public enum EstadoAlquiler {
    PENDIENTE,      // Solicitud creada por cliente, esperando aprobación
    APROBADO,       // Aprobado por operario, esperando inicio
    ACTIVO,         // Alquiler en curso
    FINALIZADO,     // Alquiler completado exitosamente
    CANCELADO       // Alquiler cancelado
}
