package com.sena.proyecto.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.model.AppNotification
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.utils.FormatUtils
import java.util.UUID

class NotificationRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    private val gson = Gson()

    fun getNotifications(): List<AppNotification> {
        val json = prefs.getString(KEY_NOTIFICATIONS, null) ?: return emptyList()
        val type = object : TypeToken<List<AppNotification>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveNotifications(notifications: List<AppNotification>) {
        prefs.edit().putString(KEY_NOTIFICATIONS, gson.toJson(notifications)).apply()
    }

    fun detectChanges(
        currentAlquileres: List<Alquiler>,
        currentFacturas: List<Factura>
    ): List<AppNotification> {
        val newNotifications = mutableListOf<AppNotification>()
        val now = System.currentTimeMillis()

        // Detectar cambios de estado en alquileres
        val lastStatesJson = prefs.getString(KEY_ALQUILER_STATES, null)
        val lastStates: Map<String, String> = if (lastStatesJson != null) {
            try {
                val type = object : TypeToken<Map<String, String>>() {}.type
                gson.fromJson(lastStatesJson, type)
            } catch (e: Exception) {
                emptyMap()
            }
        } else emptyMap()

        val currentStates = mutableMapOf<String, String>()
        for (alquiler in currentAlquileres) {
            val id = alquiler.alquilerId ?: continue
            val estado = alquiler.estado ?: continue
            currentStates[id.toString()] = estado

            val lastEstado = lastStates[id.toString()]
            if (lastEstado != null && lastEstado != estado) {
                newNotifications.add(
                    AppNotification(
                        id = UUID.randomUUID().toString(),
                        title = "Alquiler #$id actualizado",
                        message = "Estado cambió de ${FormatUtils.getEstadoAlquilerLabel(lastEstado)} a ${FormatUtils.getEstadoAlquilerLabel(estado)}",
                        type = TYPE_ALQUILER_ESTADO,
                        timestamp = now
                    )
                )
            }
        }
        prefs.edit().putString(KEY_ALQUILER_STATES, gson.toJson(currentStates)).apply()

        // Detectar nuevas facturas
        val lastFacturaIdsJson = prefs.getString(KEY_FACTURA_IDS, null)
        val lastFacturaIds: Set<String> = if (lastFacturaIdsJson != null) {
            try {
                val type = object : TypeToken<Set<String>>() {}.type
                gson.fromJson(lastFacturaIdsJson, type)
            } catch (e: Exception) {
                emptySet()
            }
        } else emptySet()

        val currentFacturaIds = mutableSetOf<String>()
        for (factura in currentFacturas) {
            val id = factura.facturaId ?: continue
            currentFacturaIds.add(id.toString())

            if (lastFacturaIds.isNotEmpty() && !lastFacturaIds.contains(id.toString())) {
                val monto = FormatUtils.formatCurrency(factura.montoTotal)
                newNotifications.add(
                    AppNotification(
                        id = UUID.randomUUID().toString(),
                        title = "Nueva factura #$id",
                        message = "Factura generada por $monto",
                        type = TYPE_FACTURA_NUEVA,
                        timestamp = now
                    )
                )
            }
        }
        prefs.edit().putString(KEY_FACTURA_IDS, gson.toJson(currentFacturaIds)).apply()

        // Combinar con notificaciones existentes (máximo 20)
        if (newNotifications.isNotEmpty()) {
            val existing = getNotifications()
            val merged = (newNotifications + existing).take(MAX_NOTIFICATIONS)
            saveNotifications(merged)
            return merged
        }

        return getNotifications()
    }

    fun markAsRead(notificationId: String) {
        val notifications = getNotifications().map {
            if (it.id == notificationId) it.copy(read = true) else it
        }
        saveNotifications(notifications)
    }

    fun clearAll() {
        prefs.edit().remove(KEY_NOTIFICATIONS).apply()
    }

    companion object {
        private const val PREFS_NAME = "maquimu_notifications"
        private const val KEY_NOTIFICATIONS = "notifications"
        private const val KEY_ALQUILER_STATES = "alquiler_states"
        private const val KEY_FACTURA_IDS = "factura_ids"
        private const val MAX_NOTIFICATIONS = 20
        const val TYPE_ALQUILER_ESTADO = "ALQUILER_ESTADO"
        const val TYPE_FACTURA_NUEVA = "FACTURA_NUEVA"
    }
}
