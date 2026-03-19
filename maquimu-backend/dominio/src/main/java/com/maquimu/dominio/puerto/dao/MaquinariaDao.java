package com.maquimu.dominio.puerto.dao;

import com.maquimu.dominio.modelo.Maquinaria;

import java.util.List;
import java.util.Optional;

public interface MaquinariaDao {
    List<Maquinaria> listarTodas();
    Optional<Maquinaria> buscarPorId(Long id);
    Optional<Maquinaria> buscarPorSerial(String serial);
    boolean existePorSerial(String serial);
    long contarPorEstado(com.maquimu.dominio.modelo.EstadoMaquinaria estado); // Added for HU 11
}
