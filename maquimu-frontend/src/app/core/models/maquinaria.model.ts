export interface Maquinaria {
    id?: number;
    nombre: string;
    marca?: string;
    modelo?: string;
    serial: string;
    estado: 'DISPONIBLE' | 'ALQUILADO' | 'MANTENIMIENTO';
    tarifaDia: number;
    tarifaHora: number;
    descripcion?: string;
}
