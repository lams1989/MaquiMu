package com.sena.proyecto.data.model

import java.math.BigDecimal

data class Alquiler(
    val alquilerId: Long?,
    val clienteId: Long?,
    val maquinariaId: Long?,
    val usuarioId: Long?,
    val fechaInicio: String?,
    val fechaFin: String?,
    val costoTotal: BigDecimal?,
    val estado: String?,
    val motivoRechazo: String?,
    val fechaFinSolicitada: String?,
    val costoAdicional: BigDecimal?
)
