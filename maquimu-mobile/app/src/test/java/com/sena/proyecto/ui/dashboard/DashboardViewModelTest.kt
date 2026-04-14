package com.sena.proyecto.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.model.AppNotification
import com.sena.proyecto.data.model.DashboardClienteResponse
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.data.repository.AlquilerRepository
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.DashboardRepository
import com.sena.proyecto.data.repository.FacturaRepository
import com.sena.proyecto.data.repository.NotificationRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var dashboardRepository: DashboardRepository
    private lateinit var alquilerRepository: AlquilerRepository
    private lateinit var facturaRepository: FacturaRepository
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        authRepository = mockk(relaxed = true)
        dashboardRepository = mockk(relaxed = true)
        alquilerRepository = mockk(relaxed = true)
        facturaRepository = mockk(relaxed = true)
        notificationRepository = mockk(relaxed = true)

        every { authRepository.getUserName() } returns "Juan Pérez"
        coEvery { alquilerRepository.getMisAlquileres() } returns Result.success(emptyList())
        coEvery { facturaRepository.getMisFacturas() } returns Result.success(emptyList())
        every { notificationRepository.detectChanges(any(), any()) } returns emptyList()

        viewModel = DashboardViewModel(authRepository, dashboardRepository, alquilerRepository, facturaRepository, notificationRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDashboard success emits Success state`() = runTest {
        val dashData = DashboardClienteResponse(alquileresActivos = 3, facturasPendientes = 2)
        coEvery { dashboardRepository.getDashboardCliente() } returns Result.success(dashData)

        viewModel.loadDashboard()

        val state = viewModel.state.value
        assertTrue(state is DashboardViewModel.DashboardState.Success)
        assertEquals(3L, (state as DashboardViewModel.DashboardState.Success).data.alquileresActivos)
        assertEquals(2L, state.data.facturasPendientes)
    }

    @Test
    fun `loadDashboard failure emits Error state`() = runTest {
        coEvery { dashboardRepository.getDashboardCliente() } returns Result.failure(Exception("Error de conexión"))

        viewModel.loadDashboard()

        val state = viewModel.state.value
        assertTrue(state is DashboardViewModel.DashboardState.Error)
        assertEquals("Error de conexión", (state as DashboardViewModel.DashboardState.Error).message)
    }

    @Test
    fun `userName returns name from authRepository`() {
        assertEquals("Juan Pérez", viewModel.userName)
    }

    @Test
    fun `userName returns default when null`() {
        every { authRepository.getUserName() } returns null
        val vm = DashboardViewModel(authRepository, dashboardRepository, alquilerRepository, facturaRepository, notificationRepository)
        assertEquals("Usuario", vm.userName)
    }

    @Test
    fun `loadDashboard triggers notification check`() = runTest {
        val dashData = DashboardClienteResponse(alquileresActivos = 1, facturasPendientes = 0)
        coEvery { dashboardRepository.getDashboardCliente() } returns Result.success(dashData)

        val notifs = listOf(
            AppNotification("1", "Test", "msg", "ALQUILER_ESTADO", System.currentTimeMillis())
        )
        every { notificationRepository.detectChanges(any(), any()) } returns notifs

        viewModel.loadDashboard()

        val notifications = viewModel.notifications.value
        assertEquals(1, notifications?.size)
        assertEquals("Test", notifications?.first()?.title)
    }

    @Test
    fun `clearNotifications clears list`() {
        viewModel.clearNotifications()

        verify { notificationRepository.clearAll() }
        assertTrue(viewModel.notifications.value.isNullOrEmpty())
    }
}
