package com.sena.proyecto.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        data class Success(val message: String) : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

    private val _registerState = MutableLiveData<RegisterState>(RegisterState.Idle)
    val registerState: LiveData<RegisterState> = _registerState

    fun register(nombre: String, apellido: String, email: String, password: String, identificacion: String) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = authRepository.register(nombre, apellido, email, password, identificacion)
            result.fold(
                onSuccess = { response ->
                    _registerState.value = RegisterState.Success(
                        response.mensaje ?: "Tu cuenta ha sido creada exitosamente. Un operario la revisará en un plazo de 1 a 3 días hábiles."
                    )
                },
                onFailure = { error ->
                    _registerState.value = RegisterState.Error(
                        error.message ?: "Error al registrarse"
                    )
                }
            )
        }
    }

    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(authRepository) as T
        }
    }
}
