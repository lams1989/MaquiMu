package com.maquimu.infraestructura.factura.controlador;

import com.maquimu.aplicacion.autenticacion.servicio.GeneradorJwt;
import com.maquimu.aplicacion.factura.consulta.manejador.ManejadorDescargarFactura;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.factura.modelo.EstadoPago;
import com.maquimu.dominio.factura.modelo.Factura;
import com.maquimu.dominio.factura.puerto.dao.FacturaDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private final GeneradorJwt generadorJwt;
    private final ClienteDao clienteDao;

    public ConsultaControladorFactura(FacturaDao facturaDao,
                                       ManejadorDescargarFactura manejadorDescargarFactura,
                                       GeneradorJwt generadorJwt,
                                       ClienteDao clienteDao) {
        this.facturaDao = facturaDao;
        this.manejadorDescargarFactura = manejadorDescargarFactura;
        this.generadorJwt = generadorJwt;
        this.clienteDao = clienteDao;
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

    // ========== Endpoints para CLIENTE (JWT) ==========

    /**
     * Lista las facturas del cliente autenticado.
     * GET /api/maquimu/v1/facturas/mis-facturas
     */
    @GetMapping("/mis-facturas")
    public ResponseEntity<List<Factura>> listarMisFacturas(HttpServletRequest request) {
        Cliente cliente = extraerClienteDesdeJwt(request);
        List<Factura> facturas = facturaDao.buscarPorClienteId(cliente.getClienteId());
        return ResponseEntity.ok(facturas);
    }

    /**
     * Descarga el PDF de una factura verificando que pertenece al cliente autenticado.
     * GET /api/maquimu/v1/facturas/mis-facturas/{id}/pdf
     */
    @GetMapping("/mis-facturas/{id}/pdf")
    public ResponseEntity<byte[]> descargarMiPdf(@PathVariable("id") Long id, HttpServletRequest request) {
        Cliente cliente = extraerClienteDesdeJwt(request);

        Factura factura = facturaDao.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + id));

        if (!factura.getClienteId().equals(cliente.getClienteId())) {
            throw new SecurityException("No tienes permiso para acceder a esta factura");
        }

        byte[] pdfBytes = manejadorDescargarFactura.ejecutar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "factura-" + String.format("%06d", id) + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    // ========== Utilidades JWT ==========

    private Cliente extraerClienteDesdeJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
        }
        String token = header.substring(7);
        Long usuarioId = generadorJwt.obtenerUsuarioIdDelToken(token);
        if (usuarioId == null) {
            throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
        }
        return clienteDao.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro el cliente asociado al usuario"));
    }
}
