package com.sena.proyecto.data.api

import com.sena.proyecto.data.model.AuthResponse
import com.sena.proyecto.data.model.LoginRequest
import com.sena.proyecto.data.model.MensajeResponse
import com.sena.proyecto.data.model.RegisterRequest
import com.sena.proyecto.data.model.ResetPasswordRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("maquimu/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("maquimu/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MensajeResponse>

    @POST("maquimu/v1/auth/solicitar-restablecimiento")
    suspend fun solicitarRestablecimiento(@Body request: ResetPasswordRequest): Response<MensajeResponse>
}
