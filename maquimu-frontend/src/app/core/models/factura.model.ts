export interface Factura {
  facturaId?: number;
  alquilerId: number;
  clienteId: number;
  fechaEmision: string;
  monto: number;
  estadoPago: EstadoPago;
}

export type EstadoPago = 'PENDIENTE' | 'PAGADO' | 'ANULADO';
