package com.sena.proyecto.data.model

import java.math.BigDecimal

data class Factura(
    val facturaId: Long?,
    val alquilerId: Long?,
    val clienteId: Long?,
    val fechaEmision: String?,
    val montoTotal: BigDecimal?,
    val estadoPago: String?
)
