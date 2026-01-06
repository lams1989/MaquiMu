package com.maquimu.aplicacion.alquiler.consulta.manejador;

import com.maquimu.aplicacion.alquiler.consulta.ConsultaMaquinariasDisponibles;
import com.maquimu.dominio.alquiler.servicio.ValidadorDisponibilidadMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador de la consulta para obtener maquinarias disponibles.
 */
@Service
public class ManejadorMaquinariasDisponibles {

    private final MaquinariaDao maquinariaDao;
    private final ValidadorDisponibilidadMaquinaria validadorDisponibilidad;

    public ManejadorMaquinariasDisponibles(MaquinariaDao maquinariaDao, 
                                           ValidadorDisponibilidadMaquinaria validadorDisponibilidad) {
        this.maquinariaDao = maquinariaDao;
        this.validadorDisponibilidad = validadorDisponibilidad;
    }

    public List<Maquinaria> ejecutar(ConsultaMaquinariasDisponibles consulta) {
        // Obtener todas las maquinarias activas
        List<Maquinaria> todasMaquinarias = maquinariaDao.listarTodas();

        // Filtrar solo las que están disponibles en el rango de fechas solicitado
        return todasMaquinarias.stream()
                .filter(maquinaria -> validadorDisponibilidad.estaDisponible(
                        maquinaria.getId(), 
                        consulta.getFechaInicio(), 
                        consulta.getFechaFin()))
                .collect(Collectors.toList());
    }
}
