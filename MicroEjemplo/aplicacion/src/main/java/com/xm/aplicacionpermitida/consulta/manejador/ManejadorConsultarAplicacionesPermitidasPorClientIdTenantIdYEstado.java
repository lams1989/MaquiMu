package com.xm.aplicacionpermitida.consulta.manejador;

import org.springframework.stereotype.Component;

import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;
import com.xm.aplicacionpermitida.puerto.dao.DaoAplicacionPermitida;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorConsultarAplicacionesPermitidasPorClientIdTenantIdYEstado {

	private final DaoAplicacionPermitida daoAplicacionPermitida;

	public DtoAplicacionPermitida ejecutar(String clienteId, String tenantId, boolean estado) {
		return this.daoAplicacionPermitida.consultarPorClientIdTenantIdYEstado(clienteId, tenantId, estado);
	}
}
