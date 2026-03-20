package com.maquimu.aplicacion.cliente.comando.fabrica;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.maquimu.aplicacion.cliente.comando.ComandoActualizarCliente;
import com.maquimu.aplicacion.cliente.comando.ComandoCrearCliente;
import com.maquimu.dominio.autenticacion.modelo.Usuario;
import com.maquimu.dominio.cliente.modelo.Cliente;

@Component
public class FabricaCliente {

	public Cliente crear(ComandoCrearCliente comando) {
		return Cliente.builder().usuarioId(comando.getUsuarioId()).nombreCliente(comando.getNombreCliente())
				.apellido(comando.getApellido()).identificacion(comando.getIdentificacion())
				.telefono(comando.getTelefono()).email(comando.getEmail()).direccion(comando.getDireccion())
				.fechaRegistro(LocalDateTime.now()).build();
	}

	/**
	 * Crea un Cliente a partir de un Usuario registrado con rol CLIENTE. Usa los
	 * datos del usuario para llenar los campos del cliente.
	 */
	public Cliente crearDesdeUsuario(Usuario usuario, String identificacion, String nombre, String apellido) {
		return Cliente.builder().usuarioId(usuario.getId()).nombreCliente(nombre).apellido(apellido)
				.identificacion(identificacion).telefono(null) // Se puede completar después
				.email(usuario.getEmail()).direccion(null) // Se puede completar después
				.fechaRegistro(LocalDateTime.now()).build();
	}

	public Cliente actualizar(Cliente clienteExistente, ComandoActualizarCliente comando) {
		return Cliente.builder().clienteId(clienteExistente.getClienteId()).usuarioId(clienteExistente.getUsuarioId())
				.nombreCliente(comando.getNombreCliente()).apellido(comando.getApellido())
				.identificacion(comando.getIdentificacion()).telefono(comando.getTelefono()).email(comando.getEmail())
				.direccion(comando.getDireccion()).fechaRegistro(clienteExistente.getFechaRegistro()).build();
	}
}
