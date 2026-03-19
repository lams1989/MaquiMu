import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;
  registroExitoso: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      identificacion: ['', [Validators.required, Validators.minLength(5)]]
    }, { validator: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const { nombre, apellido, email, password, identificacion } = this.registerForm.value;
    const nombreCompleto = `${nombre?.trim() ?? ''} ${apellido?.trim() ?? ''}`.trim();

    this.authService.register({ nombre, apellido, nombreCompleto, email, password, identificacion, rol: 'CLIENTE' }).subscribe({
      next: (response: any) => {
        this.isLoading = false;
        this.registroExitoso = true;
        this.successMessage = response?.mensaje || 'Tu cuenta ha sido creada exitosamente. Un operario la revisará en un plazo de 1 a 3 días hábiles.';
      },
      error: (err: any) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Ocurrió un error durante el registro. Intente de nuevo.';
      }
    });
  }

  volverAlLogin(): void {
    this.router.navigate(['/auth/login']);
  }
}
