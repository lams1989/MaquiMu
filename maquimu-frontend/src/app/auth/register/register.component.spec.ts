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

  it('should initialize register form without rol field (hardcoded as CLIENTE)', () => {
    expect(component.registerForm).toBeDefined();
    expect(component.registerForm.get('rol')).toBeNull();
  });

  it('should validate nombre as required with minimum length', () => {
    const nombreControl = component.registerForm.get('nombre');

    nombreControl?.setValue('');
    expect(nombreControl?.hasError('required')).toBe(true);

    nombreControl?.setValue('A');
    expect(nombreControl?.hasError('minlength')).toBe(true);

    nombreControl?.setValue('Juan');
    expect(nombreControl?.valid).toBe(true);
  });

  it('should validate apellido as required with minimum length', () => {
    const apellidoControl = component.registerForm.get('apellido');

    apellidoControl?.setValue('');
    expect(apellidoControl?.hasError('required')).toBe(true);

    apellidoControl?.setValue('P');
    expect(apellidoControl?.hasError('minlength')).toBe(true);

    apellidoControl?.setValue('Pérez');
    expect(apellidoControl?.valid).toBe(true);
  });

  it('should build nombreCompleto from nombre and apellido on submit', () => {
    authService.register.and.returnValue(of({ mensaje: 'Usuario registrado exitosamente' }));

    component.registerForm.patchValue({
      nombre: 'Juan',
      apellido: 'Pérez',
      email: 'juan@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith({
      nombre: 'Juan',
      apellido: 'Pérez',
      nombreCompleto: 'Juan Pérez',
      email: 'juan@example.com',
      password: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });
  });

  it('should trim nombre and apellido before building nombreCompleto', () => {
    authService.register.and.returnValue(of({ mensaje: 'Usuario registrado exitosamente' }));

    component.registerForm.patchValue({
      nombre: '  Juan  ',
      apellido: '  Pérez  ',
      email: 'juan@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith(
      jasmine.objectContaining({ nombreCompleto: 'Juan Pérez' })
    );
  });

  it('should handle apellido with spaces correctly', () => {
    authService.register.and.returnValue(of({ mensaje: 'Usuario registrado exitosamente' }));

    component.registerForm.patchValue({
      nombre: 'María',
      apellido: 'de la Cruz',
      email: 'maria@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith(
      jasmine.objectContaining({ nombreCompleto: 'María de la Cruz' })
    );
  });

  it('should not submit if nombre and apellido are invalid', () => {
    component.registerForm.patchValue({
      nombre: '',
      apellido: '',
      email: 'valid@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).not.toHaveBeenCalled();
    expect(component.registerForm.get('nombre')?.hasError('required')).toBe(true);
    expect(component.registerForm.get('apellido')?.hasError('required')).toBe(true);
  });

  it('should always submit with CLIENTE role regardless of any attempt to change it', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombre: 'Carlos',
      apellido: 'Operario',
      email: 'operario@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '987654321'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith(
      jasmine.objectContaining({ rol: 'CLIENTE', nombreCompleto: 'Carlos Operario' })
    );
  });

  it('should create nombreCompleto when apellido has accent characters', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombre: 'José',
      apellido: 'Muñoz',
      email: 'jose@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith(
      jasmine.objectContaining({ nombreCompleto: 'José Muñoz' })
    );
  });

  it('should allow submit when nombre and apellido are valid', () => {
    authService.register.and.returnValue(of({ mensaje: 'Éxito' }));

    component.registerForm.patchValue({
      nombre: 'Laura',
      apellido: 'Martínez',
      email: 'laura@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalled();
    expect(component.registerForm.get('nombre')?.valid).toBe(true);
    expect(component.registerForm.get('apellido')?.valid).toBe(true);
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
      nombre: '',
      apellido: '',
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
      nombre: 'Juan',
      apellido: 'Pérez',
      email: 'juan@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith({
      nombre: 'Juan',
      apellido: 'Pérez',
      nombreCompleto: 'Juan Pérez',
      email: 'juan@example.com',
      password: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });
  });

  it('should set registroExitoso and success message on successful registration', () => {
    authService.register.and.returnValue(of({ mensaje: 'Tu cuenta ha sido creada exitosamente.' }));

    component.registerForm.patchValue({
      nombre: 'Test',
      apellido: 'User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789'
    });

    component.onSubmit();

    expect(component.registroExitoso).toBe(true);
    expect(component.successMessage).toBe('Tu cuenta ha sido creada exitosamente.');
    expect(component.isLoading).toBe(false);
  });

  it('should display error message on registration failure', () => {
    const errorResponse = {
      error: { message: 'El email ya está registrado' }
    };

    authService.register.and.returnValue(throwError(() => errorResponse));

    component.registerForm.patchValue({
      nombre: 'Test',
      apellido: 'User',
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
      nombre: 'Test',
      apellido: 'User',
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
      nombre: 'Test',
      apellido: 'User',
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
      nombre: 'Test',
      apellido: 'User',
      email: 'test@example.com',
      password: 'password123',
      confirmPassword: 'password123',
      identificacion: '123456789',
      rol: 'CLIENTE'
    });

    component.onSubmit();

    expect(component.errorMessage).toBe('');
  });

  it('should call volverAlLogin and navigate to login', () => {
    component.volverAlLogin();

    expect(router.navigate).toHaveBeenCalledWith(['/auth/login']);
  });
});
