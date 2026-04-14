package com.sena.proyecto.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sena.proyecto.R
import com.sena.proyecto.data.repository.AuthRepository

class RegisterActivity : AppCompatActivity() {

    private lateinit var nombreInputLayout: TextInputLayout
    private lateinit var apellidoInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var identificacionInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout

    private lateinit var nombreEditText: TextInputEditText
    private lateinit var apellidoEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var identificacionEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText

    private lateinit var registerButton: MaterialButton
    private lateinit var loginButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModel.Factory(AuthRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializeViews()
        setupClickListeners()
        observeViewModel()
    }

    private fun initializeViews() {
        nombreInputLayout = findViewById(R.id.nombreInputLayout)
        apellidoInputLayout = findViewById(R.id.apellidoInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        identificacionInputLayout = findViewById(R.id.identificacionInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)

        nombreEditText = findViewById(R.id.nombreEditText)
        apellidoEditText = findViewById(R.id.apellidoEditText)
        emailEditText = findViewById(R.id.emailEditText)
        identificacionEditText = findViewById(R.id.identificacionEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            if (validateInput()) {
                viewModel.register(
                    nombre = nombreEditText.text.toString().trim(),
                    apellido = apellidoEditText.text.toString().trim(),
                    email = emailEditText.text.toString().trim(),
                    password = passwordEditText.text.toString().trim(),
                    identificacion = identificacionEditText.text.toString().trim()
                )
            }
        }

        loginButton.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.registerState.observe(this) { state ->
            when (state) {
                is RegisterViewModel.RegisterState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    registerButton.isEnabled = false
                }
                is RegisterViewModel.RegisterState.Success -> {
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    // Volver al login después de registrar
                    finish()
                }
                is RegisterViewModel.RegisterState.Error -> {
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is RegisterViewModel.RegisterState.Idle -> {
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val nombre = nombreEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val identificacion = identificacionEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        if (nombre.isEmpty()) {
            nombreInputLayout.error = "El nombre es obligatorio"
            isValid = false
        } else {
            nombreInputLayout.error = null
        }

        if (email.isEmpty()) {
            emailInputLayout.error = "El correo electrónico es obligatorio"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Correo electrónico no válido"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (identificacion.isEmpty()) {
            identificacionInputLayout.error = "La identificación es obligatoria"
            isValid = false
        } else {
            identificacionInputLayout.error = null
        }

        if (password.isEmpty()) {
            passwordInputLayout.error = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = "Mínimo 6 caracteres"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        if (confirmPassword != password) {
            confirmPasswordInputLayout.error = "Las contraseñas no coinciden"
            isValid = false
        } else {
            confirmPasswordInputLayout.error = null
        }

        return isValid
    }
}
