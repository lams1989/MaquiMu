package com.maquimu.aplicacion.cliente.comando.manejador;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.fabrica.FabricaCliente;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.autenticacion.puerto.dao.UsuarioDao;
import com.maquimu.dominio.autenticacion.puerto.repositorio.UsuarioRepositorio;
import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;
import com.maquimu.dominio.cliente.puerto.repositorio.ClienteRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorActualizarCliente {

	private final FabricaCliente fabricaCliente;
	private final ClienteRepositorio clienteRepositorio;
	private final ClienteDao clienteDao;
	private final UsuarioDao usuarioDao;
	private final UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void ejecutar(ComandoActualizarCliente comando) {
		Cliente clienteExistente = clienteDao.buscarPorId(comando.getClienteId()).orElseThrow(
				() -> new IllegalArgumentException("Cliente no encontrado con ID: " + comando.getClienteId()));

		if (comando.getIdentificacion() != null) {
			validarIdentificacionUnica(comando.getIdentificacion(), comando.getClienteId());
		}

		if (comando.getEmail() != null) {
			validarEmailUnico(comando.getEmail(), comando.getClienteId());
		}

		String emailAnterior = clienteExistente.getEmail();
		Cliente clienteActualizado = fabricaCliente.actualizar(clienteExistente, comando);
		clienteRepositorio.guardar(clienteActualizado);

		sincronizarEmailUsuario(comando, emailAnterior);
	}

	private void sincronizarEmailUsuario(ComandoActualizarCliente comando, String emailAnterior) {
		if (comando.getUsuarioId() == null || comando.getEmail() == null) {
			return;
		}
		if (comando.getEmail().equals(emailAnterior)) {
			return;
		}
		usuarioDao.buscarPorId(comando.getUsuarioId()).ifPresent(usuario -> {
			usuario.setEmail(comando.getEmail());
			usuarioRepositorio.guardar(usuario);
		});
	}

	private void validarIdentificacionUnica(String identificacion, Long clienteId) {
		clienteDao.buscarPorIdentificacion(identificacion).ifPresent(cliente -> {
			if (!cliente.getClienteId().equals(clienteId)) {
				throw new IllegalArgumentException("Ya existe otro cliente con la identificación: " + identificacion);
			}
		});
	}

	private void validarEmailUnico(String email, Long clienteId) {
		clienteDao.buscarPorEmail(email).ifPresent(cliente -> {
			if (!cliente.getClienteId().equals(clienteId)) {
				throw new IllegalArgumentException("Ya existe otro cliente con el email: " + email);
			}
		});
	}
}
