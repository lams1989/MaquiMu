package com.sena.proyecto.ui.rental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.repository.AlquilerRepository
import kotlinx.coroutines.launch

class RentalsViewModel(private val alquilerRepository: AlquilerRepository) : ViewModel() {

    sealed class RentalsState {
        object Loading : RentalsState()
        data class Success(val alquileres: List<Alquiler>) : RentalsState()
        data class Error(val message: String) : RentalsState()
    }

    sealed class DetalleState {
        object Idle : DetalleState()
        object Loading : DetalleState()
        data class Success(val alquiler: Alquiler) : DetalleState()
        data class Error(val message: String) : DetalleState()
    }

    private val _state = MutableLiveData<RentalsState>()
    val state: LiveData<RentalsState> = _state

    private val _detalleState = MutableLiveData<DetalleState>(DetalleState.Idle)
    val detalleState: LiveData<DetalleState> = _detalleState

    fun loadAlquileres() {
        _state.value = RentalsState.Loading
        viewModelScope.launch {
            val result = alquilerRepository.getMisAlquileres()
            result.fold(
                onSuccess = { _state.value = RentalsState.Success(it) },
                onFailure = { _state.value = RentalsState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun loadDetalle(id: Long) {
        _detalleState.value = DetalleState.Loading
        viewModelScope.launch {
            val result = alquilerRepository.getAlquilerDetalle(id)
            result.fold(
                onSuccess = { _detalleState.value = DetalleState.Success(it) },
                onFailure = { _detalleState.value = DetalleState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun clearDetalle() {
        _detalleState.value = DetalleState.Idle
    }

    class Factory(private val alquilerRepository: AlquilerRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RentalsViewModel(alquilerRepository) as T
        }
    }
}
