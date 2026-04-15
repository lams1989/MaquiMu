import { AlquilerService } from './alquiler.service';
import { AppNotification } from '@core/models/notification.model';
import { AuthService } from './auth/auth.service';
import { BehaviorSubject, of } from 'rxjs';
import { ClienteService } from './cliente.service';
import { discardPeriodicTasks, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FacturaService } from './factura.service';
import { NotificationService } from './notification.service';
import { PLATFORM_ID } from '@angular/core';
import { Usuario } from '@core/models/auth/login-register.models';
import { UsuarioService } from './usuario.service';

describe('NotificationService', () => {
  let service: NotificationService;
  let alquilerServiceSpy: jasmine.SpyObj<AlquilerService>;
  let facturaServiceSpy: jasmine.SpyObj<FacturaService>;
  let clienteServiceSpy: jasmine.SpyObj<ClienteService>;
  let currentUserSubject: BehaviorSubject<Usuario | null>;
  let currentUserValueRef: { value: Usuario | null };

  const mockOperario: Usuario = {
    usuarioId: 1,
    nombreCompleto: 'Admin Test',
    email: 'admin@test.com',
    rol: 'OPERARIO'
  };

  const mockCliente: Usuario = {
    usuarioId: 2,
    nombreCompleto: 'Cliente Test',
    email: 'cliente@test.com',
    rol: 'CLIENTE',
    clienteId: 10
  };

  beforeEach(() => {
    localStorage.clear();
    currentUserSubject = new BehaviorSubject<Usuario | null>(null);
    currentUserValueRef = { value: null };

    const authSpy = jasmine.createSpyObj('AuthService', ['logout'], {
      currentUser: currentUserSubject.asObservable()
    });
    Object.defineProperty(authSpy, 'currentUserValue', {
      get: () => currentUserValueRef.value,
      configurable: true
    });

    alquilerServiceSpy = jasmine.createSpyObj('AlquilerService', ['getAlquileres', 'getMisAlquileres']);
    facturaServiceSpy = jasmine.createSpyObj('FacturaService', ['getMisFacturas']);
    clienteServiceSpy = jasmine.createSpyObj('ClienteService', ['getClienteById']);

    // Default: return empty arrays
    alquilerServiceSpy.getAlquileres.and.returnValue(of([]));
    alquilerServiceSpy.getMisAlquileres.and.returnValue(of([]));
    facturaServiceSpy.getMisFacturas.and.returnValue(of([]));
    clienteServiceSpy.getClienteById.and.returnValue(of({ clienteId: 10, nombreCliente: 'Test', apellido: 'Apellido', identificacion: '123', email: 'test@test.com', telefono: '300', direccion: 'Dir', fechaRegistro: '2025-01-01' }));

    TestBed.configureTestingModule({
      providers: [
        NotificationService,
        { provide: AuthService, useValue: authSpy },
        { provide: AlquilerService, useValue: alquilerServiceSpy },
        { provide: FacturaService, useValue: facturaServiceSpy },
        { provide: ClienteService, useValue: clienteServiceSpy },
        { provide: UsuarioService, useValue: (() => { const spy = jasmine.createSpyObj('UsuarioService', ['getUsuariosPendientes', 'getUsuariosRestablecer']); spy.getUsuariosPendientes.and.returnValue(of([])); spy.getUsuariosRestablecer.and.returnValue(of([])); return spy; })() },
        { provide: PLATFORM_ID, useValue: 'browser' }
      ]
    });

    service = TestBed.inject(NotificationService);
  });

  afterEach(() => {
    service.ngOnDestroy();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should start with empty notifications', () => {
    expect(service.unreadCount).toBe(0);
  });

  /** Helper: login as admin with pending rentals and tick to flush timer */
  function loginAdminWithPending(
    rentals: any[] = [{ alquilerId: 1, clienteId: 10, maquinariaId: 1, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 1000, estado: 'PENDIENTE' }]
  ): void {
    currentUserValueRef.value = mockOperario;
    alquilerServiceSpy.getAlquileres.and.returnValue(of(rentals));
    currentUserSubject.next(mockOperario);
    tick(0); // flush timer(0, ...)
  }

  // ===== markAsRead =====
  describe('markAsRead()', () => {
    it('should mark a specific notification as read', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      loginAdminWithPending();

      expect(notifications.length).toBeGreaterThan(0);
      const first = notifications[0];
      expect(first.read).toBeFalse();

      service.markAsRead(first.id);

      expect(notifications.find(n => n.id === first.id)?.read).toBeTrue();

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== markAllAsRead =====
  describe('markAllAsRead()', () => {
    it('should mark all notifications as read', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      currentUserValueRef.value = mockCliente;
      alquilerServiceSpy.getMisAlquileres.and.returnValue(of([
        { alquilerId: 1, clienteId: 10, maquinariaId: 1, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 1000, estado: 'PENDIENTE' },
        { alquilerId: 2, clienteId: 10, maquinariaId: 2, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 2000, estado: 'ACTIVO' }
      ]));
      facturaServiceSpy.getMisFacturas.and.returnValue(of([]));
      currentUserSubject.next(mockCliente);
      tick(0);

      expect(notifications.length).toBeGreaterThan(0);
      expect(service.unreadCount).toBeGreaterThan(0);

      service.markAllAsRead();

      expect(service.unreadCount).toBe(0);
      notifications.forEach(n => expect(n.read).toBeTrue());

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== dismissNotification =====
  describe('dismissNotification()', () => {
    it('should remove a specific notification', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      loginAdminWithPending();

      const countBefore = notifications.length;
      expect(countBefore).toBeGreaterThan(0);

      const idToRemove = notifications[0].id;
      service.dismissNotification(idToRemove);

      expect(notifications.length).toBe(countBefore - 1);
      expect(notifications.find(n => n.id === idToRemove)).toBeUndefined();

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== clearAll =====
  describe('clearAll()', () => {
    it('should remove all notifications', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      loginAdminWithPending();

      expect(notifications.length).toBeGreaterThan(0);

      service.clearAll();

      expect(notifications.length).toBe(0);

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== Admin Polling =====
  describe('Admin polling', () => {
    it('should generate pending summary on first poll for admin', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      loginAdminWithPending([
        { alquilerId: 1, clienteId: 10, maquinariaId: 1, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 1000, estado: 'PENDIENTE' },
        { alquilerId: 2, clienteId: 11, maquinariaId: 2, fechaInicio: '2026-01-01', fechaFin: '2026-01-15', costoTotal: 2000, estado: 'PENDIENTE' },
        { alquilerId: 3, clienteId: 12, maquinariaId: 3, fechaInicio: '2026-01-01', fechaFin: '2026-01-20', costoTotal: 3000, estado: 'ACTIVO' }
      ]);

      expect(notifications.length).toBe(1);
      expect(notifications[0].type).toBe('rental_pending');
      expect(notifications[0].title).toBe('Solicitudes pendientes');
      expect(notifications[0].message).toContain('2');
      expect(notifications[0].routerLink).toBe('/admin/rentals');

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));

    it('should NOT generate summary when no pending rentals', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      loginAdminWithPending([
        { alquilerId: 1, clienteId: 10, maquinariaId: 1, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 1000, estado: 'ACTIVO' }
      ]);

      expect(notifications.length).toBe(0);

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== Client Polling =====
  describe('Client polling', () => {
    it('should generate pending and active summary on first poll for client', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      currentUserValueRef.value = mockCliente;
      alquilerServiceSpy.getMisAlquileres.and.returnValue(of([
        { alquilerId: 1, clienteId: 10, maquinariaId: 1, fechaInicio: '2026-01-01', fechaFin: '2026-01-10', costoTotal: 1000, estado: 'PENDIENTE' },
        { alquilerId: 2, clienteId: 10, maquinariaId: 2, fechaInicio: '2026-01-01', fechaFin: '2026-01-15', costoTotal: 2000, estado: 'ACTIVO' }
      ]));
      facturaServiceSpy.getMisFacturas.and.returnValue(of([]));
      currentUserSubject.next(mockCliente);
      tick(0);

      expect(notifications.length).toBe(2);
      const types = notifications.map(n => n.type);
      expect(types).toContain('rental_pending');
      expect(types).toContain('rental_active');

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== localStorage Persistence =====
  describe('localStorage', () => {
    it('should save notifications to localStorage', fakeAsync(() => {
      loginAdminWithPending();

      const stored = localStorage.getItem('maquimu_notifications');
      expect(stored).toBeTruthy();
      const parsed = JSON.parse(stored!);
      expect(parsed.length).toBeGreaterThan(0);

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));

    it('should clear localStorage on logout', fakeAsync(() => {
      loginAdminWithPending();
      expect(localStorage.getItem('maquimu_notifications')).toBeTruthy();

      // Simulate logout
      currentUserValueRef.value = null;
      currentUserSubject.next(null);
      tick(0);

      expect(localStorage.getItem('maquimu_notifications')).toBeNull();

      discardPeriodicTasks();
    }));
  });

  // ===== unreadCount =====
  describe('unreadCount', () => {
    it('should return correct unread count', fakeAsync(() => {
      loginAdminWithPending();

      expect(service.unreadCount).toBe(1);

      service.markAllAsRead();

      expect(service.unreadCount).toBe(0);

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });

  // ===== Profile Incomplete Notification =====
  describe('Profile incomplete notification', () => {
    it('should generate profile_incomplete notification when client data is incomplete', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      currentUserValueRef.value = mockCliente;
      alquilerServiceSpy.getMisAlquileres.and.returnValue(of([]));
      facturaServiceSpy.getMisFacturas.and.returnValue(of([]));
      clienteServiceSpy.getClienteById.and.returnValue(of({
        clienteId: 10, nombreCliente: 'Test', identificacion: '123',
        email: 'test@test.com', fechaRegistro: '2025-01-01',
        telefono: '', direccion: '', apellido: ''
      }));
      currentUserSubject.next(mockCliente);
      tick(0);

      const profileNotif = notifications.find(n => n.type === 'profile_incomplete');
      expect(profileNotif).toBeTruthy();
      expect(profileNotif!.title).toBe('Perfil incompleto');
      expect(profileNotif!.message).toContain('datos pendientes');

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));

    it('should NOT generate profile_incomplete notification when client data is complete', fakeAsync(() => {
      let notifications: AppNotification[] = [];
      service.notifications$.subscribe(n => notifications = n);

      currentUserValueRef.value = mockCliente;
      alquilerServiceSpy.getMisAlquileres.and.returnValue(of([]));
      facturaServiceSpy.getMisFacturas.and.returnValue(of([]));
      clienteServiceSpy.getClienteById.and.returnValue(of({
        clienteId: 10, nombreCliente: 'Test', apellido: 'Apellido', identificacion: '123',
        email: 'test@test.com', telefono: '3001234567', direccion: 'Calle 1', fechaRegistro: '2025-01-01'
      }));
      currentUserSubject.next(mockCliente);
      tick(0);

      const profileNotif = notifications.find(n => n.type === 'profile_incomplete');
      expect(profileNotif).toBeUndefined();

      service.ngOnDestroy();
      discardPeriodicTasks();
    }));
  });
});
