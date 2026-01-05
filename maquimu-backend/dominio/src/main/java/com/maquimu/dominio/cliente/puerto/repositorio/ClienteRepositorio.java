package com.maquimu.dominio.cliente.puerto.repositorio;

import com.maquimu.dominio.cliente.modelo.Cliente;

public interface ClienteRepositorio {
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
