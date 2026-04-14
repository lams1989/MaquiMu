package com.sena.proyecto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.sena.proyecto.R
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.DashboardRepository

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel

    private lateinit var tvWelcome: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var tvAlquileresActivos: TextView
    private lateinit var tvFacturasPendientes: TextView
    private lateinit var cardAlquileres: MaterialCardView
    private lateinit var cardFacturas: MaterialCardView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authRepo = AuthRepository(requireContext())
        val dashRepo = DashboardRepository()
        viewModel = ViewModelProvider(this, DashboardViewModel.Factory(authRepo, dashRepo))
            .get(DashboardViewModel::class.java)

        initViews(view)
        setupClickListeners()
        observeViewModel()
        viewModel.loadDashboard()
    }

    private fun initViews(view: View) {
        tvWelcome = view.findViewById(R.id.tvWelcome)
        tvSubtitle = view.findViewById(R.id.tvSubtitle)
        tvAlquileresActivos = view.findViewById(R.id.tvAlquileresActivos)
        tvFacturasPendientes = view.findViewById(R.id.tvFacturasPendientes)
        cardAlquileres = view.findViewById(R.id.cardAlquileres)
        cardFacturas = view.findViewById(R.id.cardFacturas)
        progressBar = view.findViewById(R.id.progressBar)
        tvError = view.findViewById(R.id.tvError)

        tvWelcome.text = "¡Hola, ${viewModel.userName}!"
    }

    private fun setupClickListeners() {
        cardAlquileres.setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
                ?.selectedItemId = R.id.nav_rentals
        }
        cardFacturas.setOnClickListener {
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
                ?.selectedItemId = R.id.nav_invoices
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DashboardViewModel.DashboardState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
                is DashboardViewModel.DashboardState.Success -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.GONE
                    tvAlquileresActivos.text = state.data.alquileresActivos.toString()
                    tvFacturasPendientes.text = state.data.facturasPendientes.toString()
                }
                is DashboardViewModel.DashboardState.Error -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = state.message
                }
            }
        }
    }
}
