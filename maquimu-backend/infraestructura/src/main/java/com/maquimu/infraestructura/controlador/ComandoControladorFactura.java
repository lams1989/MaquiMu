package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.factura.comando.ComandoActualizarEstadoPago;
import com.maquimu.aplicacion.factura.comando.ComandoGenerarFactura;
import com.maquimu.aplicacion.factura.comando.manejador.ManejadorActualizarEstadoPago;
import com.maquimu.aplicacion.factura.comando.manejador.ManejadorGenerarFactura;
import com.maquimu.dominio.factura.modelo.Factura;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para comandos de factura (escritura).
 */
@RestController
@RequestMapping("/api/maquimu/v1/facturas")
public class ComandoControladorFactura {

    private final ManejadorGenerarFactura manejadorGenerarFactura;
    private final ManejadorActualizarEstadoPago manejadorActualizarEstadoPago;

    public ComandoControladorFactura(ManejadorGenerarFactura manejadorGenerarFactura,
                                      ManejadorActualizarEstadoPago manejadorActualizarEstadoPago) {
        this.manejadorGenerarFactura = manejadorGenerarFactura;
        this.manejadorActualizarEstadoPago = manejadorActualizarEstadoPago;
    }

    /**
     * Genera una factura para un alquiler finalizado.
     * POST /api/maquimu/v1/facturas
     */
    @PostMapping
    public ResponseEntity<Factura> generarFactura(@RequestBody ComandoGenerarFactura comando) {
        Factura factura = manejadorGenerarFactura.ejecutar(comando);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

    /**
     * Actualiza el estado de pago de una factura.
     * PATCH /api/maquimu/v1/facturas/{id}/estado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Factura> actualizarEstadoPago(@PathVariable("id") Long id,
                                                         @RequestBody ComandoActualizarEstadoPago comando) {
        ComandoActualizarEstadoPago comandoCompleto = ComandoActualizarEstadoPago.builder()
                .facturaId(id)
                .estadoPago(comando.getEstadoPago())
                .build();
        Factura factura = manejadorActualizarEstadoPago.ejecutar(comandoCompleto);
        return ResponseEntity.ok(factura);
    }
}
