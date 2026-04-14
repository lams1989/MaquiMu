package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Cliente

class ClienteRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getCliente(clienteId: Long): Result<Cliente> {
        return try {
            val response = apiService.getCliente(clienteId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar perfil (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun actualizarCliente(clienteId: Long, request: ActualizarClienteRequest): Result<Unit> {
        return try {
            val response = apiService.actualizarCliente(clienteId, request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al actualizar perfil (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
