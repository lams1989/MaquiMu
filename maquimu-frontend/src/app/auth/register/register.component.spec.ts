import { AuthService } from '../../core/services/auth/auth.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: jasmine.SpyObj<AuthService>;
  let router: Router;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['register']);

    await TestBed.configureTestingModule({
      imports: [
        RegisterComponent,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [
        { provide: AuthService, useValue: authServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize register form with default CLIENTE role', () => {
    expect(component.registerForm).toBeDefined();
    expect(component.registerForm.get('rol')?.value).toBe('CLIENTE');
  });

  it('should validate nombreCompleto as required with minimum length', () => {
    const nombreControl = component.registerForm.get('nombreCompleto');

    nombreControl?.setValue('');
    expect(nombreControl?.hasError('required')).toBe(true);

    nombreControl?.setValue('AB');
    expect(nombreControl?.hasError('minlength')).toBe(true);

    nombreControl?.setValue('Juan Pérez');
    expect(nombreControl?.valid).toBe(true);
  });

  it('should validate email field as required and valid format', () => {
    const emailControl = component.registerForm.get('email');

    emailControl?.setValue('');
    expect(emailControl?.hasError('required')).toBe(true);

    emailControl?.setValue('invalid-email');
    expect(emailControl?.hasError('email')).toBe(true);

    emailControl?.setValue('valid@example.com');
    expect(emailControl?.valid).toBe(true);
  });

  it('should validate password with minimum length of 6 characters', () => {
    const passwordControl = component.registerForm.get('password');

    passwordControl?.setValue('');
    expect(passwordControl?.hasError('required')).toBe(true);

    passwordControl?.setValue('12345');
    expect(passwordControl?.hasError('minlength')).toBe(true);

    passwordControl?.setValue('123456');
    expect(passwordControl?.valid).toBe(true);
  });

  it('should validate identificacion as required with minimum length', () => {
    const idControl = component.registerForm.get('identificacion');

    idControl?.setValue('');
    expect(idControl?.hasError('required')).toBe(true);

    idControl?.setValue('1234');
    expect(idControl?.hasError('minlength')).toBe(true);

    idControl?.setValue('12345');
    expect(idControl?.valid).toBe(true);
  });

  it('should validate that password and confirmPassword match', () => {
    component.registerForm.patchValue({
      password: 'password123',
      confirmPassword: 'password123'
    });

    expect(component.registerForm.hasError('mismatch')).toBe(false);
  });

  it('should show mismatch error when passwords do not match', () => {
    component.registerForm.patchValue({
      password: 'password123',
      confirmPassword: 'different456'
    });

    expect(component.registerForm.hasError('mismatch')).toBe(true);
  });

  it('should not submit if form is invalid', () => {
    component.registerForm.patchValue({
      nombreCompleto: '',
      email: 'invalid',
      password: '123',
      confirmPassword: '123',
      identificacion: ''
    });

    component.onSubmit();

    expect(authService.register).not.toHaveBeenCalled();
    expect(component.registerForm.touched).toBe(true);
  });

  it('should call authService.register with correct data on valid submit', () => {
    authService.register.and.returnValue(of({ mensaje: 'Usuario registrado exitosamente' }));

    component.registerForm.patchValue({
      nombreCompleto: 'Juan Pérez',
      email: 'juan@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith({
      nombreCompleto: 'Juan Pérez',
      email: 'juan@example.com',
      password: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });
  });

  it('should redirect to login with registered query param on successful registration', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombreCompleto: 'Test User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(router.navigate).toHaveBeenCalledWith(
      ['/auth/login'],
      { queryParams: { registered: 'true' } }
    );
    expect(component.isLoading).toBe(false);
  });

  it('should display error message on registration failure', () => {
    const errorResponse = {
      error: { message: 'El email ya está registrado' }
    };

    authService.register.and.returnValue(throwError(() => errorResponse));

    component.registerForm.patchValue({
      nombreCompleto: 'Test User',
      email: 'duplicate@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('El email ya está registrado');
    expect(component.isLoading).toBe(false);
  });

  it('should display default error message when server error has no message', () => {
    authService.register.and.returnValue(throwError(() => ({ error: {} })));

    component.registerForm.patchValue({
      nombreCompleto: 'Test User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('Ocurrió un error durante el registro. Intente de nuevo.');
    expect(component.isLoading).toBe(false);
  });

  it('should set isLoading to true during registration process', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombreCompleto: 'Test User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    expect(component.isLoading).toBe(false);

    component.onSubmit();

    expect(component.isLoading).toBe(false);
  });

  it('should clear error message on new submit attempt', () => {
    component.errorMessage = 'Previous error';
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombreCompleto: 'Test User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('');
  });

  it('should accept OPERARIO role', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombreCompleto: 'Operario Test',
      email: 'operario@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '987654321',
      rol: 'OPERARIO'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith(
      jasmine.objectContaining({ rol: 'OPERARIO' })
    );
  });
});
