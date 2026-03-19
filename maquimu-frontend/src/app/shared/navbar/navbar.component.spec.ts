import { AuthService } from '../../core/services/auth/auth.service';
import { BehaviorSubject } from 'rxjs';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarComponent } from './navbar.component';
import { NotificationDropdownComponent } from '../notification-dropdown/notification-dropdown.component';
import { NotificationService } from '../../core/services/notification.service';
import { RouterTestingModule } from '@angular/router/testing';
import { Usuario } from '../../core/models/auth/login-register.models';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let currentUserSubject: BehaviorSubject<Usuario | null>;

  const mockUser: Usuario = {
    usuarioId: 1,
    nombreCompleto: 'Luis Pérez',
    email: 'luis@test.com',
    rol: 'OPERARIO'
  };

  beforeEach(async () => {
    currentUserSubject = new BehaviorSubject<Usuario | null>(null);

    authServiceSpy = jasmine.createSpyObj('AuthService', ['logout'], {
      currentUser: currentUserSubject.asObservable(),
      currentUserValue: null
    });

    const notifServiceSpy = jasmine.createSpyObj('NotificationService', [
      'markAsRead', 'markAllAsRead', 'dismissNotification', 'clearAll'
    ], {
      notifications$: new BehaviorSubject([]).asObservable()
    });

    await TestBed.configureTestingModule({
      imports: [NavbarComponent, RouterTestingModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: NotificationService, useValue: notifServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('rendering', () => {
    it('should render the navbar-pro element', () => {
      const nav = fixture.nativeElement.querySelector('.navbar-pro');
      expect(nav).toBeTruthy();
    });

    it('should display the brand name', () => {
      const brand = fixture.nativeElement.querySelector('.brand-name');
      expect(brand.textContent.trim()).toBe('MaquiMu');
    });

    it('should display the Admin role chip', () => {
      const chip = fixture.nativeElement.querySelector('.role-chip');
      expect(chip.textContent.trim()).toBe('Admin');
    });

    it('should display the notification dropdown', () => {
      const dropdown = fixture.nativeElement.querySelector('app-notification-dropdown');
      expect(dropdown).toBeTruthy();
    });

    it('should display logout button', () => {
      const logoutBtn = fixture.nativeElement.querySelector('.nav-logout');
      expect(logoutBtn).toBeTruthy();
    });
  });

  describe('user info', () => {
    it('should show "U" as default avatar initial when no user', () => {
      const avatar = fixture.nativeElement.querySelector('.user-avatar-pro span');
      expect(avatar.textContent.trim()).toBe('U');
    });

    it('should show "Usuario" as default name when no user', () => {
      const name = fixture.nativeElement.querySelector('.user-name');
      expect(name.textContent.trim()).toBe('Usuario');
    });

    it('should show user initial and name when user is logged in', () => {
      currentUserSubject.next(mockUser);
      fixture.detectChanges();

      const avatar = fixture.nativeElement.querySelector('.user-avatar-pro span');
      expect(avatar.textContent.trim()).toBe('L');

      const name = fixture.nativeElement.querySelector('.user-name');
      expect(name.textContent.trim()).toBe('Luis Pérez');
    });
  });

  describe('logout', () => {
    it('should call authService.logout() when logout button is clicked', () => {
      const logoutBtn = fixture.nativeElement.querySelector('.nav-logout');
      logoutBtn.click();
      expect(authServiceSpy.logout).toHaveBeenCalled();
    });
  });
});
