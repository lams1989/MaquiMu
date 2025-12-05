package com.maquimu.infraestructura.autenticacion.adaptador.repositorio;

import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.infraestructura.autenticacion.adaptador.dao.JpaUsuarioRepository;
import com.maquimu.infraestructura.autenticacion.adaptador.entidad.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUsuarioRepositorio implements UsuarioRepositorio {

    private final JpaUsuarioRepository jpaRepository;

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario);
        UsuarioEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id.intValue());
    }
}
