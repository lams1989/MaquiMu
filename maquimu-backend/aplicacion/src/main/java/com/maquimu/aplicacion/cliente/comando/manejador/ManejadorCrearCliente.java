package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorCrearCliente {

    private final FabricaCliente fabricaCliente;
    private final ClienteRepositorio clienteRepositorio;
    private final ClienteDao clienteDao;

    @Transactional
    public Long ejecutar(ComandoCrearCliente comando) {
        validarIdentificacionUnica(comando.getIdentificacion());
        Cliente cliente = fabricaCliente.crear(comando);
        Cliente clienteGuardado = clienteRepositorio.guardar(cliente);
        return clienteGuardado.getClienteId();
    }

    private void validarIdentificacionUnica(String identificacion) {
        if (clienteDao.existePorIdentificacion(identificacion)) {
            throw new IllegalArgumentException("Ya existe un cliente con la identificación: " + identificacion);
        }
    }
}
