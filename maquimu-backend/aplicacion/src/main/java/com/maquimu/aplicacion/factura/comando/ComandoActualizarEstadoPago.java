package com.maquimu.aplicacion.factura.comando;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Comando para actualizar el estado de pago de una factura.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandoActualizarEstadoPago {
	private Long facturaId;
	private String estadoPago; // "PAGADO" o "ANULADO"
}
