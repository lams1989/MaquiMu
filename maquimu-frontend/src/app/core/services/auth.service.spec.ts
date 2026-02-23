import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth/login-register.models';
import { AuthService } from './auth/auth.service';
import { environment } from '../../../environments/environment';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { TestBed } from '@angular/core/testing';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let router: Router;
  const apiUrl = `${environment.apiUrl}/v1/auth`;

  beforeEach(() => {
    // Clear localStorage before each test
    localStorage.clear();

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('login()', () => {
    it('should call POST endpoint and store token and user in localStorage', (done) => {
      const loginRequest: LoginRequest = { email: 'test@example.com', password: 'password123' };
      const mockResponse: AuthResponse = {
        token: 'fake-jwt-token',
        usuario: {
          usuarioId: 1,
          nombreCompleto: 'Test User',
          email: 'test@example.com',
          rol: 'CLIENTE',
          clienteId: 10
        }
      };

      service.login(loginRequest).subscribe(response => {
        expect(response).toEqual(mockResponse);
        expect(localStorage.getItem('jwtToken')).toBe('fake-jwt-token');
        expect(localStorage.getItem('currentUser')).toBe(JSON.stringify(mockResponse.usuario));
        expect(service.currentUserValue).toEqual(mockResponse.usuario);
        done();
      });

      const req = httpMock.expectOne(`${apiUrl}/login`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(loginRequest);
      req.flush(mockResponse);
    });

    it('should handle login with OPERARIO role', (done) => {
      const loginRequest: LoginRequest = { email: 'operario@example.com', password: 'password123' };
      const mockResponse: AuthResponse = {
        token: 'operario-jwt-token',
        usuario: {
          usuarioId: 2,
          nombreCompleto: 'Operario Test',
          email: 'operario@example.com',
          rol: 'OPERARIO'
        }
      };

      service.login(loginRequest).subscribe(response => {
        expect(response.usuario.rol).toBe('OPERARIO');
        expect(service.getUserRole()).toBe('OPERARIO');
        done();
      });

      const req = httpMock.expectOne(`${apiUrl}/login`);
      req.flush(mockResponse);
    });
  });

  describe('register()', () => {
    it('should call POST endpoint with registration data', (done) => {
      const registerRequest: RegisterRequest = {
        nombreCompleto: 'New User',
        email: 'newuser@example.com',
        password: 'password123',
        identificacion: '123456789',
        rol: 'CLIENTE'
      };

      service.register(registerRequest).subscribe(response => {
        expect(response).toBeDefined();
        done();
      });

      const req = httpMock.expectOne(`${apiUrl}/register`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(registerRequest);
      req.flush({ mensaje: 'Usuario registrado exitosamente' });
    });
  });

  describe('getToken()', () => {
    it('should return token from localStorage', () => {
      localStorage.setItem('jwtToken', 'test-token');
      expect(service.getToken()).toBe('test-token');
    });

    it('should return null if no token exists', () => {
      expect(service.getToken()).toBeNull();
    });
  });

  describe('isAuthenticated()', () => {
    it('should return true if token exists', () => {
      localStorage.setItem('jwtToken', 'test-token');
      expect(service.isAuthenticated()).toBe(true);
    });

    it('should return false if no token exists', () => {
      expect(service.isAuthenticated()).toBe(false);
    });
  });

  describe('getUserRole()', () => {
    it('should return user role from currentUser', () => {
      const mockUser = {
        usuarioId: 1,
        nombreCompleto: 'Test User',
        email: 'test@example.com',
        rol: 'CLIENTE' as 'CLIENTE',
        clienteId: 10
      };
      localStorage.setItem('currentUser', JSON.stringify(mockUser));
      localStorage.setItem('jwtToken', 'test-token');

      // Recreate service to load from localStorage
      TestBed.resetTestingModule();
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, RouterTestingModule],
        providers: [AuthService]
      });
      service = TestBed.inject(AuthService);

      expect(service.getUserRole()).toBe('CLIENTE');
    });

    it('should return null if no user is logged in', () => {
      expect(service.getUserRole()).toBeNull();
    });
  });

  describe('logout()', () => {
    it('should remove token and user from localStorage', () => {
      localStorage.setItem('jwtToken', 'test-token');
      localStorage.setItem('currentUser', JSON.stringify({ email: 'test@example.com' }));

      service.logout();

      expect(localStorage.getItem('jwtToken')).toBeNull();
      expect(localStorage.getItem('currentUser')).toBeNull();
      expect(service.currentUserValue).toBeNull();
    });

    it('should navigate to login page', () => {
      service.logout();
      expect(router.navigate).toHaveBeenCalledWith(['/auth/login']);
    });

    it('should clear currentUserSubject', () => {
      const mockUser = {
        usuarioId: 1,
        nombreCompleto: 'Test User',
        email: 'test@example.com',
        rol: 'CLIENTE' as 'CLIENTE'
      };

      localStorage.setItem('jwtToken', 'test-token');
      localStorage.setItem('currentUser', JSON.stringify(mockUser));

      // Recreate service to load from localStorage
      TestBed.resetTestingModule();
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, RouterTestingModule],
        providers: [AuthService]
      });
      service = TestBed.inject(AuthService);
      router = TestBed.inject(Router);
      spyOn(router, 'navigate');

      expect(service.currentUserValue).toEqual(mockUser);

      service.logout();

      expect(service.currentUserValue).toBeNull();
    });
  });
});
