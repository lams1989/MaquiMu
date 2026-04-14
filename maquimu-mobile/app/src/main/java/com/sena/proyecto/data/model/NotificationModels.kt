package com.sena.proyecto.data.model

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val type: String,
    val timestamp: Long,
    val read: Boolean = false
)
