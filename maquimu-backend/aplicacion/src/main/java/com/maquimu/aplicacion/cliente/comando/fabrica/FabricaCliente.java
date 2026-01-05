package com.maquimu.aplicacion.cliente.comando.fabrica;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.dominio.cliente.modelo.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FabricaCliente {

    public Cliente crear(ComandoCrearCliente comando) {
        return Cliente.builder()
                .nombreCliente(comando.getNombreCliente())
                .identificacion(comando.getIdentificacion())
                .telefono(comando.getTelefono())
                .email(comando.getEmail())
                .direccion(comando.getDireccion())
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    public Cliente actualizar(Cliente clienteExistente, ComandoActualizarCliente comando) {
        return Cliente.builder()
                .clienteId(clienteExistente.getClienteId())
                .nombreCliente(comando.getNombreCliente())
                .identificacion(comando.getIdentificacion())
                .telefono(comando.getTelefono())
                .email(comando.getEmail())
                .direccion(comando.getDireccion())
                .fechaRegistro(clienteExistente.getFechaRegistro())
                .build();
    }
}
