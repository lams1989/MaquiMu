package com.maquimu.backend.application.service;

import com.maquimu.backend.domain.model.Maquinaria;
import com.maquimu.backend.domain.port.out.MaquinariaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaquinariaService {

    private final MaquinariaRepositoryPort maquinariaRepositoryPort;

    public MaquinariaService(MaquinariaRepositoryPort maquinariaRepositoryPort) {
        this.maquinariaRepositoryPort = maquinariaRepositoryPort;
    }

    @Transactional
    public Maquinaria createMaquinaria(Maquinaria maquinaria) {
        if (maquinariaRepositoryPort.existsBySerial(maquinaria.getSerial())) {
            throw new IllegalArgumentException("Ya existe una maquinaria con el serial: " + maquinaria.getSerial());
        }
        if (maquinaria.getEstado() == null) {
            maquinaria.setEstado("DISPONIBLE");
        }
        return maquinariaRepositoryPort.save(maquinaria);
    }

    @Transactional(readOnly = true)
    public List<Maquinaria> getAllMaquinarias() {
        return maquinariaRepositoryPort.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Maquinaria> getMaquinariaById(Long id) {
        return maquinariaRepositoryPort.findById(id);
    }

    @Transactional
    public Maquinaria updateMaquinaria(Long id, Maquinaria maquinaria) {
        return maquinariaRepositoryPort.findById(id)
                .map(existing -> {
                    // Check if serial is being changed and if it conflicts
                    if (!existing.getSerial().equals(maquinaria.getSerial()) &&
                            maquinariaRepositoryPort.existsBySerial(maquinaria.getSerial())) {
                        throw new IllegalArgumentException(
                                "Ya existe una maquinaria con el serial: " + maquinaria.getSerial());
                    }

                    existing.setNombre(maquinaria.getNombre());
                    existing.setMarca(maquinaria.getMarca());
                    existing.setModelo(maquinaria.getModelo());
                    existing.setSerial(maquinaria.getSerial());
                    existing.setEstado(maquinaria.getEstado());
                    existing.setTarifaDia(maquinaria.getTarifaDia());
                    existing.setTarifaHora(maquinaria.getTarifaHora());
                    existing.setDescripcion(maquinaria.getDescripcion());

                    return maquinariaRepositoryPort.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Maquinaria no encontrada con id: " + id));
    }

    @Transactional
    public void deleteMaquinaria(Long id) {
        if (maquinariaRepositoryPort.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Maquinaria no encontrada con id: " + id);
        }
        maquinariaRepositoryPort.deleteById(id);
    }
}
