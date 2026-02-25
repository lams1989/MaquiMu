// src/app/core/models/auth/login-register.models.ts

export interface Usuario {
    usuarioId: number;
    nombreCompleto: string;
    email: string;
    rol: 'OPERARIO' | 'CLIENTE';
    clienteId?: number; // ID del cliente asociado si el rol es CLIENTE
}

export interface RegisterRequest {
    nombre: string;
    apellido: string;
    nombreCompleto?: string;
    email: string;
    password: string;
    identificacion: string; // Campo nuevo requerido para clientes
    rol: 'OPERARIO' | 'CLIENTE'; // Default for auto-registration is CLIENTE, but backend allows OPERARIO
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface AuthResponse {
    token: string;
    usuario: Usuario;
}
