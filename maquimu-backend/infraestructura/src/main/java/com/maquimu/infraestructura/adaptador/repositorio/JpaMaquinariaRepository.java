package com.maquimu.infraestructura.adaptador.repositorio;

import com.maquimu.dominio.modelo.EstadoMaquinaria;
import com.maquimu.infraestructura.adaptador.entidad.MaquinariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMaquinariaRepository extends JpaRepository<MaquinariaEntity, Long> {
    Optional<MaquinariaEntity> findBySerial(String serial);
    boolean existsBySerial(String serial);
    long countByEstado(EstadoMaquinaria estado);
}
