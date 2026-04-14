package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.ApiService
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.DashboardClienteResponse
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

class DashboardRepositoryTest {

    private lateinit var mockApiService: ApiService
    private lateinit var repository: DashboardRepository

    @Before
    fun setup() {
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        repository = DashboardRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getDashboardCliente success returns data`() = runTest {
        val dashData = DashboardClienteResponse(alquileresActivos = 5, facturasPendientes = 3)
        coEvery { mockApiService.getDashboardCliente() } returns Response.success(dashData)

        val result = repository.getDashboardCliente()

        assertTrue(result.isSuccess)
        assertEquals(5L, result.getOrNull()?.alquileresActivos)
        assertEquals(3L, result.getOrNull()?.facturasPendientes)
    }

    @Test
    fun `getDashboardCliente error returns failure`() = runTest {
        coEvery { mockApiService.getDashboardCliente() } returns Response.error(
            401, ResponseBody.create(null, "Unauthorized")
        )

        val result = repository.getDashboardCliente()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("401") == true)
    }

    @Test
    fun `getDashboardCliente exception returns failure`() = runTest {
        coEvery { mockApiService.getDashboardCliente() } throws RuntimeException("Connection refused")

        val result = repository.getDashboardCliente()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }
}
