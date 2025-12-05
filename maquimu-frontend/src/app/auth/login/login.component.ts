import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth/auth.service'; // Adjust path if necessary
import { RouterLink } from '@angular/router'; // Import RouterLink for the template

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // Add ReactiveFormsModule and RouterLink
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
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
          this.router.navigate(['/']); // Fallback to home or a default route
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Credenciales inválidas. Intente de nuevo.';
      }
    });
  }
}
