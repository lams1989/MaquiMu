package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.Alquiler

class AlquilerRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getMisAlquileres(): Result<List<Alquiler>> {
        return try {
            val response = apiService.getMisAlquileres()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar alquileres (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun getAlquilerDetalle(id: Long): Result<Alquiler> {
        return try {
            val response = apiService.getAlquilerDetalle(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar detalle (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
