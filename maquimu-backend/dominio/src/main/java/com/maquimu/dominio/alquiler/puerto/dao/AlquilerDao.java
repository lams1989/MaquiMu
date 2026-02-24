package com.maquimu.dominio.alquiler.puerto.dao;

import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de lectura para Alquiler (DAO - Data Access Object).
 * Define operaciones de consulta sin modificar datos.
 */
public interface AlquilerDao {

    /**
     * Lista todos los alquileres.
     */
    List<Alquiler> listarTodos();

    /**
     * Busca un alquiler por su ID.
     */
    Optional<Alquiler> buscarPorId(Long id);

    /**
     * Lista los alquileres de un cliente específico.
     */
    List<Alquiler> buscarPorCliente(Long clienteId);

    /**
     * Busca alquileres de una maquinaria que se solapan con un rango de fechas.
     * Usado para validar disponibilidad.
     * 
     * @param maquinariaId ID de la maquinaria
     * @param fechaInicio Inicio del rango
     * @param fechaFin Fin del rango
     * @return Lista de alquileres que se solapan con el rango
     */
    List<Alquiler> buscarPorMaquinariaYRangoFechas(Long maquinariaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Lista los alquileres pendientes de aprobación.
     */
    List<Alquiler> listarPendientes();

    /**
     * Lista los alquileres filtrados por estado.
     */
    List<Alquiler> listarPorEstado(EstadoAlquiler estado);

    /**
     * Cuenta los alquileres por estado.
     */
    long contarPorEstado(EstadoAlquiler estado);

    /**
     * Cuenta los alquileres de un cliente específico filtrados por estado.
     */
    long contarPorClienteYEstado(Long clienteId, EstadoAlquiler estado);
}
