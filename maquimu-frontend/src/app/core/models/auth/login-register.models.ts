// src/app/core/models/auth/login-register.models.ts

export interface Usuario {
    usuarioId: number;
    nombreCompleto: string;
    email: string;
    rol: 'OPERARIO' | 'CLIENTE';
    estado?: 'PENDIENTE_APROBACION' | 'ACTIVO' | 'RECHAZADO' | 'RESTABLECER';
    clienteId?: number; // ID del cliente asociado si el rol es CLIENTE
}

export interface RegisterRequest {
    nombre: string;
    apellido: string;
    nombreCompleto?: string;
    email: string;
    password: string;
    identificacion: string;
    rol: 'CLIENTE'; // Solo clientes se auto-registran
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface AuthResponse {
    token: string;
    usuario: Usuario;
}

export interface LoginErrorResponse {
    estado?: 'PENDIENTE_APROBACION' | 'RECHAZADO' | 'RESTABLECER';
    message?: string;
    motivoRechazo?: string;
}
