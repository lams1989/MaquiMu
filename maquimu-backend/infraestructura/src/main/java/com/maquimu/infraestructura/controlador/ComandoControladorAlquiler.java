package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.alquiler.comando.ComandoAprobarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoEntregarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoFinalizarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoRechazarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorAprobarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorEntregarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorFinalizarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorRechazarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorSolicitarAlquiler;
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

    public ComandoControladorAlquiler(ManejadorSolicitarAlquiler manejadorSolicitarAlquiler,
                                       ManejadorAprobarAlquiler manejadorAprobarAlquiler,
                                       ManejadorRechazarAlquiler manejadorRechazarAlquiler,
                                       ManejadorEntregarAlquiler manejadorEntregarAlquiler,
                                       ManejadorFinalizarAlquiler manejadorFinalizarAlquiler) {
        this.manejadorSolicitarAlquiler = manejadorSolicitarAlquiler;
        this.manejadorAprobarAlquiler = manejadorAprobarAlquiler;
        this.manejadorRechazarAlquiler = manejadorRechazarAlquiler;
        this.manejadorEntregarAlquiler = manejadorEntregarAlquiler;
        this.manejadorFinalizarAlquiler = manejadorFinalizarAlquiler;
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
}
