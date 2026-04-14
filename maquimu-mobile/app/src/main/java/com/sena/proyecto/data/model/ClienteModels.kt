package com.sena.proyecto.data.model

data class Cliente(
    val clienteId: Long?,
    val nombreCliente: String?,
    val apellido: String?,
    val identificacion: String?,
    val telefono: String?,
    val email: String?,
    val direccion: String?,
    val fechaRegistro: String?
)

data class ActualizarClienteRequest(
    val nombreCliente: String?,
    val apellido: String?,
    val identificacion: String?,
    val telefono: String?,
    val email: String?,
    val direccion: String?
)
