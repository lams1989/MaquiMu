package com.maquimu.infraestructura.autenticacion.adaptador.dao;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaUsuarioDao implements UsuarioDao {

    private final JpaUsuarioRepository jpaRepository;

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UsuarioEntity::toDomain);
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepository.findById(id.intValue())
                .map(UsuarioEntity::toDomain);
    }

    @Override
    public List<Usuario> buscarPorEstado(EstadoUsuario estado) {
        return jpaRepository.findByEstadoCuenta(estado)
                .stream()
                .map(UsuarioEntity::toDomain)
                .collect(Collectors.toList());
    }
}
