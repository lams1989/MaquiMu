package com.sena.proyecto.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val role: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = { response ->
                    if (response.usuario.rol == "OPERARIO") {
                        authRepository.logout()
                        _loginState.value = LoginState.Error(
                            "La app móvil es exclusiva para clientes. Si eres operario, utiliza la plataforma web."
                        )
                    } else {
                        authRepository.saveSession(response.token, response.usuario)
                        _loginState.value = LoginState.Success(response.usuario.rol)
                    }
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(
                        error.message ?: "Error al iniciar sesión"
                    )
                }
            )
        }
    }

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            val result = authRepository.requestPasswordReset(email)
            result.fold(
                onSuccess = {
                    _loginState.value = LoginState.Error(
                        "Se ha enviado una solicitud de restablecimiento. Un operario te asignará una contraseña temporal en un plazo de 1 a 3 días hábiles."
                    )
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(
                        error.message ?: "Error al solicitar restablecimiento"
                    )
                }
            )
        }
    }

    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(authRepository) as T
        }
    }
}
