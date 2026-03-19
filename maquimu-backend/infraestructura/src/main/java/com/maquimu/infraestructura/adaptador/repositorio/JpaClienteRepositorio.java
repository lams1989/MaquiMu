package com.maquimu.infraestructura.adaptador.repositorio;

import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import com.maquimu.infraestructura.adaptador.entidad.ClienteEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JpaClienteRepositorio implements ClienteRepositorio {

    private final JpaClienteRepository jpaClienteRepository;

    public JpaClienteRepositorio(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteEntity clienteEntity = ClienteEntity.fromCliente(cliente);
        return jpaClienteRepository.save(clienteEntity).toCliente();
    }

    @Override
    public void eliminar(Long id) {
        jpaClienteRepository.deleteById(id);
    }
}
