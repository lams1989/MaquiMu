package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarExtension;
import com.maquimu.aplicacion.alquiler.comando.ComandoEntregarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoFinalizarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarExtension;
import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarExtension;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorAprobarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorAprobarExtension;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorEntregarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorFinalizarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorRechazarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorRechazarExtension;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorSolicitarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorSolicitarExtension;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maquimu/v1/alquileres")
public class ComandoControladorAlquiler {

    private final ManejadorSolicitarAlquiler manejadorSolicitarAlquiler;
    private final ManejadorAprobarAlquiler manejadorAprobarAlquiler;
    private final ManejadorRechazarAlquiler manejadorRechazarAlquiler;
    private final ManejadorEntregarAlquiler manejadorEntregarAlquiler;
    private final ManejadorFinalizarAlquiler manejadorFinalizarAlquiler;
    private final ManejadorSolicitarExtension manejadorSolicitarExtension;
    private final ManejadorAprobarExtension manejadorAprobarExtension;
    private final ManejadorRechazarExtension manejadorRechazarExtension;

    public ComandoControladorAlquiler(ManejadorSolicitarAlquiler manejadorSolicitarAlquiler,
                                       ManejadorAprobarAlquiler manejadorAprobarAlquiler,
                                       ManejadorRechazarAlquiler manejadorRechazarAlquiler,
                                       ManejadorEntregarAlquiler manejadorEntregarAlquiler,
                                       ManejadorFinalizarAlquiler manejadorFinalizarAlquiler,
                                       ManejadorSolicitarExtension manejadorSolicitarExtension,
                                       ManejadorAprobarExtension manejadorAprobarExtension,
                                       ManejadorRechazarExtension manejadorRechazarExtension) {
        this.manejadorSolicitarAlquiler = manejadorSolicitarAlquiler;
        this.manejadorAprobarAlquiler = manejadorAprobarAlquiler;
        this.manejadorRechazarAlquiler = manejadorRechazarAlquiler;
        this.manejadorEntregarAlquiler = manejadorEntregarAlquiler;
        this.manejadorFinalizarAlquiler = manejadorFinalizarAlquiler;
        this.manejadorSolicitarExtension = manejadorSolicitarExtension;
        this.manejadorAprobarExtension = manejadorAprobarExtension;
        this.manejadorRechazarExtension = manejadorRechazarExtension;
    }

    @PostMapping
    public ResponseEntity<Alquiler> solicitarAlquiler(@RequestBody ComandoSolicitarAlquiler comando) {
        Alquiler nuevoAlquiler = manejadorSolicitarAlquiler.ejecutar(comando);
        return new ResponseEntity<>(nuevoAlquiler, HttpStatus.CREATED);
    }

    /**
     * Aprueba un alquiler pendiente.
     * PATCH /api/maquimu/v1/alquileres/{id}/aprobar
     */
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Alquiler> aprobarAlquiler(@PathVariable("id") Long id,
                                                      @RequestBody ComandoAprobarAlquiler comando) {
        ComandoAprobarAlquiler comandoCompleto = ComandoAprobarAlquiler.builder()
                .alquilerId(id)
                .usuarioId(comando.getUsuarioId())
                .build();
        Alquiler alquiler = manejadorAprobarAlquiler.ejecutar(comandoCompleto);
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Rechaza un alquiler pendiente.
     * PATCH /api/maquimu/v1/alquileres/{id}/rechazar
     */
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<Alquiler> rechazarAlquiler(@PathVariable("id") Long id,
                                                       @RequestBody(required = false) ComandoRechazarAlquiler comando) {
        ComandoRechazarAlquiler comandoCompleto = ComandoRechazarAlquiler.builder()
                .alquilerId(id)
                .motivoRechazo(comando != null ? comando.getMotivoRechazo() : null)
                .build();
        Alquiler alquiler = manejadorRechazarAlquiler.ejecutar(comandoCompleto);
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Registra la entrega de maquinaria al cliente.
     * PATCH /api/maquimu/v1/alquileres/{id}/entregar
     * Transición: Alquiler APROBADO->ACTIVO + Maquinaria DISPONIBLE->ALQUILADO
     */
    @PatchMapping("/{id}/entregar")
    public ResponseEntity<Alquiler> entregarAlquiler(@PathVariable("id") Long id) {
        ComandoEntregarAlquiler comando = ComandoEntregarAlquiler.builder()
                .alquilerId(id)
                .build();
        Alquiler alquiler = manejadorEntregarAlquiler.ejecutar(comando);
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Finaliza un alquiler activo (devolución de maquinaria).
     * PATCH /api/maquimu/v1/alquileres/{id}/finalizar
     * Transición: Alquiler ACTIVO->FINALIZADO + Maquinaria ALQUILADO->DISPONIBLE
     */
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Alquiler> finalizarAlquiler(@PathVariable("id") Long id) {
        ComandoFinalizarAlquiler comando = ComandoFinalizarAlquiler.builder()
                .alquilerId(id)
                .build();
        Alquiler alquiler = manejadorFinalizarAlquiler.ejecutar(comando);
        return ResponseEntity.ok(alquiler);
    }

    // ===== HU #17: Extensión de Alquiler Activo =====

    /**
     * Solicitar extensión de un alquiler activo (cliente).
     * POST /api/maquimu/v1/alquileres/{id}/extension
     * Transición: ACTIVO -> PENDIENTE_EXTENSION
     */
    @PostMapping("/{id}/extension")
    public ResponseEntity<Alquiler> solicitarExtension(@PathVariable("id") Long id,
                                                        @RequestBody ComandoSolicitarExtension comando) {
        ComandoSolicitarExtension comandoCompleto = ComandoSolicitarExtension.builder()
                .alquilerId(id)
                .nuevaFechaFin(comando.getNuevaFechaFin())
                .build();
        Alquiler alquiler = manejadorSolicitarExtension.ejecutar(comandoCompleto);
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Aprobar extensión de un alquiler (operario).
     * PATCH /api/maquimu/v1/alquileres/{id}/aprobar-extension
     * Transición: PENDIENTE_EXTENSION -> ACTIVO (con fechas y costo actualizados)
     */
    @PatchMapping("/{id}/aprobar-extension")
    public ResponseEntity<Alquiler> aprobarExtension(@PathVariable("id") Long id) {
        ComandoAprobarExtension comando = ComandoAprobarExtension.builder()
                .alquilerId(id)
                .build();
        Alquiler alquiler = manejadorAprobarExtension.ejecutar(comando);
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Rechazar extensión de un alquiler (operario).
     * PATCH /api/maquimu/v1/alquileres/{id}/rechazar-extension
     * Transición: PENDIENTE_EXTENSION -> ACTIVO (sin cambios)
     */
    @PatchMapping("/{id}/rechazar-extension")
    public ResponseEntity<Alquiler> rechazarExtension(@PathVariable("id") Long id,
                                                       @RequestBody(required = false) ComandoRechazarExtension comando) {
        ComandoRechazarExtension comandoCompleto = ComandoRechazarExtension.builder()
                .alquilerId(id)
                .motivo(comando != null ? comando.getMotivo() : null)
                .build();
        Alquiler alquiler = manejadorRechazarExtension.ejecutar(comandoCompleto);
        return ResponseEntity.ok(alquiler);
    }
}
