package com.maquimu.aplicacion.cliente.consulta.manejador;

import com.maquimu.aplicacion.cliente.consulta.ConsultaBuscarCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManejadorBuscarCliente {

    private final ClienteDao clienteDao;

    public Cliente ejecutar(ConsultaBuscarCliente consulta) {
        return clienteDao.buscarPorId(consulta.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + consulta.getClienteId()));
    }
}
