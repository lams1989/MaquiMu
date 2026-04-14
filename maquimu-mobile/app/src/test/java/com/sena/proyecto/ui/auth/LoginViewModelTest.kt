package com.sena.proyecto.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sena.proyecto.data.model.AuthResponse
import com.sena.proyecto.data.model.UsuarioResponse
import com.sena.proyecto.data.repository.AuthRepository
import io.mockk.coEvery
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
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var authRepository: AuthRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        authRepository = mockk(relaxed = true)
        viewModel = LoginViewModel(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success with CLIENTE role emits Success`() = runTest {
        val usuario = UsuarioResponse(1, "Test User", "test@test.com", "CLIENTE", "ACTIVO", 1L)
        val authResponse = AuthResponse(token = "test-token", usuario = usuario)
        coEvery { authRepository.login("test@test.com", "pass123") } returns Result.success(authResponse)

        viewModel.login("test@test.com", "pass123")

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Success)
        assertEquals("CLIENTE", (state as LoginViewModel.LoginState.Success).role)
        verify { authRepository.saveSession("test-token", usuario) }
    }

    @Test
    fun `login with OPERARIO role emits Error and logs out`() = runTest {
        val usuario = UsuarioResponse(1, "Operario", "op@test.com", "OPERARIO", "ACTIVO", null)
        val authResponse = AuthResponse(token = "test-token", usuario = usuario)
        coEvery { authRepository.login("op@test.com", "pass123") } returns Result.success(authResponse)

        viewModel.login("op@test.com", "pass123")

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertTrue((state as LoginViewModel.LoginState.Error).message.contains("exclusiva para clientes"))
        verify { authRepository.logout() }
    }

    @Test
    fun `login failure emits Error with message`() = runTest {
        coEvery { authRepository.login(any(), any()) } returns Result.failure(Exception("Credenciales incorrectas"))

        viewModel.login("bad@test.com", "wrong")

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertEquals("Credenciales incorrectas", (state as LoginViewModel.LoginState.Error).message)
    }

    @Test
    fun `requestPasswordReset success emits info message`() = runTest {
        coEvery { authRepository.requestPasswordReset(any()) } returns Result.success(
            com.sena.proyecto.data.model.MensajeResponse("Solicitud enviada")
        )

        viewModel.requestPasswordReset("test@test.com")

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertTrue((state as LoginViewModel.LoginState.Error).message.contains("restablecimiento"))
    }

    @Test
    fun `requestPasswordReset failure emits Error`() = runTest {
        coEvery { authRepository.requestPasswordReset(any()) } returns Result.failure(Exception("Email no encontrado"))

        viewModel.requestPasswordReset("bad@test.com")

        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Error)
        assertEquals("Email no encontrado", (state as LoginViewModel.LoginState.Error).message)
    }

    @Test
    fun `initial state is Idle`() {
        val state = viewModel.loginState.value
        assertTrue(state is LoginViewModel.LoginState.Idle)
    }
}
