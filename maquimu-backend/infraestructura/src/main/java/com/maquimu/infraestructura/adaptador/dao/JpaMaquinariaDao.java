package com.maquimu.infraestructura.adaptador.dao;

import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.dominio.modelo.Maquinaria;
import com.maquimu.dominio.puerto.dao.MaquinariaDao;
import com.maquimu.infraestructura.adaptador.entidad.MaquinariaEntity; // Added this import
import com.maquimu.infraestructura.adaptador.repositorio.JpaMaquinariaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMaquinariaDao implements MaquinariaDao {

    private final JpaMaquinariaRepository jpaMaquinariaRepository;

    public JpaMaquinariaDao(JpaMaquinariaRepository jpaMaquinariaRepository) {
        this.jpaMaquinariaRepository = jpaMaquinariaRepository;
    }

    @Override
    public List<Maquinaria> listarTodas() {
        return jpaMaquinariaRepository.findAll().stream()
                .map(MaquinariaEntity::toMaquinaria)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Maquinaria> buscarPorId(Long id) {
        return jpaMaquinariaRepository.findById(id)
                .map(MaquinariaEntity::toMaquinaria);
    }

    @Override
    public Optional<Maquinaria> buscarPorSerial(String serial) {
        return jpaMaquinariaRepository.findBySerial(serial)
                .map(MaquinariaEntity::toMaquinaria);
    }

    @Override
    public boolean existePorSerial(String serial) {
        return jpaMaquinariaRepository.existsBySerial(serial);
    }

    @Override
    public long contarPorEstado(EstadoMaquinaria estado) {
        return jpaMaquinariaRepository.countByEstado(estado);
    }
}
