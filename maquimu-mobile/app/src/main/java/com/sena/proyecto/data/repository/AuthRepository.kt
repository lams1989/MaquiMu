package com.sena.proyecto.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.AuthResponse
import com.sena.proyecto.data.model.ErrorResponse
import com.sena.proyecto.data.model.LoginRequest
import com.sena.proyecto.data.model.MensajeResponse
import com.sena.proyecto.data.model.RegisterRequest
import com.sena.proyecto.data.model.ResetPasswordRequest
import com.sena.proyecto.data.model.UsuarioResponse
import com.sena.proyecto.utils.Constants

class AuthRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREFS_NAME, Context.MODE_PRIVATE
    )

    private val apiService = RetrofitClient.apiService
    private val gson = Gson()

    init {
        // Inicializar RetrofitClient con el provider de token
        RetrofitClient.init { getToken() }
    }

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody, response.code())
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun register(
        nombre: String,
        apellido: String,
        email: String,
        password: String,
        identificacion: String
    ): Result<MensajeResponse> {
        return try {
            val request = RegisterRequest(
                nombre = nombre,
                apellido = apellido.ifEmpty { null },
                email = email,
                password = password,
                identificacion = identificacion
            )
            val response = apiService.register(request)
            if (response.isSuccessful) {
                val body = response.body()
                Result.success(body ?: MensajeResponse("Registro exitoso"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody, response.code())
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    suspend fun requestPasswordReset(email: String): Result<MensajeResponse> {
        return try {
            val response = apiService.solicitarRestablecimiento(ResetPasswordRequest(email))
            if (response.isSuccessful) {
                Result.success(response.body() ?: MensajeResponse("Solicitud enviada"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody, response.code())
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    fun saveSession(token: String, usuario: UsuarioResponse) {
        prefs.edit()
            .putString(Constants.PREF_TOKEN, token)
            .putLong(Constants.PREF_USER_ID, usuario.id)
            .putString(Constants.PREF_USER_NAME, usuario.nombreCompleto)
            .putString(Constants.PREF_USER_EMAIL, usuario.email)
            .putString(Constants.PREF_USER_ROL, usuario.rol)
            .putLong(Constants.PREF_CLIENTE_ID, usuario.clienteId ?: -1L)
            .apply()
    }

    fun getToken(): String? = prefs.getString(Constants.PREF_TOKEN, null)

    fun getUserName(): String? = prefs.getString(Constants.PREF_USER_NAME, null)

    fun getUserEmail(): String? = prefs.getString(Constants.PREF_USER_EMAIL, null)

    fun getUserRole(): String? = prefs.getString(Constants.PREF_USER_ROL, null)

    fun getClienteId(): Long = prefs.getLong(Constants.PREF_CLIENTE_ID, -1L)

    fun isAuthenticated(): Boolean = !getToken().isNullOrEmpty()

    fun logout() {
        prefs.edit().clear().apply()
    }

    private fun parseErrorMessage(errorBody: String?, statusCode: Int): String {
        if (errorBody.isNullOrEmpty()) {
            return when (statusCode) {
                401 -> "Credenciales incorrectas"
                403 -> "Acceso denegado"
                404 -> "Servicio no disponible"
                else -> "Error del servidor ($statusCode)"
            }
        }
        return try {
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            when {
                errorResponse.estado == "PENDIENTE_APROBACION" ->
                    "Tu cuenta aún está pendiente de aprobación. Por favor espera de 1 a 3 días hábiles."
                errorResponse.estado == "RECHAZADO" ->
                    "Tu cuenta ha sido rechazada. ${errorResponse.motivoRechazo ?: "Contacta al administrador para más información."}"
                errorResponse.estado == "RESTABLECER" ->
                    "Tu contraseña está en proceso de restablecimiento. Contacta al operario."
                !errorResponse.message.isNullOrEmpty() -> errorResponse.message
                else -> "Error del servidor ($statusCode)"
            }
        } catch (e: Exception) {
            // Intentar parsear como mensaje simple
            try {
                val mensajeResponse = gson.fromJson(errorBody, MensajeResponse::class.java)
                mensajeResponse.mensaje ?: "Error del servidor ($statusCode)"
            } catch (e2: Exception) {
                "Error del servidor ($statusCode)"
            }
        }
    }
}
