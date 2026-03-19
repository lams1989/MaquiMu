package com.maquimu.aplicacion.comando.fabrica;

import com.maquimu.aplicacion.comando.ComandoActualizarMaquinaria;
import com.maquimu.aplicacion.comando.ComandoCrearMaquinaria;
import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FabricaMaquinaria {

    public Maquinaria crear(ComandoCrearMaquinaria comando) {
        return new Maquinaria(
                comando.getNombreEquipo(),
                comando.getMarca(),
                comando.getModelo(),
                comando.getSerial(),
                EstadoMaquinaria.DISPONIBLE, // Estado inicial por defecto
                comando.getTarifaPorDia(),
                comando.getTarifaPorHora(),
                comando.getDescripcion()
        );
    }

    public Maquinaria actualizar(Maquinaria existente, ComandoActualizarMaquinaria comando) {
        existente.setNombreEquipo(Optional.ofNullable(comando.getNombreEquipo()).orElse(existente.getNombreEquipo()));
        existente.setMarca(Optional.ofNullable(comando.getMarca()).orElse(existente.getMarca()));
        existente.setModelo(Optional.ofNullable(comando.getModelo()).orElse(existente.getModelo()));
        existente.setSerial(Optional.ofNullable(comando.getSerial()).orElse(existente.getSerial()));
        existente.setTarifaPorDia(Optional.ofNullable(comando.getTarifaPorDia()).orElse(existente.getTarifaPorDia()));
        existente.setTarifaPorHora(Optional.ofNullable(comando.getTarifaPorHora()).orElse(existente.getTarifaPorHora()));
        existente.setDescripcion(Optional.ofNullable(comando.getDescripcion()).orElse(existente.getDescripcion()));
        // El estado se actualiza si se proporciona en el comando de actualización
        Optional.ofNullable(comando.getEstado())
                .map(EstadoMaquinaria::valueOf)
                .ifPresent(existente::setEstado);
        return existente;
    }
}
