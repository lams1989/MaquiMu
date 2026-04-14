package com.sena.proyecto.data.model

import com.google.gson.annotations.SerializedName

// ========== REQUEST DTOs ==========

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val nombre: String,
    val apellido: String?,
    val email: String,
    val password: String,
    val rol: String = "CLIENTE",
    val identificacion: String
)

data class ResetPasswordRequest(
    val email: String
)

// ========== RESPONSE DTOs ==========

data class AuthResponse(
    val token: String,
    val usuario: UsuarioResponse
)

data class UsuarioResponse(
    val id: Long,
    val nombreCompleto: String,
    val email: String,
    val rol: String,
    val estado: String,
    val clienteId: Long?
)

data class MensajeResponse(
    val mensaje: String?
)

data class ErrorResponse(
    val estado: String?,
    val message: String?,
    val motivoRechazo: String?
)
