package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.Factura
import okhttp3.ResponseBody

class FacturaRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getMisFacturas(): Result<List<Factura>> {
        return try {
            val response = apiService.getMisFacturas()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al cargar facturas (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun descargarFacturaPdf(facturaId: Long): Result<ResponseBody> {
        return try {
            val response = apiService.descargarFacturaPdf(facturaId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al descargar PDF (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
