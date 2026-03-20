package com.maquimu.infraestructura.dashboard.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maquimu.aplicacion.autenticacion.servicio.ServicioGeneradorJwt;
import com.maquimu.aplicacion.dashboard.consulta.ConsultaDashboardCliente;
import com.maquimu.aplicacion.dashboard.consulta.ConsultaDashboardOperario;
import com.maquimu.aplicacion.dashboard.consulta.manejador.ManejadorDashboardCliente;
import com.maquimu.aplicacion.dashboard.consulta.manejador.ManejadorDashboardOperario;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador REST para el dashboard.
 */
@RestController
@RequestMapping("/api/maquimu/v1/dashboard")
public class ConsultaControladorDashboard {

	private final ManejadorDashboardOperario manejadorDashboardOperario;
	private final ManejadorDashboardCliente manejadorDashboardCliente;
	private final ServicioGeneradorJwt servicioGeneradorJwt;
	private final ClienteDao clienteDao;

	public ConsultaControladorDashboard(ManejadorDashboardOperario manejadorDashboardOperario,
			ManejadorDashboardCliente manejadorDashboardCliente, ServicioGeneradorJwt servicioGeneradorJwt,
			ClienteDao clienteDao) {
		this.manejadorDashboardOperario = manejadorDashboardOperario;
		this.manejadorDashboardCliente = manejadorDashboardCliente;
		this.servicioGeneradorJwt = servicioGeneradorJwt;
		this.clienteDao = clienteDao;
	}

	/**
	 * Obtiene los KPIs del dashboard del operario. GET
	 * /api/maquimu/v1/dashboard/operario
	 */
	@GetMapping("/operario")
	public ResponseEntity<ConsultaDashboardOperario> obtenerDashboardOperario() {
		ConsultaDashboardOperario dashboard = manejadorDashboardOperario.ejecutar();
		return ResponseEntity.ok(dashboard);
	}

	/**
	 * Obtiene los KPIs del dashboard del cliente autenticado. GET
	 * /api/maquimu/v1/dashboard/cliente
	 */
	@GetMapping("/cliente")
	public ResponseEntity<ConsultaDashboardCliente> obtenerDashboardCliente(HttpServletRequest request) {
		Cliente cliente = extraerClienteDesdeJwt(request);
		ConsultaDashboardCliente dashboard = manejadorDashboardCliente.ejecutar(cliente.getClienteId());
		return ResponseEntity.ok(dashboard);
	}

	// ========== Utilidades JWT ==========

	private Cliente extraerClienteDesdeJwt(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
			throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
		}
		String token = header.substring(7);
		Long usuarioId = servicioGeneradorJwt.obtenerUsuarioIdDelToken(token);
		if (usuarioId == null) {
			throw new IllegalArgumentException("No se pudo identificar al usuario desde el token");
		}
		return clienteDao.buscarPorUsuarioId(usuarioId)
				.orElseThrow(() -> new IllegalArgumentException("No se encontro el cliente asociado al usuario"));
	}
}
