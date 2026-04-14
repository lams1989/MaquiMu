package com.sena.proyecto.data.api

import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.model.AuthResponse
import com.sena.proyecto.data.model.Cliente
import com.sena.proyecto.data.model.DashboardClienteResponse
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.data.model.LoginRequest
import com.sena.proyecto.data.model.MensajeResponse
import com.sena.proyecto.data.model.RegisterRequest
import com.sena.proyecto.data.model.ResetPasswordRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // ========== Auth ==========
    @POST("maquimu/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("maquimu/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MensajeResponse>

    @POST("maquimu/v1/auth/solicitar-restablecimiento")
    suspend fun solicitarRestablecimiento(@Body request: ResetPasswordRequest): Response<MensajeResponse>

    // ========== Dashboard ==========
    @GET("maquimu/v1/dashboard/cliente")
    suspend fun getDashboardCliente(): Response<DashboardClienteResponse>

    // ========== Alquileres ==========
    @GET("maquimu/v1/alquileres/mis-alquileres")
    suspend fun getMisAlquileres(): Response<List<Alquiler>>

    @GET("maquimu/v1/alquileres/mis-alquileres/{id}")
    suspend fun getAlquilerDetalle(@Path("id") id: Long): Response<Alquiler>

    // ========== Facturas ==========
    @GET("maquimu/v1/facturas/mis-facturas")
    suspend fun getMisFacturas(): Response<List<Factura>>

    @GET("maquimu/v1/facturas/mis-facturas/{id}/pdf")
    suspend fun descargarFacturaPdf(@Path("id") id: Long): Response<ResponseBody>

    // ========== Clientes ==========
    @GET("maquimu/v1/clientes/{id}")
    suspend fun getCliente(@Path("id") id: Long): Response<Cliente>

    @PUT("maquimu/v1/clientes/{id}")
    suspend fun actualizarCliente(@Path("id") id: Long, @Body request: ActualizarClienteRequest): Response<Void>
}
