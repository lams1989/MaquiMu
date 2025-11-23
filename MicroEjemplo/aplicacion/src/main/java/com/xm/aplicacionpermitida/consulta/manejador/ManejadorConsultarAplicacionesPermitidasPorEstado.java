package com.xm.aplicacionpermitida.consulta.manejador;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xm.aplicacionpermitida.modelo.dto.DtoAplicacionPermitida;
import com.xm.aplicacionpermitida.puerto.dao.DaoAplicacionPermitida;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ManejadorConsultarAplicacionesPermitidasPorEstado {

	private final DaoAplicacionPermitida daoAplicacionPermitida;

	public List<DtoAplicacionPermitida> ejecutar(boolean estado) {
		return this.daoAplicacionPermitida.consultarPorEstado(estado);
	}
}