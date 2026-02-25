import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  estadoCuenta: string = '';
  motivoRechazo: string = '';
  isLoading: boolean = false;

  // Forgot password modal
  showForgotModal = false;
  forgotEmail = '';
  forgotError = '';
  forgotLoading = false;
  forgotSuccess = false;
  forgotSuccessMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.estadoCuenta = '';
    this.motivoRechazo = '';

    const { email, password } = this.loginForm.value;

    this.authService.login({ email, password }).subscribe({
      next: (response) => {
        this.isLoading = false;
        const userRole = this.authService.getUserRole();
        if (userRole === 'OPERARIO') {
          this.router.navigate(['/admin/dashboard']);
        } else if (userRole === 'CLIENTE') {
          this.router.navigate(['/client/portal']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        this.isLoading = false;
        if (err.status === 403 && err.error?.estado) {
          this.estadoCuenta = err.error.estado;
          this.errorMessage = err.error.message || '';
          this.motivoRechazo = err.error.motivoRechazo || '';
        } else {
          this.estadoCuenta = '';
          this.errorMessage = err.error?.message || 'Credenciales inválidas. Intente de nuevo.';
        }
      }
    });
  }

  openForgotPasswordModal(): void {
    this.showForgotModal = true;
    this.forgotEmail = '';
    this.forgotError = '';
    this.forgotSuccess = false;
    this.forgotSuccessMessage = '';
  }

  closeForgotPasswordModal(): void {
    this.showForgotModal = false;
  }

  submitForgotPassword(): void {
    if (!this.forgotEmail.trim()) {
      this.forgotError = 'El correo electrónico es requerido.';
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.forgotEmail.trim())) {
      this.forgotError = 'Ingresa un correo electrónico válido.';
      return;
    }

    this.forgotLoading = true;
    this.forgotError = '';

    this.authService.solicitarRestablecimiento(this.forgotEmail.trim()).subscribe({
      next: (res) => {
        this.forgotLoading = false;
        this.forgotSuccess = true;
        this.forgotSuccessMessage = res.mensaje || 'Se ha enviado una solicitud de restablecimiento. Un operario te asignará una contraseña temporal en un plazo de 1 a 3 días hábiles.';
      },
      error: (err) => {
        this.forgotLoading = false;
        this.forgotError = err.error?.message || 'No se pudo procesar la solicitud. Intente de nuevo.';
      }
    });
  }
}
