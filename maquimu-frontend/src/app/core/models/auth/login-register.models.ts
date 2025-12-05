// src/app/core/models/auth/login-register.models.ts

export interface Usuario {
    usuarioId: number;
    nombreCompleto: string;
    email: string;
    rol: 'OPERARIO' | 'CLIENTE'; // Assuming these are the only two roles
}

export interface RegisterRequest {
    nombreCompleto: string;
    email: string;
    password: string;
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
