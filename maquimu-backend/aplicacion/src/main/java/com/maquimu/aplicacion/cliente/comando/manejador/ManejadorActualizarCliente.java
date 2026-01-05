package com.maquimu.aplicacion.cliente.comando.manejador;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ManejadorActualizarCliente {

    private final FabricaCliente fabricaCliente;
    private final ClienteRepositorio clienteRepositorio;
    private final ClienteDao clienteDao;

    @Transactional
    public void ejecutar(ComandoActualizarCliente comando) {
        Cliente clienteExistente = clienteDao.buscarPorId(comando.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + comando.getClienteId()));

        validarIdentificacionUnica(comando.getIdentificacion(), comando.getClienteId());

        Cliente clienteActualizado = fabricaCliente.actualizar(clienteExistente, comando);
        clienteRepositorio.guardar(clienteActualizado);
    }

    private void validarIdentificacionUnica(String identificacion, Long clienteId) {
        clienteDao.buscarPorIdentificacion(identificacion)
                .ifPresent(cliente -> {
                    if (!cliente.getClienteId().equals(clienteId)) {
                        throw new IllegalArgumentException("Ya existe otro cliente con la identificación: " + identificacion);
                    }
                });
    }
}
