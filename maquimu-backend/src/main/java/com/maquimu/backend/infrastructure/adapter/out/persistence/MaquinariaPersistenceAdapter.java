package com.maquimu.backend.infrastructure.adapter.out.persistence;

import com.maquimu.backend.domain.model.Maquinaria;
import com.maquimu.backend.domain.port.out.MaquinariaRepositoryPort;
import com.maquimu.backend.infrastructure.adapter.out.persistence.entity.MaquinariaEntity;
import com.maquimu.backend.infrastructure.adapter.out.persistence.repository.JpaMaquinariaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaquinariaPersistenceAdapter implements MaquinariaRepositoryPort {

    private final JpaMaquinariaRepository jpaMaquinariaRepository;

    public MaquinariaPersistenceAdapter(JpaMaquinariaRepository jpaMaquinariaRepository) {
        this.jpaMaquinariaRepository = jpaMaquinariaRepository;
    }

    @Override
    public Maquinaria save(Maquinaria maquinaria) {
        MaquinariaEntity entity = toEntity(maquinaria);
        MaquinariaEntity savedEntity = jpaMaquinariaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Maquinaria> findById(Long id) {
        return jpaMaquinariaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Maquinaria> findAll() {
        return jpaMaquinariaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaMaquinariaRepository.deleteById(id);
    }

    @Override
    public boolean existsBySerial(String serial) {
        return jpaMaquinariaRepository.existsBySerial(serial);
    }

    private MaquinariaEntity toEntity(Maquinaria domain) {
        if (domain == null)
            return null;
        return new MaquinariaEntity(
                domain.getId(),
                domain.getNombre(),
                domain.getMarca(),
                domain.getSerial(),
                domain.getModelo(),
                domain.getTarifaDia(),
                domain.getTarifaHora(),
                domain.getEstado(),
                domain.getDescripcion());
    }

    private Maquinaria toDomain(MaquinariaEntity entity) {
        if (entity == null)
            return null;
        return new Maquinaria(
                entity.getId(),
                entity.getNombre(),
                entity.getMarca(),
                entity.getModelo(),
                entity.getSerial(),
                entity.getEstado(),
                entity.getTarifaDia(),
                entity.getTarifaHora(),
                entity.getDescripcion());
    }
}
