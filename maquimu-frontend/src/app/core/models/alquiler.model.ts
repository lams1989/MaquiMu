export interface Alquiler {
  alquilerId?: number;
  clienteId: number;
  maquinariaId: number;
  usuarioId?: number;
  fechaInicio: string;
  fechaFin: string;
  costoTotal: number;
  estado: EstadoAlquiler;
  motivoRechazo?: string;
}

export type EstadoAlquiler = 'PENDIENTE' | 'APROBADO' | 'RECHAZADO' | 'ACTIVO' | 'FINALIZADO' | 'CANCELADO';

export interface SolicitudAlquiler {
  clienteId: number;
  maquinariaId: number;
  fechaInicio: string;
  fechaFin: string;
}
