export interface Maquinaria {
  maquinariaId: number;
  nombreEquipo: string;
  marca: string;
  modelo: string;
  serial: string;
  estado: 'DISPONIBLE' | 'ALQUILADO' | 'EN_MANTENIMIENTO';
  tarifaPorDia: number;
  tarifaPorHora: number;
  descripcion?: string;
}

export interface CrearMaquinariaRequest {
  nombreEquipo: string;
  marca: string;
  modelo: string;
  serial: string;
  tarifaPorDia: number;
  tarifaPorHora: number;
  descripcion?: string;
}

export interface ActualizarMaquinariaRequest {
  nombreEquipo?: string;
  marca?: string;
  modelo?: string;
  serial?: string;
  estado?: 'DISPONIBLE' | 'ALQUILADO' | 'EN_MANTENIMIENTO';
  tarifaPorDia?: number;
  tarifaPorHora?: number;
  descripcion?: string;
}