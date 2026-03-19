package com.maquimu.infraestructura.autenticacion.adaptador.dao;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UsuarioEntity> findByEstadoCuenta(EstadoUsuario estadoCuenta);
}
