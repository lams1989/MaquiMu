package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.alquiler.consulta.ConsultaAlquileresCliente;
import com.maquimu.aplicacion.alquiler.consulta.ConsultaMaquinariasDisponibles;
import com.maquimu.aplicacion.alquiler.consulta.manejador.ManejadorAlquileresCliente;
import com.maquimu.aplicacion.alquiler.consulta.manejador.ManejadorMaquinariasDisponibles;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.modelo.Maquinaria;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maquimu/v1")
public class ConsultaControladorAlquiler {

    private final ManejadorMaquinariasDisponibles manejadorMaquinariasDisponibles;
    private final ManejadorAlquileresCliente manejadorAlquileresCliente;

    public ConsultaControladorAlquiler(ManejadorMaquinariasDisponibles manejadorMaquinariasDisponibles,
                                        ManejadorAlquileresCliente manejadorAlquileresCliente) {
        this.manejadorMaquinariasDisponibles = manejadorMaquinariasDisponibles;
        this.manejadorAlquileresCliente = manejadorAlquileresCliente;
    }

    /**
     * Lista maquinarias disponibles en un rango de fechas.
     * GET /api/maquimu/v1/maquinarias/disponibles?fechaInicio=2024-01-01T00:00:00&fechaFin=2024-01-10T23:59:59
     */
    @GetMapping("/maquinarias/disponibles")
    public ResponseEntity<List<Maquinaria>> listarMaquinariasDisponibles(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        ConsultaMaquinariasDisponibles consulta = new ConsultaMaquinariasDisponibles(fechaInicio, fechaFin);
        List<Maquinaria> maquinariasDisponibles = manejadorMaquinariasDisponibles.ejecutar(consulta);
        return ResponseEntity.ok(maquinariasDisponibles);
    }

    /**
     * Lista los alquileres de un cliente específico.
     * GET /api/maquimu/v1/clientes/{clienteId}/alquileres
     */
    @GetMapping("/clientes/{clienteId}/alquileres")
    public ResponseEntity<List<Alquiler>> listarAlquileresCliente(@PathVariable("clienteId") Long clienteId) {
        ConsultaAlquileresCliente consulta = new ConsultaAlquileresCliente(clienteId);
        List<Alquiler> alquileres = manejadorAlquileresCliente.ejecutar(consulta);
        return ResponseEntity.ok(alquileres);
    }
}
