package com.sena.proyecto.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.sena.proyecto.data.api.ApiService
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.AuthResponse
import com.sena.proyecto.data.model.LoginRequest
import com.sena.proyecto.data.model.MensajeResponse
import com.sena.proyecto.data.model.UsuarioResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthRepositoryTest {

    private lateinit var mockApiService: ApiService
    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor
    private lateinit var mockContext: Context
    private lateinit var repository: AuthRepository

    @Before
    fun setup() {
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        every { RetrofitClient.init(any()) } returns Unit

        mockEditor = mockk(relaxed = true)
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.putLong(any(), any()) } returns mockEditor
        every { mockEditor.clear() } returns mockEditor

        mockPrefs = mockk(relaxed = true)
        every { mockPrefs.edit() } returns mockEditor

        mockContext = mockk()
        every { mockContext.getSharedPreferences(any(), any()) } returns mockPrefs

        repository = AuthRepository(mockContext)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `login success returns AuthResponse`() = runTest {
        val usuario = UsuarioResponse(1, "Test User", "test@test.com", "CLIENTE", "ACTIVO", 1L)
        val authResponse = AuthResponse(token = "jwt-token", usuario = usuario)
        coEvery { mockApiService.login(any()) } returns Response.success(authResponse)

        val result = repository.login("test@test.com", "pass123")

        assertTrue(result.isSuccess)
        assertEquals("jwt-token", result.getOrNull()?.token)
        assertEquals("Test User", result.getOrNull()?.usuario?.nombreCompleto)
    }

    @Test
    fun `login error returns failure with message`() = runTest {
        coEvery { mockApiService.login(any()) } returns Response.error(
            401, ResponseBody.create(null, "{\"message\":\"Credenciales incorrectas\"}")
        )

        val result = repository.login("bad@test.com", "wrong")

        assertTrue(result.isFailure)
    }

    @Test
    fun `login network error returns connection failure`() = runTest {
        coEvery { mockApiService.login(any()) } throws RuntimeException("Network unreachable")

        val result = repository.login("test@test.com", "pass123")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }

    @Test
    fun `register success returns message`() = runTest {
        coEvery { mockApiService.register(any()) } returns Response.success(MensajeResponse("Registro exitoso"))

        val result = repository.register("Juan", "Pérez", "juan@test.com", "pass123", "123456")

        assertTrue(result.isSuccess)
        assertEquals("Registro exitoso", result.getOrNull()?.mensaje)
    }

    @Test
    fun `register error returns failure`() = runTest {
        coEvery { mockApiService.register(any()) } returns Response.error(
            409, ResponseBody.create(null, "{\"message\":\"Email ya registrado\"}")
        )

        val result = repository.register("Juan", "Pérez", "existing@test.com", "pass123", "123456")

        assertTrue(result.isFailure)
    }

    @Test
    fun `saveSession stores all values`() {
        val usuario = UsuarioResponse(1, "Test User", "test@test.com", "CLIENTE", "ACTIVO", 5L)

        repository.saveSession("test-token", usuario)

        verify { mockEditor.putString("jwt_token", "test-token") }
        verify { mockEditor.putLong("user_id", 1L) }
        verify { mockEditor.putString("user_name", "Test User") }
        verify { mockEditor.putString("user_email", "test@test.com") }
        verify { mockEditor.putString("user_rol", "CLIENTE") }
        verify { mockEditor.putLong("cliente_id", 5L) }
        verify { mockEditor.apply() }
    }

    @Test
    fun `getToken returns stored token`() {
        every { mockPrefs.getString("jwt_token", null) } returns "stored-token"

        assertEquals("stored-token", repository.getToken())
    }

    @Test
    fun `isAuthenticated returns true when token exists`() {
        every { mockPrefs.getString("jwt_token", null) } returns "valid-token"

        assertTrue(repository.isAuthenticated())
    }

    @Test
    fun `logout clears preferences`() {
        repository.logout()

        verify { mockEditor.clear() }
        verify { mockEditor.apply() }
    }
}
