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
import com.sena.proyecto.ui.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var forgotPasswordButton: MaterialButton
    private lateinit var registerButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.Factory(AuthRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si ya está autenticado, ir directo al dashboard
        val authRepo = AuthRepository(this)
        if (authRepo.isAuthenticated()) {
            navigateToDashboard()
            return
        }

        setContentView(R.layout.activity_login)
        initializeViews()
        setupClickListeners()
        observeViewModel()
    }

    private fun initializeViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)
        registerButton = findViewById(R.id.registerButton)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.login(email, password)
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordButton.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    loginButton.isEnabled = false
                }
                is LoginViewModel.LoginState.Success -> {
                    progressBar.visibility = View.GONE
                    loginButton.isEnabled = true
                    navigateToDashboard()
                }
                is LoginViewModel.LoginState.Error -> {
                    progressBar.visibility = View.GONE
                    loginButton.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginState.Idle -> {
                    progressBar.visibility = View.GONE
                    loginButton.isEnabled = true
                }
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
        } else if (password.length < 6) {
            passwordInputLayout.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showForgotPasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val emailInput = dialogView.findViewById<TextInputEditText>(R.id.forgotEmailEditText)

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Restablecer Contraseña")
            .setView(dialogView)
            .setPositiveButton("Enviar solicitud") { _, _ ->
                val email = emailInput.text.toString().trim()
                if (email.isNotEmpty()) {
                    viewModel.requestPasswordReset(email)
                } else {
                    Toast.makeText(this, "Ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
