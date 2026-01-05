package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoEliminarCliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorEliminarCliente {

    private final ClienteRepositorio clienteRepositorio;
    private final ClienteDao clienteDao;

    @Transactional
    public void ejecutar(ComandoEliminarCliente comando) {
        if (clienteDao.buscarPorId(comando.getClienteId()).isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + comando.getClienteId());
        }
        clienteRepositorio.eliminar(comando.getClienteId());
    }
}
