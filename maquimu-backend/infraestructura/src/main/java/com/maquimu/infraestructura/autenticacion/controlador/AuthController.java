package com.maquimu.infraestructura.autenticacion.controlador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.RespuestaAutenticacion;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorAutenticarUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ManejadorRegistrarUsuario manejadorRegistrarUsuario;
    private final ManejadorAutenticarUsuario manejadorAutenticarUsuario;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody ComandoRegistrarUsuario comando) {
        manejadorRegistrarUsuario.ejecutar(comando);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado exitosamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaAutenticacion> login(@RequestBody ConsultaAutenticarUsuario consulta) {
        RespuestaAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);
        return ResponseEntity.ok(respuesta);
    }
}
