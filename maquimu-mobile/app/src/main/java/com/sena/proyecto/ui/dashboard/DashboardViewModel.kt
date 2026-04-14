package com.sena.proyecto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sena.proyecto.data.model.AppNotification
import com.sena.proyecto.data.model.DashboardClienteResponse
import com.sena.proyecto.data.repository.AlquilerRepository
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.DashboardRepository
import com.sena.proyecto.data.repository.FacturaRepository
import com.sena.proyecto.data.repository.NotificationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val authRepository: AuthRepository,
    private val dashboardRepository: DashboardRepository,
    private val alquilerRepository: AlquilerRepository,
    private val facturaRepository: FacturaRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    sealed class DashboardState {
        object Loading : DashboardState()
        data class Success(val data: DashboardClienteResponse) : DashboardState()
        data class Error(val message: String) : DashboardState()
    }

    private val _state = MutableLiveData<DashboardState>()
    val state: LiveData<DashboardState> = _state

    private val _notifications = MutableLiveData<List<AppNotification>>()
    val notifications: LiveData<List<AppNotification>> = _notifications

    val userName: String get() = authRepository.getUserName() ?: "Usuario"

    private var pollingJob: Job? = null

    fun loadDashboard() {
        _state.value = DashboardState.Loading
        viewModelScope.launch {
            val result = dashboardRepository.getDashboardCliente()
            result.fold(
                onSuccess = { _state.value = DashboardState.Success(it) },
                onFailure = { _state.value = DashboardState.Error(it.message ?: "Error desconocido") }
            )
            checkNotifications()
        }
    }

    private suspend fun checkNotifications() {
        val alquileres = alquilerRepository.getMisAlquileres().getOrDefault(emptyList())
        val facturas = facturaRepository.getMisFacturas().getOrDefault(emptyList())
        val notifications = notificationRepository.detectChanges(alquileres, facturas)
        _notifications.postValue(notifications)
    }

    fun refreshNotifications() {
        viewModelScope.launch {
            checkNotifications()
        }
    }

    fun startPolling() {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (isActive) {
                delay(60_000)
                checkNotifications()
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
    }

    fun clearNotifications() {
        notificationRepository.clearAll()
        _notifications.value = emptyList()
    }

    class Factory(
        private val authRepository: AuthRepository,
        private val dashboardRepository: DashboardRepository,
        private val alquilerRepository: AlquilerRepository,
        private val facturaRepository: FacturaRepository,
        private val notificationRepository: NotificationRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(authRepository, dashboardRepository, alquilerRepository, facturaRepository, notificationRepository) as T
        }
    }
}
