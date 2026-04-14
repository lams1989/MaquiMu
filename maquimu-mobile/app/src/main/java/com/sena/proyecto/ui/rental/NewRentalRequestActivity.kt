package com.sena.proyecto.ui.rental

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sena.proyecto.R

class NewRentalRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_rental_request)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nueva Solicitud"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
