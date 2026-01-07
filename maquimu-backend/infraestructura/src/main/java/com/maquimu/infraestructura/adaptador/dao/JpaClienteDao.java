package com.maquimu.infraestructura.adaptador.dao;

import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.infraestructura.adaptador.entidad.ClienteEntity;
import com.maquimu.infraestructura.adaptador.repositorio.JpaClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaClienteDao implements ClienteDao {

    private final JpaClienteRepository jpaClienteRepository;

    public JpaClienteDao(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public List<Cliente> listarTodos() {
        return jpaClienteRepository.findAll().stream()
                .map(ClienteEntity::toCliente)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpaClienteRepository.findById(id)
                .map(ClienteEntity::toCliente);
    }

    @Override
    public Optional<Cliente> buscarPorIdentificacion(String identificacion) {
        return jpaClienteRepository.findByIdentificacion(identificacion)
                .map(ClienteEntity::toCliente);
    }

    @Override
    public Optional<Cliente> buscarPorUsuarioId(Long usuarioId) {
        return jpaClienteRepository.findByUsuarioId(usuarioId)
                .map(ClienteEntity::toCliente);
    }

    @Override
    public boolean existePorIdentificacion(String identificacion) {
        return jpaClienteRepository.existsByIdentificacion(identificacion);
    }
}
