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
  fechaFinSolicitada?: string;
  costoAdicional?: number;
}

export type EstadoAlquiler = 'PENDIENTE' | 'APROBADO' | 'RECHAZADO' | 'ACTIVO' | 'FINALIZADO' | 'CANCELADO' | 'PENDIENTE_EXTENSION';

export interface SolicitudAlquiler {
  clienteId: number;
  maquinariaId: number;
  fechaInicio: string;
  fechaFin: string;
}

export interface SolicitudExtension {
  nuevaFechaFin: string;
}

export interface RechazoExtension {
  motivo?: string;
}
