package com.sena.proyecto.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.sena.proyecto.R
import com.sena.proyecto.data.model.ActualizarClienteRequest
import com.sena.proyecto.data.model.Cliente
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.data.repository.ClienteRepository
import com.sena.proyecto.ui.dashboard.DashboardActivity
import com.sena.proyecto.utils.FormatUtils
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    private lateinit var tvInitials: TextView
    private lateinit var tvNombreCompleto: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvIdentificacion: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvFechaRegistro: TextView
    private lateinit var cardEditProfile: MaterialCardView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView

    private var currentCliente: Cliente? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authRepo = AuthRepository(requireContext())
        val clienteRepo = ClienteRepository()
        viewModel = ViewModelProvider(this, ProfileViewModel.Factory(authRepo, clienteRepo))
            .get(ProfileViewModel::class.java)

        initViews(view)
        observeViewModel()
        viewModel.loadProfile()
    }

    private fun initViews(view: View) {
        tvInitials = view.findViewById(R.id.tvInitials)
        tvNombreCompleto = view.findViewById(R.id.tvNombreCompleto)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvEstado = view.findViewById(R.id.tvEstado)
        tvIdentificacion = view.findViewById(R.id.tvIdentificacion)
        tvTelefono = view.findViewById(R.id.tvTelefono)
        tvDireccion = view.findViewById(R.id.tvDireccion)
        tvFechaRegistro = view.findViewById(R.id.tvFechaRegistro)
        cardEditProfile = view.findViewById(R.id.cardEditProfile)
        progressBar = view.findViewById(R.id.progressBar)
        tvError = view.findViewById(R.id.tvError)

        cardEditProfile.setOnClickListener { showEditDialog() }

        view.findViewById<View>(R.id.logoutButton).setOnClickListener {
            (activity as? DashboardActivity)?.logout()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileViewModel.ProfileState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
                is ProfileViewModel.ProfileState.Success -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.GONE
                    currentCliente = state.cliente
                    bindProfile(state.cliente)
                }
                is ProfileViewModel.ProfileState.Error -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = state.message
                }
            }
        }

        viewModel.updateState.observe(viewLifecycleOwner) { uState ->
            when (uState) {
                is ProfileViewModel.UpdateState.Success -> {
                    showSnackbar(uState.message)
                    viewModel.clearUpdateState()
                }
                is ProfileViewModel.UpdateState.Error -> {
                    showSnackbar(uState.message)
                    viewModel.clearUpdateState()
                }
                else -> { /* Idle / Loading */ }
            }
        }
    }

    private fun bindProfile(cliente: Cliente) {
        val nombre = buildString {
            append(cliente.nombreCliente ?: "")
            if (!cliente.apellido.isNullOrBlank()) {
                append(" ")
                append(cliente.apellido)
            }
        }
        tvNombreCompleto.text = nombre
        tvInitials.text = FormatUtils.getInitials(nombre)
        tvEmail.text = cliente.email ?: "-"
        tvEstado.text = "Cliente activo"
        tvIdentificacion.text = cliente.identificacion ?: "-"
        tvTelefono.text = cliente.telefono ?: "No registrado"
        tvDireccion.text = cliente.direccion ?: "No registrada"
        tvFechaRegistro.text = "Registrado: ${FormatUtils.formatDate(cliente.fechaRegistro)}"
    }

    private fun showEditDialog() {
        val cliente = currentCliente ?: return

        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_profile, null)

        val editNombre = dialogView.findViewById<TextInputEditText>(R.id.editNombre)
        val editApellido = dialogView.findViewById<TextInputEditText>(R.id.editApellido)
        val editEmail = dialogView.findViewById<TextInputEditText>(R.id.editEmail)
        val editIdentificacion = dialogView.findViewById<TextInputEditText>(R.id.editIdentificacion)
        val editTelefono = dialogView.findViewById<TextInputEditText>(R.id.editTelefono)
        val editDireccion = dialogView.findViewById<TextInputEditText>(R.id.editDireccion)

        editNombre.setText(cliente.nombreCliente ?: "")
        editApellido.setText(cliente.apellido ?: "")
        editEmail.setText(cliente.email ?: "")
        editIdentificacion.setText(cliente.identificacion ?: "")
        editTelefono.setText(cliente.telefono ?: "")
        editDireccion.setText(cliente.direccion ?: "")

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Perfil")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val request = ActualizarClienteRequest(
                    nombreCliente = editNombre.text.toString().trim(),
                    apellido = editApellido.text.toString().trim().ifEmpty { null },
                    identificacion = cliente.identificacion,
                    telefono = editTelefono.text.toString().trim().ifEmpty { null },
                    email = cliente.email,
                    direccion = editDireccion.text.toString().trim().ifEmpty { null }
                )
                viewModel.actualizarCliente(request)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showSnackbar(message: String) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            val textView = snackbar.view.findViewById<android.widget.TextView>(com.google.android.material.R.id.snackbar_text)
            textView.maxLines = 5
            snackbar.show()
        }
    }
}
