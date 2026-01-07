package com.maquimu.dominio.cliente.puerto.dao;

import com.maquimu.dominio.cliente.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDao {
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorIdentificacion(String identificacion);
    Optional<Cliente> buscarPorUsuarioId(Long usuarioId);
    boolean existePorIdentificacion(String identificacion);
}
