export interface Alquiler {
  alquilerId?: number;
  clienteId: number;
  maquinariaId: number;
  usuarioId?: number;
  fechaInicio: string;
  fechaFin: string;
  costoTotal: number;
  estado: EstadoAlquiler;
}

export type EstadoAlquiler = 'PENDIENTE' | 'APROBADO' | 'ACTIVO' | 'FINALIZADO' | 'CANCELADO';

export interface SolicitudAlquiler {
  clienteId: number;
  maquinariaId: number;
  fechaInicio: string;
  fechaFin: string;
}
