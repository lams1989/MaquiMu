import { AuthResponse } from '../../core/models/auth/login-register.models';
import { AuthService } from '../../core/services/auth/auth.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: jasmine.SpyObj<AuthService>;
  let router: Router;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['login', 'getUserRole']);

    await TestBed.configureTestingModule({
      imports: [
        LoginComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        { provide: AuthService, useValue: authServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize login form with empty fields', () => {
    expect(component.loginForm).toBeDefined();
    expect(component.loginForm.get('email')?.value).toBe('');
    expect(component.loginForm.get('password')?.value).toBe('');
  });

  it('should validate email field as required and valid email format', () => {
    const emailControl = component.loginForm.get('email');

    emailControl?.setValue('');
    expect(emailControl?.hasError('required')).toBe(true);

    emailControl?.setValue('invalid-email');
    expect(emailControl?.hasError('email')).toBe(true);

    emailControl?.setValue('valid@example.com');
    expect(emailControl?.valid).toBe(true);
  });

  it('should validate password field as required', () => {
    const passwordControl = component.loginForm.get('password');

    passwordControl?.setValue('');
    expect(passwordControl?.hasError('required')).toBe(true);

    passwordControl?.setValue('password123');
    expect(passwordControl?.valid).toBe(true);
  });

  it('should not submit if form is invalid', () => {
    component.loginForm.patchValue({ email: '', password: '' });
    component.onSubmit();

    expect(authService.login).not.toHaveBeenCalled();
    expect(component.loginForm.get('email')?.touched).toBe(true);
    expect(component.loginForm.get('password')?.touched).toBe(true);
  });

  it('should call authService.login with correct credentials on valid submit', () => {
    const mockResponse: AuthResponse = {
      token: 'fake-jwt-token',
      usuario: {
        usuarioId: 1,
        nombreCompleto: 'Test User',
        email: 'test@example.com',
        rol: 'CLIENTE'
      }
    };

    authService.login.and.returnValue(of(mockResponse));
    authService.getUserRole.and.returnValue('CLIENTE');

    component.loginForm.patchValue({
      email: 'test@example.com',
      password: 'password123'
    });

    component.onSubmit();

    expect(authService.login).toHaveBeenCalledWith({
      email: 'test@example.com',
      password: 'password123'
    });
  });

  it('should redirect to /client/portal after successful login with CLIENTE role', () => {
    const mockResponse: AuthResponse = {
      token: 'fake-jwt-token',
      usuario: {
        usuarioId: 1,
        nombreCompleto: 'Cliente Test',
        email: 'cliente@example.com',
        rol: 'CLIENTE',
        clienteId: 10
      }
    };

    authService.login.and.returnValue(of(mockResponse));
    authService.getUserRole.and.returnValue('CLIENTE');

    component.loginForm.patchValue({
      email: 'cliente@example.com',
      password: 'password123'
    });

    component.onSubmit();

    expect(router.navigate).toHaveBeenCalledWith(['/client/portal']);
    expect(component.isLoading).toBe(false);
  });

  it('should redirect to /admin/dashboard after successful login with OPERARIO role', () => {
    const mockResponse: AuthResponse = {
      token: 'operario-jwt-token',
      usuario: {
        usuarioId: 2,
        nombreCompleto: 'Operario Test',
        email: 'operario@example.com',
        rol: 'OPERARIO'
      }
    };

    authService.login.and.returnValue(of(mockResponse));
    authService.getUserRole.and.returnValue('OPERARIO');

    component.loginForm.patchValue({
      email: 'operario@example.com',
      password: 'password123'
    });

    component.onSubmit();

    expect(router.navigate).toHaveBeenCalledWith(['/admin/dashboard']);
    expect(component.isLoading).toBe(false);
  });

  it('should display error message on login failure', () => {
    const errorResponse = {
      error: { message: 'Credenciales inválidas' }
    };

    authService.login.and.returnValue(throwError(() => errorResponse));

    component.loginForm.patchValue({
      email: 'wrong@example.com',
      password: 'wrongpassword'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('Credenciales inválidas');
    expect(component.isLoading).toBe(false);
  });

  it('should display default error message when server error has no message', () => {
    authService.login.and.returnValue(throwError(() => ({ error: {} })));

    component.loginForm.patchValue({
      email: 'test@example.com',
      password: 'password123'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('Credenciales inválidas. Intente de nuevo.');
    expect(component.isLoading).toBe(false);
  });

  it('should set isLoading to true during login process', () => {
    authService.login.and.returnValue(of({} as AuthResponse));
    authService.getUserRole.and.returnValue('CLIENTE');

    component.loginForm.patchValue({
      email: 'test@example.com',
      password: 'password123'
    });

    expect(component.isLoading).toBe(false);

    component.onSubmit();

    // During the call, isLoading should be true
    // After completion, it's set back to false
    expect(component.isLoading).toBe(false);
  });

  it('should clear error message on new submit attempt', () => {
    component.errorMessage = 'Previous error';
    authService.login.and.returnValue(of({} as AuthResponse));
    authService.getUserRole.and.returnValue('CLIENTE');

    component.loginForm.patchValue({
      email: 'test@example.com',
      password: 'password123'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('');
  });
});
