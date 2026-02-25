package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoEliminarCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorCrearCliente;
import com.maquimu.aplicacion.cliente.comando.manejador.ManejadorEliminarCliente;
import com.maquimu.aplicacion.autenticacion.servicio.GeneradorJwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/clientes")
public class ComandoControladorCliente {

    private final ManejadorCrearCliente manejadorCrearCliente;
    private final ManejadorActualizarCliente manejadorActualizarCliente;
    private final ManejadorEliminarCliente manejadorEliminarCliente;
    private final GeneradorJwt generadorJwt;

    public ComandoControladorCliente(ManejadorCrearCliente manejadorCrearCliente,
                                     ManejadorActualizarCliente manejadorActualizarCliente,
                                     ManejadorEliminarCliente manejadorEliminarCliente,
                                     GeneradorJwt generadorJwt) {
        this.manejadorCrearCliente = manejadorCrearCliente;
        this.manejadorActualizarCliente = manejadorActualizarCliente;
        this.manejadorEliminarCliente = manejadorEliminarCliente;
        this.generadorJwt = generadorJwt;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> crearCliente(@RequestBody ComandoCrearCliente comandoCrearCliente,
                                                          HttpServletRequest request) {
        Long usuarioId = extraerUsuarioIdDesdeJwt(request);
        ComandoCrearCliente comando = ComandoCrearCliente.builder()
                .usuarioId(usuarioId)
                .nombreCliente(comandoCrearCliente.getNombreCliente())
            .apellido(comandoCrearCliente.getApellido())
                .identificacion(comandoCrearCliente.getIdentificacion())
                .telefono(comandoCrearCliente.getTelefono())
                .email(comandoCrearCliente.getEmail())
                .direccion(comandoCrearCliente.getDireccion())
                .build();
        Long clienteId = manejadorCrearCliente.ejecutar(comando);
        return new ResponseEntity<>(Map.of("clienteId", clienteId), HttpStatus.CREATED);
    }

    private Long extraerUsuarioIdDesdeJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring(7);
        return generadorJwt.obtenerUsuarioIdDelToken(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable("id") Long id, @RequestBody ComandoActualizarCliente comandoActualizarCliente) {
        ComandoActualizarCliente comando = ComandoActualizarCliente.builder()
                .clienteId(id)
                .nombreCliente(comandoActualizarCliente.getNombreCliente())
            .apellido(comandoActualizarCliente.getApellido())
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
