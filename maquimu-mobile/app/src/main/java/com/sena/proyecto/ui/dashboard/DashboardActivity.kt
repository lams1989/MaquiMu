package com.sena.proyecto.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sena.proyecto.R
import com.sena.proyecto.data.repository.AuthRepository
import com.sena.proyecto.ui.auth.LoginActivity
import com.sena.proyecto.ui.invoice.InvoicesFragment
import com.sena.proyecto.ui.profile.ProfileFragment
import com.sena.proyecto.ui.rental.RentalsFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initializeViews()
        setupBottomNavigation()
        setupBackNavigation()

        // Mostrar el dashboard por defecto
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DashboardFragment())
                .commit()
            bottomNavigation.selectedItemId = R.id.nav_dashboard
        }
    }

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Si no está en el tab de Dashboard, volver al tab Dashboard
                if (bottomNavigation.selectedItemId != R.id.nav_dashboard) {
                    bottomNavigation.selectedItemId = R.id.nav_dashboard
                } else {
                    // Si ya está en Dashboard, preguntar si quiere salir
                    MaterialAlertDialogBuilder(this@DashboardActivity)
                        .setTitle("Salir de MaquiMu")
                        .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                        .setPositiveButton("Salir") { _, _ ->
                            finishAffinity()
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
            }
        })
    }

    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_dashboard -> DashboardFragment()
                R.id.nav_rentals -> RentalsFragment()
                R.id.nav_invoices -> InvoicesFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> DashboardFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            true
        }
    }

    fun logout() {
        val authRepository = AuthRepository(this)
        authRepository.logout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
