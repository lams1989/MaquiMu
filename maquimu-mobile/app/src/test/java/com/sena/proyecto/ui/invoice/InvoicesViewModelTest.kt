package com.sena.proyecto.ui.invoice

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.data.repository.FacturaRepository
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
class InvoicesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var application: Application
    private lateinit var facturaRepository: FacturaRepository
    private lateinit var viewModel: InvoicesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        application = mockk(relaxed = true)
        facturaRepository = mockk(relaxed = true)
        viewModel = InvoicesViewModel(application, facturaRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFacturas success emits Success with list`() = runTest {
        val facturas = listOf(
            Factura(1, 1, 1, "2026-01-15", BigDecimal("500000"), "PENDIENTE"),
            Factura(2, 2, 1, "2026-02-15", BigDecimal("300000"), "PAGADO")
        )
        coEvery { facturaRepository.getMisFacturas() } returns Result.success(facturas)

        viewModel.loadFacturas()

        val state = viewModel.state.value
        assertTrue(state is InvoicesViewModel.InvoicesState.Success)
        assertEquals(2, (state as InvoicesViewModel.InvoicesState.Success).facturas.size)
    }

    @Test
    fun `loadFacturas failure emits Error`() = runTest {
        coEvery { facturaRepository.getMisFacturas() } returns Result.failure(Exception("Error al cargar facturas"))

        viewModel.loadFacturas()

        val state = viewModel.state.value
        assertTrue(state is InvoicesViewModel.InvoicesState.Error)
        assertEquals("Error al cargar facturas", (state as InvoicesViewModel.InvoicesState.Error).message)
    }

    @Test
    fun `clearDownloadState resets to Idle`() {
        viewModel.clearDownloadState()

        val state = viewModel.downloadState.value
        assertTrue(state is InvoicesViewModel.DownloadState.Idle)
    }
}
