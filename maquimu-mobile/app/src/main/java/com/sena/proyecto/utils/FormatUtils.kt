package com.sena.proyecto.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

object FormatUtils {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val inputFormatAlt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun formatCurrency(amount: BigDecimal?): String {
        if (amount == null) return "$0"
        return currencyFormat.format(amount)
    }

    fun formatDate(dateStr: String?): String {
        if (dateStr.isNullOrEmpty()) return "-"
        return try {
            val date = inputFormat.parse(dateStr)
            displayFormat.format(date!!)
        } catch (e: Exception) {
            try {
                val date = inputFormatAlt.parse(dateStr)
                displayFormat.format(date!!)
            } catch (e2: Exception) {
                dateStr
            }
        }
    }

    fun getEstadoAlquilerLabel(estado: String?): String {
        return when (estado) {
            "PENDIENTE" -> "Pendiente"
            "APROBADO" -> "Aprobado"
            "ACTIVO" -> "Activo"
            "FINALIZADO" -> "Finalizado"
            "CANCELADO" -> "Cancelado"
            "RECHAZADO" -> "Rechazado"
            "PENDIENTE_EXTENSION" -> "Extensión Pendiente"
            else -> estado ?: "-"
        }
    }

    fun getEstadoPagoLabel(estado: String?): String {
        return when (estado) {
            "PENDIENTE" -> "Pendiente"
            "PAGADO" -> "Pagado"
            "ANULADO" -> "Anulado"
            else -> estado ?: "-"
        }
    }

    fun getInitials(name: String?): String {
        if (name.isNullOrBlank()) return "?"
        val parts = name.trim().split(" ").filter { it.isNotBlank() }
        return when {
            parts.size >= 2 -> "${parts[0][0]}${parts[1][0]}".uppercase()
            parts.size == 1 -> parts[0].take(2).uppercase()
            else -> "?"
        }
    }
}
