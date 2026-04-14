package com.sena.proyecto.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.MensajeResponse
import com.sena.proyecto.data.repository.AuthRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        authRepository = mockk(relaxed = true)
        viewModel = RegisterViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `register success emits Success with message`() = runTest {
        coEvery {
            authRepository.register(any(), any(), any(), any(), any())
        } returns Result.success(MensajeResponse("Registro exitoso. Pendiente de aprobación."))

        viewModel.register("Juan", "Pérez", "juan@test.com", "pass123", "123456")

        val state = viewModel.registerState.value
        assertTrue(state is RegisterViewModel.RegisterState.Success)
        assertTrue((state as RegisterViewModel.RegisterState.Success).message.contains("Registro exitoso"))
    }

    @Test
    fun `register success with null message uses default`() = runTest {
        coEvery {
            authRepository.register(any(), any(), any(), any(), any())
        } returns Result.success(MensajeResponse(null))

        viewModel.register("Juan", "Pérez", "juan@test.com", "pass123", "123456")

        val state = viewModel.registerState.value
        assertTrue(state is RegisterViewModel.RegisterState.Success)
        assertTrue((state as RegisterViewModel.RegisterState.Success).message.contains("operario"))
    }

    @Test
    fun `register failure emits Error`() = runTest {
        coEvery {
            authRepository.register(any(), any(), any(), any(), any())
        } returns Result.failure(Exception("El email ya está registrado"))

        viewModel.register("Juan", "Pérez", "existing@test.com", "pass123", "123456")

        val state = viewModel.registerState.value
        assertTrue(state is RegisterViewModel.RegisterState.Error)
        assertEquals("El email ya está registrado", (state as RegisterViewModel.RegisterState.Error).message)
    }

    @Test
    fun `initial state is Idle`() {
        val state = viewModel.registerState.value
        assertTrue(state is RegisterViewModel.RegisterState.Idle)
    }
}
