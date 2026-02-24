package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.factura.consulta.manejador.ManejadorDescargarFactura;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultas de factura (lectura).
 */
@RestController
@RequestMapping("/api/maquimu/v1/facturas")
public class ConsultaControladorFactura {

    private final FacturaDao facturaDao;
    private final ManejadorDescargarFactura manejadorDescargarFactura;

    public ConsultaControladorFactura(FacturaDao facturaDao,
                                       ManejadorDescargarFactura manejadorDescargarFactura) {
        this.facturaDao = facturaDao;
        this.manejadorDescargarFactura = manejadorDescargarFactura;
    }

    /**
     * Lista todas las facturas, opcionalmente filtradas por estado de pago o cliente.
     * GET /api/maquimu/v1/facturas
     * GET /api/maquimu/v1/facturas?estadoPago=PENDIENTE
     * GET /api/maquimu/v1/facturas?clienteId=5
     */
    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas(
            @RequestParam(value = "estadoPago", required = false) EstadoPago estadoPago,
            @RequestParam(value = "clienteId", required = false) Long clienteId) {
        
        List<Factura> facturas;
        if (clienteId != null) {
            facturas = facturaDao.buscarPorClienteId(clienteId);
        } else if (estadoPago != null) {
            facturas = facturaDao.listarPorEstadoPago(estadoPago);
        } else {
            facturas = facturaDao.listarTodas();
        }
        return ResponseEntity.ok(facturas);
    }

    /**
     * Obtiene una factura por ID.
     * GET /api/maquimu/v1/facturas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable("id") Long id) {
        Factura factura = facturaDao.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + id));
        return ResponseEntity.ok(factura);
    }

    /**
     * Busca la factura de un alquiler específico.
     * GET /api/maquimu/v1/facturas/alquiler/{alquilerId}
     */
    @GetMapping("/alquiler/{alquilerId}")
    public ResponseEntity<Factura> obtenerFacturaPorAlquiler(@PathVariable("alquilerId") Long alquilerId) {
        Factura factura = facturaDao.buscarPorAlquilerId(alquilerId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró factura para el alquiler ID: " + alquilerId));
        return ResponseEntity.ok(factura);
    }

    /**
     * Descarga el PDF de una factura.
     * GET /api/maquimu/v1/facturas/{id}/pdf
     */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable("id") Long id) {
        byte[] pdfBytes = manejadorDescargarFactura.ejecutar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "factura-" + String.format("%06d", id) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
