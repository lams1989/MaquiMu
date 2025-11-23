package com.xm.aplicacionpermitida.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DtoAplicacionPermitida {
	private int aplicacionPermitidaId;
	private String nombre;
	private String clienteIdJwt;
	private String tipoIntegracion;
	private String tenantId;
	private String ambiente;
	private boolean estado;
	private String urlWebhook;
}