package com.maquimu.infraestructura.autenticacion.controlador;

import com.maquimu.aplicacion.autenticacion.comando.ComandoAsignarPasswordTemporal;
import com.maquimu.aplicacion.autenticacion.comando.ComandoCambiarPassword;
import com.maquimu.aplicacion.autenticacion.comando.ComandoCambiarRol;
import com.maquimu.aplicacion.autenticacion.comando.ComandoRechazarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorAprobarUsuario;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorAsignarPasswordTemporal;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorCambiarPassword;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorCambiarRol;
import com.maquimu.aplicacion.autenticacion.comando.manejador.ManejadorRechazarUsuario;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorConsultarUsuariosPendientes;
import com.maquimu.aplicacion.autenticacion.consulta.manejador.ManejadorConsultarUsuariosRestablecer;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maquimu/v1/usuarios")
@RequiredArgsConstructor
public class ComandoControladorUsuario {

    private final ManejadorConsultarUsuariosPendientes manejadorConsultarPendientes;
    private final ManejadorConsultarUsuariosRestablecer manejadorConsultarRestablecer;
    private final ManejadorAprobarUsuario manejadorAprobarUsuario;
    private final ManejadorRechazarUsuario manejadorRechazarUsuario;
    private final ManejadorCambiarPassword manejadorCambiarPassword;
    private final ManejadorCambiarRol manejadorCambiarRol;
    private final ManejadorAsignarPasswordTemporal manejadorAsignarPasswordTemporal;
    private final UsuarioDao usuarioDao;

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

    @GetMapping("/restablecer")
    public ResponseEntity<List<Map<String, Object>>> listarRestablecer() {
        List<Usuario> restablecer = manejadorConsultarRestablecer.ejecutar();
        List<Map<String, Object>> respuesta = restablecer.stream()
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

    @PatchMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody ComandoCambiarPassword comando) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = usuarioDao.buscarPorEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            manejadorCambiarPassword.ejecutar(usuario.getId(), comando);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/asignar-password")
    public ResponseEntity<?> asignarPasswordTemporal(@PathVariable("id") Long id, @RequestBody ComandoAsignarPasswordTemporal comando) {
        try {
            manejadorAsignarPasswordTemporal.ejecutar(id, comando);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña temporal asignada y enviada por correo."));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable("id") Long id) {
        return usuarioDao.buscarPorId(id)
                .map(u -> ResponseEntity.ok(Map.<String, Object>of(
                        "usuarioId", u.getId(),
                        "nombreCompleto", u.getNombreCompleto(),
                        "email", u.getEmail(),
                        "rol", u.getRol().name(),
                        "estado", u.getEstado().name(),
                        "fechaCreacion", u.getFechaCreacion().toString()
                )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario no encontrado")));
    }

    @PatchMapping("/{id}/cambiar-rol")
    public ResponseEntity<?> cambiarRol(@PathVariable("id") Long id, @RequestBody ComandoCambiarRol comando) {
        try {
            manejadorCambiarRol.ejecutar(id, comando.getNuevoRol());
            return ResponseEntity.ok(Map.of("mensaje", "Rol actualizado exitosamente"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
        }
    }
}
