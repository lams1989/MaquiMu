package com.sena.proyecto.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.repository.AlquilerRepository
import io.mockk.coEvery
import io.mockk.mockk
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
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class RentalsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var alquilerRepository: AlquilerRepository
    private lateinit var viewModel: RentalsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        alquilerRepository = mockk(relaxed = true)
        viewModel = RentalsViewModel(alquilerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAlquileres success emits Success with list`() = runTest {
        val alquileres = listOf(
            Alquiler(1, 1, 1, 1, "2026-01-01", "2026-01-15", BigDecimal("500000"), "ACTIVO", null, null, null),
            Alquiler(2, 1, 2, 1, "2026-02-01", "2026-02-15", BigDecimal("300000"), "PENDIENTE", null, null, null)
        )
        coEvery { alquilerRepository.getMisAlquileres() } returns Result.success(alquileres)

        viewModel.loadAlquileres()

        val state = viewModel.state.value
        assertTrue(state is RentalsViewModel.RentalsState.Success)
        assertEquals(2, (state as RentalsViewModel.RentalsState.Success).alquileres.size)
    }

    @Test
    fun `loadAlquileres failure emits Error`() = runTest {
        coEvery { alquilerRepository.getMisAlquileres() } returns Result.failure(Exception("Error al cargar"))

        viewModel.loadAlquileres()

        val state = viewModel.state.value
        assertTrue(state is RentalsViewModel.RentalsState.Error)
        assertEquals("Error al cargar", (state as RentalsViewModel.RentalsState.Error).message)
    }

    @Test
    fun `loadDetalle success emits DetalleState Success`() = runTest {
        val alquiler = Alquiler(1, 1, 1, 1, "2026-01-01", "2026-01-15", BigDecimal("500000"), "ACTIVO", null, null, null)
        coEvery { alquilerRepository.getAlquilerDetalle(1) } returns Result.success(alquiler)

        viewModel.loadDetalle(1)

        val state = viewModel.detalleState.value
        assertTrue(state is RentalsViewModel.DetalleState.Success)
        assertEquals(1L, (state as RentalsViewModel.DetalleState.Success).alquiler.alquilerId)
    }

    @Test
    fun `loadDetalle failure emits DetalleState Error`() = runTest {
        coEvery { alquilerRepository.getAlquilerDetalle(99) } returns Result.failure(Exception("No encontrado"))

        viewModel.loadDetalle(99)

        val state = viewModel.detalleState.value
        assertTrue(state is RentalsViewModel.DetalleState.Error)
    }

    @Test
    fun `clearDetalle resets to Idle`() {
        viewModel.clearDetalle()

        val state = viewModel.detalleState.value
        assertTrue(state is RentalsViewModel.DetalleState.Idle)
    }
}
