package com.sena.proyecto.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Cliente
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.ClienteRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    sealed class ProfileState {
        object Loading : ProfileState()
        data class Success(val cliente: Cliente) : ProfileState()
        data class Error(val message: String) : ProfileState()
    }

    sealed class UpdateState {
        object Idle : UpdateState()
        object Loading : UpdateState()
        data class Success(val message: String) : UpdateState()
        data class Error(val message: String) : UpdateState()
    }

    private val _state = MutableLiveData<ProfileState>()
    val state: LiveData<ProfileState> = _state

    private val _updateState = MutableLiveData<UpdateState>(UpdateState.Idle)
    val updateState: LiveData<UpdateState> = _updateState

    val clienteId: Long get() = authRepository.getClienteId()

    fun loadProfile() {
        val id = clienteId
        if (id <= 0) {
            _state.value = ProfileState.Error("No se encontró el ID del cliente")
            return
        }
        _state.value = ProfileState.Loading
        viewModelScope.launch {
            val result = clienteRepository.getCliente(id)
            result.fold(
                onSuccess = { _state.value = ProfileState.Success(it) },
                onFailure = { _state.value = ProfileState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun actualizarCliente(request: ActualizarClienteRequest) {
        val id = clienteId
        if (id <= 0) {
            _updateState.value = UpdateState.Error("No se encontró el ID del cliente")
            return
        }
        _updateState.value = UpdateState.Loading
        viewModelScope.launch {
            val result = clienteRepository.actualizarCliente(id, request)
            result.fold(
                onSuccess = {
                    _updateState.value = UpdateState.Success("Información actualizada correctamente")
                    loadProfile()
                },
                onFailure = { _updateState.value = UpdateState.Error(it.message ?: "Error al actualizar") }
            )
        }
    }

    fun clearUpdateState() {
        _updateState.value = UpdateState.Idle
    }

    class Factory(
        private val authRepository: AuthRepository,
        private val clienteRepository: ClienteRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(authRepository, clienteRepository) as T
        }
    }
}
