package com.maquimu.infraestructura.autenticacion.controlador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.ComandoSolicitarRestablecimiento;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorRegistrarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorSolicitarRestablecimiento;
import com.maquimu.aplicacion.autenticacion.consulta.ConsultaAutenticarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.ComandoAutenticacion;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorAutenticarUsuario;
import com.maquimu.dominio.autenticacion.excepcion.CuentaNoActivaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ManejadorRegistrarUsuario manejadorRegistrarUsuario;
    private final ManejadorAutenticarUsuario manejadorAutenticarUsuario;
    private final ManejadorSolicitarRestablecimiento manejadorSolicitarRestablecimiento;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody ComandoRegistrarUsuario comando) {
        manejadorRegistrarUsuario.ejecutar(comando);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Tu cuenta ha sido creada exitosamente. Un operario la revisará en un plazo de 1 a 3 días hábiles."
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ConsultaAutenticarUsuario consulta) {
        try {
            ComandoAutenticacion respuesta = manejadorAutenticarUsuario.ejecutar(consulta);
            return ResponseEntity.ok(respuesta);
        } catch (CuentaNoActivaException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("estado", ex.getEstado().name());
            body.put("message", ex.getMessage());
            if (ex.getMotivoRechazo() != null) {
                body.put("motivoRechazo", ex.getMotivoRechazo());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
        }
    }

    @PostMapping("/solicitar-restablecimiento")
    public ResponseEntity<?> solicitarRestablecimiento(@RequestBody ComandoSolicitarRestablecimiento comando) {
        try {
            manejadorSolicitarRestablecimiento.ejecutar(comando);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Se ha enviado una solicitud de restablecimiento. Un operario te asignará una contraseña temporal en un plazo de 1 a 3 días hábiles."
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }
}
