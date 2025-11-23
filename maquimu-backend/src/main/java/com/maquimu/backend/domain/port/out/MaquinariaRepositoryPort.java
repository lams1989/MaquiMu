package com.maquimu.backend.domain.port.out;

import com.maquimu.backend.domain.model.Maquinaria;
import java.util.List;
import java.util.Optional;

public interface MaquinariaRepositoryPort {
    Maquinaria save(Maquinaria maquinaria);

    Optional<Maquinaria> findById(Long id);

    List<Maquinaria> findAll();

    void deleteById(Long id);

    boolean existsBySerial(String serial);
}
