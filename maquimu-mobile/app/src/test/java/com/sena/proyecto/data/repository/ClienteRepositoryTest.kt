package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.ApiService
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Cliente
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ClienteRepositoryTest {

    private lateinit var mockApiService: ApiService
    private lateinit var repository: ClienteRepository

    @Before
    fun setup() {
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        repository = ClienteRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCliente success returns cliente`() = runTest {
        val cliente = Cliente(1, "Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 1", "2026-01-01")
        coEvery { mockApiService.getCliente(1) } returns Response.success(cliente)

        val result = repository.getCliente(1)

        assertTrue(result.isSuccess)
        assertEquals("Juan", result.getOrNull()?.nombreCliente)
    }

    @Test
    fun `getCliente error returns failure`() = runTest {
        coEvery { mockApiService.getCliente(99) } returns Response.error(
            404, ResponseBody.create(null, "Not Found")
        )

        val result = repository.getCliente(99)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("404") == true)
    }

    @Test
    fun `getCliente exception returns failure`() = runTest {
        coEvery { mockApiService.getCliente(any()) } throws RuntimeException("Timeout")

        val result = repository.getCliente(1)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }

    @Test
    fun `actualizarCliente success returns Unit`() = runTest {
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")
        coEvery { mockApiService.actualizarCliente(1, request) } returns Response.success(null)

        val result = repository.actualizarCliente(1, request)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `actualizarCliente error returns failure`() = runTest {
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")
        coEvery { mockApiService.actualizarCliente(1, request) } returns Response.error(
            400, ResponseBody.create(null, "Bad Request")
        )

        val result = repository.actualizarCliente(1, request)

        assertTrue(result.isFailure)
    }

    @Test
    fun `actualizarCliente exception returns failure`() = runTest {
        val request = ActualizarClienteRequest("Juan", "Pérez", "123456", "3001234567", "juan@test.com", "Calle 2")
        coEvery { mockApiService.actualizarCliente(any(), any()) } throws RuntimeException("Network error")

        val result = repository.actualizarCliente(1, request)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }
}
