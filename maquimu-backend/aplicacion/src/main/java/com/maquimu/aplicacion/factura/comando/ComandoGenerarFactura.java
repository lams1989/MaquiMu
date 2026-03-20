package com.maquimu.aplicacion.factura.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para generar una factura a partir de un alquiler finalizado.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoGenerarFactura {
	private Long alquilerId;
}
