package com.sena.proyecto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sena.proyecto.fragments.DashboardFragment
import com.sena.proyecto.fragments.InvoicesFragment
import com.sena.proyecto.fragments.ProfileFragment
import com.sena.proyecto.fragments.RentalsFragment

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        initializeViews()
        setupBottomNavigation()
        
        // Mostrar el dashboard por defecto
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DashboardFragment())
                .commit()
            bottomNavigation.selectedItemId = R.id.nav_dashboard
        }
    }
    
    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
        fab = findViewById(R.id.fab)
        
        fab.setOnClickListener {
            val intent = Intent(this, NewRentalRequestActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_dashboard -> {
                    fab.show()
                    DashboardFragment()
                }
                R.id.nav_rentals -> {
                    fab.hide()
                    RentalsFragment()
                }
                R.id.nav_invoices -> {
                    fab.hide()
                    InvoicesFragment()
                }
                R.id.nav_profile -> {
                    fab.hide()
                    ProfileFragment()
                }
                else -> DashboardFragment()
            }
            
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            
            true
        }
    }
    
    fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}