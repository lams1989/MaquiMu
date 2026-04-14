package com.sena.proyecto.ui.dashboard

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sena.proyecto.R
import com.sena.proyecto.data.model.Factura
import com.sena.proyecto.utils.FormatUtils

class InvoiceAdapter(
    private var facturas: List<Factura> = emptyList(),
    private val onItemClick: (Factura) -> Unit,
    private val onDownloadClick: (Long) -> Unit
) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    fun updateData(newData: List<Factura>) {
        facturas = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(facturas[position])
    }

    override fun getItemCount() = facturas.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFacturaId: TextView = itemView.findViewById(R.id.tvFacturaId)
        private val tvEstadoPago: TextView = itemView.findViewById(R.id.tvEstadoPago)
        private val tvFechaEmision: TextView = itemView.findViewById(R.id.tvFechaEmision)
        private val tvAlquilerRef: TextView = itemView.findViewById(R.id.tvAlquilerRef)
        private val tvMonto: TextView = itemView.findViewById(R.id.tvMonto)
        private val btnDescargarPdf: MaterialButton = itemView.findViewById(R.id.btnDescargarPdf)

        fun bind(factura: Factura) {
            tvFacturaId.text = "Factura #${factura.facturaId}"
            tvFechaEmision.text = "Emisión: ${FormatUtils.formatDate(factura.fechaEmision)}"
            tvAlquilerRef.text = "Alquiler #${factura.alquilerId}"
            tvMonto.text = FormatUtils.formatCurrency(factura.montoTotal)

            tvEstadoPago.text = FormatUtils.getEstadoPagoLabel(factura.estadoPago)
            val bgDrawable = GradientDrawable().apply {
                cornerRadius = 24f
                setColor(ContextCompat.getColor(itemView.context, getEstadoColor(factura.estadoPago)))
            }
            tvEstadoPago.background = bgDrawable

            itemView.setOnClickListener { onItemClick(factura) }
            btnDescargarPdf.setOnClickListener {
                factura.facturaId?.let { id -> onDownloadClick(id) }
            }
        }

        private fun getEstadoColor(estado: String?): Int {
            return when (estado) {
                "PAGADO" -> R.color.statusActive
                "PENDIENTE" -> R.color.statusPending
                "ANULADO" -> R.color.statusOverdue
                else -> R.color.textColorSecondary
            }
        }
    }
}
