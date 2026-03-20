package com.maquimu.dominio.cliente.puerto.dao;

import java.util.List;
import java.util.Optional;

import com.maquimu.dominio.cliente.modelo.Cliente;

public interface ClienteDao {
	List<Cliente> listarTodos();

	Optional<Cliente> buscarPorId(Long id);

	Optional<Cliente> buscarPorIdentificacion(String identificacion);

	Optional<Cliente> buscarPorEmail(String email);

	Optional<Cliente> buscarPorUsuarioId(Long usuarioId);

	boolean existePorIdentificacion(String identificacion);

	boolean existePorEmail(String email);
}
