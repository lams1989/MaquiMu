package com.sena.proyecto.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Cliente
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.ClienteRepository
import io.mockk.coEvery
import io.mockk.every
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

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var clienteRepository: ClienteRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        authRepository = mockk(relaxed = true)
        clienteRepository = mockk(relaxed = true)
        every { authRepository.getClienteId() } returns 1L
        viewModel = ProfileViewModel(authRepository, clienteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile success emits Success`() = runTest {
        val cliente = Cliente(1, "Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 1", "2026-01-01")
        coEvery { clienteRepository.getCliente(1) } returns Result.success(cliente)

        viewModel.loadProfile()

        val state = viewModel.state.value
        assertTrue(state is ProfileViewModel.ProfileState.Success)
        assertEquals("Juan", (state as ProfileViewModel.ProfileState.Success).cliente.nombreCliente)
    }

    @Test
    fun `loadProfile failure emits Error`() = runTest {
        coEvery { clienteRepository.getCliente(1) } returns Result.failure(Exception("Error al cargar perfil"))

        viewModel.loadProfile()

        val state = viewModel.state.value
        assertTrue(state is ProfileViewModel.ProfileState.Error)
        assertEquals("Error al cargar perfil", (state as ProfileViewModel.ProfileState.Error).message)
    }

    @Test
    fun `loadProfile with invalid clienteId emits Error`() {
        every { authRepository.getClienteId() } returns -1L
        val vm = ProfileViewModel(authRepository, clienteRepository)

        vm.loadProfile()

        val state = vm.state.value
        assertTrue(state is ProfileViewModel.ProfileState.Error)
        assertTrue((state as ProfileViewModel.ProfileState.Error).message.contains("ID del cliente"))
    }

    @Test
    fun `actualizarCliente success emits UpdateState Success`() = runTest {
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")
        val cliente = Cliente(1, "Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2", "2026-01-01")
        coEvery { clienteRepository.actualizarCliente(1, request) } returns Result.success(Unit)
        coEvery { clienteRepository.getCliente(1) } returns Result.success(cliente)

        viewModel.actualizarCliente(request)

        val state = viewModel.updateState.value
        assertTrue(state is ProfileViewModel.UpdateState.Success)
    }

    @Test
    fun `actualizarCliente failure emits UpdateState Error`() = runTest {
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")
        coEvery { clienteRepository.actualizarCliente(1, request) } returns Result.failure(Exception("Error al actualizar"))

        viewModel.actualizarCliente(request)

        val state = viewModel.updateState.value
        assertTrue(state is ProfileViewModel.UpdateState.Error)
        assertEquals("Error al actualizar", (state as ProfileViewModel.UpdateState.Error).message)
    }

    @Test
    fun `actualizarCliente with invalid clienteId emits Error`() {
        every { authRepository.getClienteId() } returns 0L
        val vm = ProfileViewModel(authRepository, clienteRepository)
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")

        vm.actualizarCliente(request)

        val state = vm.updateState.value
        assertTrue(state is ProfileViewModel.UpdateState.Error)
    }

    @Test
    fun `clearUpdateState resets to Idle`() {
        viewModel.clearUpdateState()

        val state = viewModel.updateState.value
        assertTrue(state is ProfileViewModel.UpdateState.Idle)
    }
}
