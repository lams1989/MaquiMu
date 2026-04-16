package com.sena.proyecto.ui.invoice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sena.proyecto.R
import com.sena.proyecto.data.repository.FacturaRepository
import com.sena.proyecto.utils.FormatUtils

class InvoicesFragment : Fragment() {

    private lateinit var viewModel: InvoicesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var tvError: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: InvoiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_invoices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            InvoicesViewModel.Factory(requireActivity().application, FacturaRepository())
        ).get(InvoicesViewModel::class.java)

        initViews(view)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadFacturas()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.invoicesRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvSubtitle = view.findViewById(R.id.tvSubtitle)
        tvError = view.findViewById(R.id.tvError)
        tvEmpty = view.findViewById(R.id.tvEmpty)
    }

    private fun setupRecyclerView() {
        adapter = InvoiceAdapter(
            onItemClick = { /* No extra action on tap */ },
            onDownloadClick = { facturaId -> viewModel.descargarPdf(facturaId) }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is InvoicesViewModel.InvoicesState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                    tvEmpty.visibility = View.GONE
                }
                is InvoicesViewModel.InvoicesState.Success -> {
                    progressBar.visibility = View.GONE
                    if (state.facturas.isEmpty()) {
                        tvEmpty.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        tvSubtitle.text = "Sin facturas registradas"
                    } else {
                        tvEmpty.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.updateData(state.facturas)
                        val total = state.facturas
                            .filter { it.estadoPago == "PENDIENTE" }
                            .mapNotNull { it.montoTotal }
                            .fold(java.math.BigDecimal.ZERO) { acc, v -> acc.add(v) }
                        tvSubtitle.text = "${state.facturas.size} factura(s) • ${FormatUtils.formatCurrency(total)} pendiente"
                    }
                }
                is InvoicesViewModel.InvoicesState.Error -> {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = state.message
                }
            }
        }

        viewModel.downloadState.observe(viewLifecycleOwner) { dState ->
            when (dState) {
                is InvoicesViewModel.DownloadState.Loading -> {
                    showSnackbar("Descargando PDF...")
                }
                is InvoicesViewModel.DownloadState.Success -> {
                    showSnackbar("PDF descargado")
                    try {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(dState.filePath), "application/pdf")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        startActivity(intent)
                    } catch (_: Exception) {
                        // No PDF viewer installed
                    }
                    viewModel.clearDownloadState()
                }
                is InvoicesViewModel.DownloadState.Error -> {
                    showSnackbar(dState.message)
                    viewModel.clearDownloadState()
                }
                is InvoicesViewModel.DownloadState.Idle -> { /* noop */ }
            }
        }
    }

    private fun showSnackbar(message: String) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.maxLines = 5
            snackbar.show()
        }
    }
}
