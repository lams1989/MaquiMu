package com.maquimu.infraestructura.controlador;

import com.maquimu.aplicacion.alquiler.consulta.ConsultaAlquileresCliente;
import com.maquimu.aplicacion.alquiler.consulta.ConsultaMaquinariasDisponibles;
import com.maquimu.aplicacion.alquiler.consulta.manejador.ManejadorAlquileresCliente;
import com.maquimu.aplicacion.alquiler.consulta.manejador.ManejadorMaquinariasDisponibles;
import com.maquimu.aplicacion.autenticacion.servicio.GeneradorJwt;
import com.maquimu.dominio.alquiler.modelo.Alquiler;
import com.maquimu.dominio.alquiler.modelo.EstadoAlquiler;
import com.maquimu.dominio.alquiler.puerto.dao.AlquilerDao;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.modelo.Maquinaria;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maquimu/v1")
public class ConsultaControladorAlquiler {

    private final ManejadorMaquinariasDisponibles manejadorMaquinariasDisponibles;
    private final ManejadorAlquileresCliente manejadorAlquileresCliente;
    private final AlquilerDao alquilerDao;
    private final ClienteDao clienteDao;
    private final GeneradorJwt generadorJwt;

    public ConsultaControladorAlquiler(ManejadorMaquinariasDisponibles manejadorMaquinariasDisponibles,
                                        ManejadorAlquileresCliente manejadorAlquileresCliente,
                                        AlquilerDao alquilerDao,
                                        ClienteDao clienteDao,
                                        GeneradorJwt generadorJwt) {
        this.manejadorMaquinariasDisponibles = manejadorMaquinariasDisponibles;
        this.manejadorAlquileresCliente = manejadorAlquileresCliente;
        this.alquilerDao = alquilerDao;
        this.clienteDao = clienteDao;
        this.generadorJwt = generadorJwt;
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

    /**
     * Lista todos los alquileres, opcionalmente filtrados por estado.
     * GET /api/maquimu/v1/alquileres
     * GET /api/maquimu/v1/alquileres?estado=PENDIENTE
     */
    @GetMapping("/alquileres")
    public ResponseEntity<List<Alquiler>> listarAlquileres(
            @RequestParam(value = "estado", required = false) EstadoAlquiler estado) {
        List<Alquiler> alquileres;
        if (estado != null) {
            alquileres = alquilerDao.listarPorEstado(estado);
        } else {
            alquileres = alquilerDao.listarTodos();
        }
        return ResponseEntity.ok(alquileres);
    }

    /**
     * Obtiene el detalle de un alquiler por ID.
     * GET /api/maquimu/v1/alquileres/{id}
     */
    @GetMapping("/alquileres/{id}")
    public ResponseEntity<Alquiler> obtenerAlquiler(@PathVariable("id") Long id) {
        Alquiler alquiler = alquilerDao.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + id));
        return ResponseEntity.ok(alquiler);
    }

    /**
     * Lista los alquileres del cliente autenticado (obtiene clienteId desde JWT).
     * GET /api/maquimu/v1/alquileres/mis-alquileres
     */
    @GetMapping("/alquileres/mis-alquileres")
    public ResponseEntity<List<Alquiler>> listarMisAlquileres(HttpServletRequest request) {
        Long usuarioId = extraerUsuarioIdDesdeJwt(request);
        if (usuarioId == null) {
            throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
        }

        Cliente cliente = clienteDao.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cliente asociado al usuario"));

        ConsultaAlquileresCliente consulta = new ConsultaAlquileresCliente(cliente.getClienteId());
        List<Alquiler> alquileres = manejadorAlquileresCliente.ejecutar(consulta);
        return ResponseEntity.ok(alquileres);
    }

    /**
     * Obtiene el detalle de un alquiler verificando que pertenece al cliente autenticado.
     * GET /api/maquimu/v1/alquileres/mis-alquileres/{id}
     */
    @GetMapping("/alquileres/mis-alquileres/{id}")
    public ResponseEntity<Alquiler> obtenerMiAlquiler(@PathVariable("id") Long id, HttpServletRequest request) {
        Long usuarioId = extraerUsuarioIdDesdeJwt(request);
        if (usuarioId == null) {
            throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
        }

        Cliente cliente = clienteDao.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el cliente asociado al usuario"));

        Alquiler alquiler = alquilerDao.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + id));

        if (!alquiler.getClienteId().equals(cliente.getClienteId())) {
            throw new SecurityException("No tienes permiso para ver este alquiler");
        }

        return ResponseEntity.ok(alquiler);
    }

    private Long extraerUsuarioIdDesdeJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring(7);
        return generadorJwt.obtenerUsuarioIdDelToken(token);
    }
}
