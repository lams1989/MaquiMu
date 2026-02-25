package com.maquimu.infraestructura.autenticacion.controlador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoRechazarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorAprobarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorRechazarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorConsultarUsuariosPendientes;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/usuarios")
@RequiredArgsConstructor
public class ComandoControladorUsuario {

    private final ManejadorConsultarUsuariosPendientes manejadorConsultarPendientes;
    private final ManejadorAprobarUsuario manejadorAprobarUsuario;
    private final ManejadorRechazarUsuario manejadorRechazarUsuario;

    @GetMapping("/pendientes")
    public ResponseEntity<List<Map<String, Object>>> listarPendientes() {
        List<Usuario> pendientes = manejadorConsultarPendientes.ejecutar();
        List<Map<String, Object>> respuesta = pendientes.stream()
                .map(u -> Map.<String, Object>of(
                        "usuarioId", u.getId(),
                        "nombreCompleto", u.getNombreCompleto(),
                        "email", u.getEmail(),
                        "rol", u.getRol().name(),
                        "estado", u.getEstado().name(),
                        "fechaCreacion", u.getFechaCreacion().toString()
                ))
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable("id") Long id) {
        manejadorAprobarUsuario.ejecutar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario aprobado exitosamente"));
    }

    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable("id") Long id, @RequestBody ComandoRechazarUsuario comando) {
        manejadorRechazarUsuario.ejecutar(id, comando.getMotivo());
        return ResponseEntity.ok(Map.of("mensaje", "Usuario rechazado exitosamente"));
    }
}
