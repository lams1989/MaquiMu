package com.maquimu.aplicacion.cliente.consulta.manejador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.maquimu.dominio.cliente.modelo.Cliente;
import com.maquimu.dominio.cliente.puerto.dao.ClienteDao;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorListarClientes {

	private final ClienteDao clienteDao;

	public List<Cliente> ejecutar() {
		return clienteDao.listarTodos();
	}
}
