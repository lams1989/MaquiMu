package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.ApiService
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.Alquiler
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.math.BigDecimal

class AlquilerRepositoryTest {

    private lateinit var mockApiService: ApiService
    private lateinit var repository: AlquilerRepository

    @Before
    fun setup() {
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        repository = AlquilerRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getMisAlquileres success returns list`() = runTest {
        val alquileres = listOf(
            Alquiler(1, 1, 1, 1, "2026-01-01", "2026-01-15", BigDecimal("500000"), "ACTIVO", null, null, null)
        )
        coEvery { mockApiService.getMisAlquileres() } returns Response.success(alquileres)

        val result = repository.getMisAlquileres()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("ACTIVO", result.getOrNull()?.first()?.estado)
    }

    @Test
    fun `getMisAlquileres error returns failure`() = runTest {
        coEvery { mockApiService.getMisAlquileres() } returns Response.error(
            500, okhttp3.ResponseBody.create(null, "Server Error")
        )

        val result = repository.getMisAlquileres()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("500") == true)
    }

    @Test
    fun `getMisAlquileres exception returns failure with connection error`() = runTest {
        coEvery { mockApiService.getMisAlquileres() } throws RuntimeException("Network unreachable")

        val result = repository.getMisAlquileres()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }

    @Test
    fun `getAlquilerDetalle success returns alquiler`() = runTest {
        val alquiler = Alquiler(1, 1, 1, 1, "2026-01-01", "2026-01-15", BigDecimal("500000"), "ACTIVO", null, null, null)
        coEvery { mockApiService.getAlquilerDetalle(1) } returns Response.success(alquiler)

        val result = repository.getAlquilerDetalle(1)

        assertTrue(result.isSuccess)
        assertEquals(1L, result.getOrNull()?.alquilerId)
    }

    @Test
    fun `getAlquilerDetalle error returns failure`() = runTest {
        coEvery { mockApiService.getAlquilerDetalle(99) } returns Response.error(
            404, okhttp3.ResponseBody.create(null, "Not Found")
        )

        val result = repository.getAlquilerDetalle(99)

        assertTrue(result.isFailure)
    }
}
