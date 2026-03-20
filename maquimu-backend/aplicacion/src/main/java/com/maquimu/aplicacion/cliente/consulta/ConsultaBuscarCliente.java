package com.maquimu.aplicacion.cliente.consulta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaBuscarCliente {
	private Long clienteId;
}
