package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.alquiler.comando.ComandoSolicitarAlquiler;
import com.maquimu.aplicacion.alquiler.comando.manejador.ManejadorSolicitarAlquiler;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maquimu/v1/alquileres")
public class ComandoControladorAlquiler {

    private final ManejadorSolicitarAlquiler manejadorSolicitarAlquiler;

    public ComandoControladorAlquiler(ManejadorSolicitarAlquiler manejadorSolicitarAlquiler) {
        this.manejadorSolicitarAlquiler = manejadorSolicitarAlquiler;
    }

    @PostMapping
    public ResponseEntity<Alquiler> solicitarAlquiler(@RequestBody ComandoSolicitarAlquiler comando) {
        Alquiler nuevoAlquiler = manejadorSolicitarAlquiler.ejecutar(comando);
        return new ResponseEntity<>(nuevoAlquiler, HttpStatus.CREATED);
    }
}
