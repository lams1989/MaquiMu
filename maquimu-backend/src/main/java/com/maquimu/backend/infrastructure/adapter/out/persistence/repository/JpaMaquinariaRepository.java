package com.maquimu.backend.infrastructure.adapter.out.persistence.repository;

import com.maquimu.backend.infrastructure.adapter.out.persistence.entity.MaquinariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMaquinariaRepository extends JpaRepository<MaquinariaEntity, Long> {
    boolean existsBySerial(String serial);
}
