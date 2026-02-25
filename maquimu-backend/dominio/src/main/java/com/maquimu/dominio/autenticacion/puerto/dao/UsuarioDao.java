package com.maquimu.dominio.autenticacion.puerto.dao;

import com.maquimu.dominio.autenticacion.modelo.EstadoUsuario;
import com.maquimu.dominio.autenticacion.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {
    Optional<Usuario> buscarPorEmail(String email);
    boolean existePorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    List<Usuario> buscarPorEstado(EstadoUsuario estado);
}
