package com.sena.proyecto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.model.DashboardClienteResponse
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.DashboardRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val authRepository: AuthRepository,
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    sealed class DashboardState {
        object Loading : DashboardState()
        data class Success(val data: DashboardClienteResponse) : DashboardState()
        data class Error(val message: String) : DashboardState()
    }

    private val _state = MutableLiveData<DashboardState>()
    val state: LiveData<DashboardState> = _state

    val userName: String get() = authRepository.getUserName() ?: "Usuario"

    fun loadDashboard() {
        _state.value = DashboardState.Loading
        viewModelScope.launch {
            val result = dashboardRepository.getDashboardCliente()
            result.fold(
                onSuccess = { _state.value = DashboardState.Success(it) },
                onFailure = { _state.value = DashboardState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    class Factory(
        private val authRepository: AuthRepository,
        private val dashboardRepository: DashboardRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(authRepository, dashboardRepository) as T
        }
    }
}
