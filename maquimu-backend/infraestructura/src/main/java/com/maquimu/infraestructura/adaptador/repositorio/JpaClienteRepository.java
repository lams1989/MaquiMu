package com.maquimu.infraestructura.adaptador.repositorio;

import com.maquimu.infraestructura.adaptador.entidad.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
}
