package com.sena.proyecto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.sena.proyecto.R
import com.sena.proyecto.data.repository.AlquilerRepository
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.DashboardRepository
import com.sena.proyecto.data.repository.FacturaRepository
import com.sena.proyecto.data.repository.NotificationRepository
import com.sena.proyecto.utils.FormatUtils

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
    private lateinit var cardNotifications: MaterialCardView
    private lateinit var tvNotificationsTitle: TextView
    private lateinit var tvClearNotifications: TextView
    private lateinit var notificationsList: LinearLayout

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
        val alquilerRepo = AlquilerRepository()
        val facturaRepo = FacturaRepository()
        val notifRepo = NotificationRepository(requireContext())
        viewModel = ViewModelProvider(this, DashboardViewModel.Factory(authRepo, dashRepo, alquilerRepo, facturaRepo, notifRepo))
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
        cardNotifications = view.findViewById(R.id.cardNotifications)
        tvNotificationsTitle = view.findViewById(R.id.tvNotificationsTitle)
        tvClearNotifications = view.findViewById(R.id.tvClearNotifications)
        notificationsList = view.findViewById(R.id.notificationsList)

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
        tvClearNotifications.setOnClickListener {
            viewModel.clearNotifications()
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

        viewModel.notifications.observe(viewLifecycleOwner) { notifications ->
            if (notifications.isNullOrEmpty()) {
                cardNotifications.visibility = View.GONE
            } else {
                cardNotifications.visibility = View.VISIBLE
                tvNotificationsTitle.text = "Notificaciones (${notifications.size})"
                notificationsList.removeAllViews()
                notifications.take(5).forEach { notif ->
                    val itemView = layoutInflater.inflate(R.layout.item_notification, notificationsList, false)
                    itemView.findViewById<TextView>(R.id.tvNotifTitle).text = notif.title
                    itemView.findViewById<TextView>(R.id.tvNotifMessage).text = notif.message
                    itemView.findViewById<TextView>(R.id.tvNotifTime).text = FormatUtils.getTimeAgo(notif.timestamp)
                    val icon = itemView.findViewById<ImageView>(R.id.ivNotifIcon)
                    when (notif.type) {
                        NotificationRepository.TYPE_ALQUILER_ESTADO -> icon.setImageResource(R.drawable.ic_construction)
                        NotificationRepository.TYPE_FACTURA_NUEVA -> icon.setImageResource(R.drawable.ic_receipt)
                    }
                    notificationsList.addView(itemView)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshNotifications()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }
}
