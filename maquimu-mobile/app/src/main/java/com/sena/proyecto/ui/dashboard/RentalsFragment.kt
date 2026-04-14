package com.sena.proyecto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sena.proyecto.R
import com.sena.proyecto.data.model.Alquiler
import com.sena.proyecto.data.repository.AlquilerRepository
import com.sena.proyecto.utils.FormatUtils

class RentalsFragment : Fragment() {

    private lateinit var viewModel: RentalsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var tvError: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: RentalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rentals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, RentalsViewModel.Factory(AlquilerRepository()))
            .get(RentalsViewModel::class.java)

        initViews(view)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadAlquileres()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.rentalsRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvSubtitle = view.findViewById(R.id.tvSubtitle)
        tvError = view.findViewById(R.id.tvError)
        tvEmpty = view.findViewById(R.id.tvEmpty)
    }

    private fun setupRecyclerView() {
        adapter = RentalAdapter { alquiler ->
            alquiler.alquilerId?.let { showDetalleDialog(alquiler) }
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RentalsViewModel.RentalsState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    tvEmpty.visibility = View.GONE
                }
                is RentalsViewModel.RentalsState.Success -> {
                    progressBar.visibility = View.GONE
                    if (state.alquileres.isEmpty()) {
                        tvEmpty.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        tvSubtitle.text = "Sin alquileres registrados"
                    } else {
                        tvEmpty.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.updateData(state.alquileres)
                        tvSubtitle.text = "${state.alquileres.size} alquiler(es) registrado(s)"
                    }
                }
                is RentalsViewModel.RentalsState.Error -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = state.message
                }
            }
        }
    }

    private fun showDetalleDialog(alquiler: Alquiler) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_rental_detail, null)

        dialogView.findViewById<TextView>(R.id.tvDetalleId).text =
            "Alquiler #${alquiler.alquilerId}"
        dialogView.findViewById<TextView>(R.id.tvDetalleMaquinaria).text =
            "Maquinaria ID: ${alquiler.maquinariaId}"
        dialogView.findViewById<TextView>(R.id.tvDetalleEstado).text =
            FormatUtils.getEstadoAlquilerLabel(alquiler.estado)
        dialogView.findViewById<TextView>(R.id.tvDetalleFechaInicio).text =
            FormatUtils.formatDate(alquiler.fechaInicio)
        dialogView.findViewById<TextView>(R.id.tvDetalleFechaFin).text =
            FormatUtils.formatDate(alquiler.fechaFin)
        dialogView.findViewById<TextView>(R.id.tvDetalleCosto).text =
            FormatUtils.formatCurrency(alquiler.costoTotal)

        val layoutMotivo = dialogView.findViewById<View>(R.id.layoutMotivo)
        val tvMotivo = dialogView.findViewById<TextView>(R.id.tvDetalleMotivo)
        if (!alquiler.motivoRechazo.isNullOrEmpty()) {
            tvMotivo.text = alquiler.motivoRechazo
            layoutMotivo.visibility = View.VISIBLE
        } else {
            layoutMotivo.visibility = View.GONE
        }

        val layoutExtension = dialogView.findViewById<View>(R.id.layoutExtension)
        val tvCostoAdicional = dialogView.findViewById<TextView>(R.id.tvDetalleCostoAdicional)
        val tvFechaFinSolicitada = dialogView.findViewById<TextView>(R.id.tvDetalleFechaFinSolicitada)
        if (alquiler.costoAdicional != null && alquiler.costoAdicional.signum() > 0) {
            tvCostoAdicional.text = FormatUtils.formatCurrency(alquiler.costoAdicional)
            tvFechaFinSolicitada.text = FormatUtils.formatDate(alquiler.fechaFinSolicitada)
            layoutExtension.visibility = View.VISIBLE
        } else {
            layoutExtension.visibility = View.GONE
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Cerrar", null)
            .show()
    }
}
