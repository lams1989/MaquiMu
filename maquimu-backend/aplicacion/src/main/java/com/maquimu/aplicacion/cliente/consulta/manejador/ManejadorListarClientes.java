package com.maquimu.aplicacion.cliente.consulta.manejador;

import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ManejadorListarClientes {

    private final ClienteDao clienteDao;

    public List<Cliente> ejecutar() {
        return clienteDao.listarTodos();
    }
}
