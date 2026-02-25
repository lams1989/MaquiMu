import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  estadoCuenta: string = '';
  motivoRechazo: string = '';
  isLoading: boolean = false;

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
}
