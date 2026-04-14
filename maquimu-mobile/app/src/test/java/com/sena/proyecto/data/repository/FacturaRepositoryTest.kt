package com.sena.proyecto.data.repository

import com.sena.proyecto.data.api.ApiService
import com.sena.proyecto.data.api.RetrofitClient
import com.sena.proyecto.data.model.Factura
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
import java.math.BigDecimal

class FacturaRepositoryTest {

    private lateinit var mockApiService: ApiService
    private lateinit var repository: FacturaRepository

    @Before
    fun setup() {
        mockkObject(RetrofitClient)
        mockApiService = mockk()
        every { RetrofitClient.apiService } returns mockApiService
        repository = FacturaRepository()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getMisFacturas success returns list`() = runTest {
        val facturas = listOf(
            Factura(1, 1, 1, "2026-01-15", BigDecimal("500000"), "PENDIENTE"),
            Factura(2, 2, 1, "2026-02-15", BigDecimal("300000"), "PAGADO")
        )
        coEvery { mockApiService.getMisFacturas() } returns Response.success(facturas)

        val result = repository.getMisFacturas()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getMisFacturas error returns failure`() = runTest {
        coEvery { mockApiService.getMisFacturas() } returns Response.error(
            500, ResponseBody.create(null, "Server Error")
        )

        val result = repository.getMisFacturas()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("500") == true)
    }

    @Test
    fun `getMisFacturas exception returns failure`() = runTest {
        coEvery { mockApiService.getMisFacturas() } throws RuntimeException("Timeout")

        val result = repository.getMisFacturas()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("conexión") == true)
    }

    @Test
    fun `descargarFacturaPdf success returns body`() = runTest {
        val mockBody = ResponseBody.create(null, "PDF content")
        coEvery { mockApiService.descargarFacturaPdf(1) } returns Response.success(mockBody)

        val result = repository.descargarFacturaPdf(1)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `descargarFacturaPdf error returns failure`() = runTest {
        coEvery { mockApiService.descargarFacturaPdf(99) } returns Response.error(
            404, ResponseBody.create(null, "Not Found")
        )

        val result = repository.descargarFacturaPdf(99)

        assertTrue(result.isFailure)
    }
}
