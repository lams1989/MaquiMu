package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoEliminarCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorCrearCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorEliminarCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/clientes")
public class ComandoControladorCliente {

    private final ManejadorCrearCliente manejadorCrearCliente;
    private final ManejadorActualizarCliente manejadorActualizarCliente;
    private final ManejadorEliminarCliente manejadorEliminarCliente;

    public ComandoControladorCliente(ManejadorCrearCliente manejadorCrearCliente,
                                     ManejadorActualizarCliente manejadorActualizarCliente,
                                     ManejadorEliminarCliente manejadorEliminarCliente) {
        this.manejadorCrearCliente = manejadorCrearCliente;
        this.manejadorActualizarCliente = manejadorActualizarCliente;
        this.manejadorEliminarCliente = manejadorEliminarCliente;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> crearCliente(@RequestBody ComandoCrearCliente comandoCrearCliente) {
        Long clienteId = manejadorCrearCliente.ejecutar(comandoCrearCliente);
        return new ResponseEntity<>(Map.of("clienteId", clienteId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable("id") Long id, @RequestBody ComandoActualizarCliente comandoActualizarCliente) {
        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(id)
                .nombreCliente(comandoActualizarCliente.getNombreCliente())
                .identificacion(comandoActualizarCliente.getIdentificacion())
                .telefono(comandoActualizarCliente.getTelefono())
                .email(comandoActualizarCliente.getEmail())
                .direccion(comandoActualizarCliente.getDireccion())
                .build();
        manejadorActualizarCliente.ejecutar(comando);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
        manejadorEliminarCliente.ejecutar(new ComandoEliminarCliente(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
