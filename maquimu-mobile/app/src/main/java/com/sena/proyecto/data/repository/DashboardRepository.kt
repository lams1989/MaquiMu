package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.DashboardClienteResponse

class DashboardRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getDashboardCliente(): Result<DashboardClienteResponse> {
        return try {
            val response = apiService.getDashboardCliente()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar dashboard (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
