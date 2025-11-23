package com.sena.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
    }
    
    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            
            if (validateInput(email, password)) {
                performLogin()
            }
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true
        
        if (email.isEmpty()) {
            emailInputLayout.error = "El correo electrónico es obligatorio"
            isValid = false
        } else {
            emailInputLayout.error = null
        }
        
        if (password.isEmpty()) {
            passwordInputLayout.error = "La contraseña es obligatoria"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }
        
        return isValid
    }
    
    private fun performLogin() {
        // Simulación de login exitoso
        Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
        
        // Navegar al DashboardActivity
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}