package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.cliente.consulta.ConsultaBuscarCliente;
import com.maquimu.aplicacion.cliente.consulta.manejador.ManejadorBuscarCliente;
import com.maquimu.aplicacion.cliente.consulta.manejador.ManejadorListarClientes;
import com.maquimu.dominio.cliente.modelo.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maquimu/v1/clientes")
public class ConsultaControladorCliente {

    private final ManejadorListarClientes manejadorListarClientes;
    private final ManejadorBuscarCliente manejadorBuscarCliente;

    public ConsultaControladorCliente(ManejadorListarClientes manejadorListarClientes,
                                      ManejadorBuscarCliente manejadorBuscarCliente) {
        this.manejadorListarClientes = manejadorListarClientes;
        this.manejadorBuscarCliente = manejadorBuscarCliente;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = manejadorListarClientes.ejecutar();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable("id") Long id) {
        Cliente cliente = manejadorBuscarCliente.ejecutar(new ConsultaBuscarCliente(id));
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
