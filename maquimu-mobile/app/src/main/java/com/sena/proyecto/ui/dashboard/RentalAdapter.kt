package com.sena.proyecto.ui.dashboard

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sena.proyecto.R
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.utils.FormatUtils

class RentalAdapter(
    private var alquileres: List<Alquiler> = emptyList(),
    private val onItemClick: (Alquiler) -> Unit
) : RecyclerView.Adapter<RentalAdapter.ViewHolder>() {

    fun updateData(newData: List<Alquiler>) {
        alquileres = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rental, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alquileres[position])
    }

    override fun getItemCount() = alquileres.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAlquilerId: TextView = itemView.findViewById(R.id.tvAlquilerId)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        private val tvMaquinaria: TextView = itemView.findViewById(R.id.tvMaquinaria)
        private val tvFechaInicio: TextView = itemView.findViewById(R.id.tvFechaInicio)
        private val tvFechaFin: TextView = itemView.findViewById(R.id.tvFechaFin)
        private val tvCosto: TextView = itemView.findViewById(R.id.tvCosto)

        fun bind(alquiler: Alquiler) {
            tvAlquilerId.text = "Alquiler #${alquiler.alquilerId}"
            tvMaquinaria.text = "Maquinaria ID: ${alquiler.maquinariaId}"
            tvFechaInicio.text = "Inicio: ${FormatUtils.formatDate(alquiler.fechaInicio)}"
            tvFechaFin.text = "Fin: ${FormatUtils.formatDate(alquiler.fechaFin)}"
            tvCosto.text = FormatUtils.formatCurrency(alquiler.costoTotal)

            tvEstado.text = FormatUtils.getEstadoAlquilerLabel(alquiler.estado)
            val bgDrawable = GradientDrawable().apply {
                cornerRadius = 24f
                setColor(ContextCompat.getColor(itemView.context, getEstadoColor(alquiler.estado)))
            }
            tvEstado.background = bgDrawable

            itemView.setOnClickListener { onItemClick(alquiler) }
        }

        private fun getEstadoColor(estado: String?): Int {
            return when (estado) {
                "ACTIVO" -> R.color.statusActive
                "PENDIENTE", "PENDIENTE_EXTENSION" -> R.color.statusPending
                "APROBADO" -> R.color.colorPrimary
                "FINALIZADO" -> R.color.statusCompleted
                "RECHAZADO", "CANCELADO" -> R.color.statusOverdue
                else -> R.color.textColorSecondary
            }
        }
    }
}
