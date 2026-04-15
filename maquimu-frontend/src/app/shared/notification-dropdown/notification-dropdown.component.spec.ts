import { AppNotification } from '@core/models/notification.model';
import { BehaviorSubject, of, Subject } from 'rxjs';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NotificationDropdownComponent } from './notification-dropdown.component';
import { NotificationService } from '@core/services/notification.service';
import { Router } from '@angular/router';

describe('NotificationDropdownComponent', () => {
  let component: NotificationDropdownComponent;
  let fixture: ComponentFixture<NotificationDropdownComponent>;
  let notifServiceSpy: jasmine.SpyObj<NotificationService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let notificationsSubject: BehaviorSubject<AppNotification[]>;

  const mockNotifications: AppNotification[] = [
    {
      id: 'test-1',
      type: 'rental_pending',
      title: 'Solicitudes pendientes',
      message: 'Hay 2 solicitud(es) pendientes.',
      icon: 'bi-hourglass-split',
      iconColor: '#f59e0b',
      timestamp: new Date(),
      read: false,
      routerLink: '/admin/rentals'
    },
    {
      id: 'test-2',
      type: 'rental_approved',
      title: 'Alquiler aprobado',
      message: 'Tu solicitud #5 fue aprobada.',
      icon: 'bi-check-circle-fill',
      iconColor: '#22c55e',
      timestamp: new Date(Date.now() - 3600000), // 1 hour ago
      read: true,
      routerLink: '/client/my-rentals'
    }
  ];

  beforeEach(async () => {
    notificationsSubject = new BehaviorSubject<AppNotification[]>(mockNotifications);

    notifServiceSpy = jasmine.createSpyObj('NotificationService', [
      'markAsRead', 'markAllAsRead', 'dismissNotification', 'clearAll'
    ], {
      notifications$: notificationsSubject.asObservable(),
      profileEditRequested$: new Subject<void>()
    });

    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [NotificationDropdownComponent],
      providers: [
        { provide: NotificationService, useValue: notifServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // ===== Initialization =====
  describe('initialization', () => {
    it('should load notifications from service', () => {
      expect(component.notifications.length).toBe(2);
    });

    it('should calculate unread count correctly', () => {
      // 1 unread (test-1), 1 read (test-2)
      expect(component.unreadCount).toBe(1);
    });

    it('should start with panel closed', () => {
      expect(component.isOpen).toBeFalse();
    });
  });

  // ===== Toggle =====
  describe('toggle()', () => {
    it('should open the panel when closed', () => {
      component.isOpen = false;
      component.toggle();
      expect(component.isOpen).toBeTrue();
    });

    it('should close the panel when open', () => {
      component.isOpen = true;
      component.toggle();
      expect(component.isOpen).toBeFalse();
    });
  });

  // ===== Badge Rendering =====
  describe('badge', () => {
    it('should show badge when there are unread notifications', () => {
      fixture.detectChanges();
      const badge = fixture.nativeElement.querySelector('.notif-badge');
      expect(badge).toBeTruthy();
      expect(badge.textContent.trim()).toBe('1');
    });

    it('should hide badge when all notifications are read', () => {
      const allRead = mockNotifications.map(n => ({ ...n, read: true }));
      notificationsSubject.next(allRead);
      fixture.detectChanges();

      const badge = fixture.nativeElement.querySelector('.notif-badge');
      expect(badge).toBeFalsy();
    });

    it('should show 9+ when unread count exceeds 9', () => {
      const manyUnread: AppNotification[] = Array.from({ length: 12 }, (_, i) => ({
        id: `notif-${i}`,
        type: 'rental_pending' as const,
        title: `Test ${i}`,
        message: `Message ${i}`,
        icon: 'bi-bell',
        iconColor: '#f59e0b',
        timestamp: new Date(),
        read: false
      }));
      notificationsSubject.next(manyUnread);
      fixture.detectChanges();

      const badge = fixture.nativeElement.querySelector('.notif-badge');
      expect(badge.textContent.trim()).toBe('9+');
    });
  });

  // ===== Panel =====
  describe('panel', () => {
    it('should render panel when isOpen is true', () => {
      component.isOpen = true;
      fixture.detectChanges();
      const panel = fixture.nativeElement.querySelector('.notif-panel');
      expect(panel).toBeTruthy();
    });

    it('should NOT render panel when isOpen is false', () => {
      component.isOpen = false;
      fixture.detectChanges();
      const panel = fixture.nativeElement.querySelector('.notif-panel');
      expect(panel).toBeFalsy();
    });

    it('should render notification items when panel is open', () => {
      component.isOpen = true;
      fixture.detectChanges();
      const items = fixture.nativeElement.querySelectorAll('.notif-item');
      expect(items.length).toBe(2);
    });

    it('should show empty state when no notifications', () => {
      notificationsSubject.next([]);
      component.isOpen = true;
      fixture.detectChanges();

      const empty = fixture.nativeElement.querySelector('.notif-empty');
      expect(empty).toBeTruthy();
      expect(empty.textContent).toContain('Sin notificaciones');
    });

    it('should mark unread notifications with notif-unread class', () => {
      component.isOpen = true;
      fixture.detectChanges();
      const items = fixture.nativeElement.querySelectorAll('.notif-item');
      expect(items[0].classList.contains('notif-unread')).toBeTrue();
      expect(items[1].classList.contains('notif-unread')).toBeFalse();
    });
  });

  // ===== Notification Click =====
  describe('onNotificationClick()', () => {
    it('should mark notification as read and navigate', () => {
      const notif = mockNotifications[0];
      component.onNotificationClick(notif);

      expect(notifServiceSpy.markAsRead).toHaveBeenCalledWith('test-1');
      expect(routerSpy.navigate).toHaveBeenCalledWith(['/admin/rentals']);
      expect(component.isOpen).toBeFalse();
    });

    it('should close panel but not navigate when no routerLink', () => {
      const notif: AppNotification = { ...mockNotifications[0], routerLink: undefined };
      component.onNotificationClick(notif);

      expect(notifServiceSpy.markAsRead).toHaveBeenCalled();
      expect(routerSpy.navigate).not.toHaveBeenCalled();
      expect(component.isOpen).toBeFalse();
    });
  });

  // ===== markAllRead =====
  describe('markAllRead()', () => {
    it('should call service markAllAsRead', () => {
      component.markAllRead();
      expect(notifServiceSpy.markAllAsRead).toHaveBeenCalled();
    });
  });

  // ===== clearAll =====
  describe('clearAll()', () => {
    it('should call service clearAll and close panel', () => {
      component.isOpen = true;
      component.clearAll();
      expect(notifServiceSpy.clearAll).toHaveBeenCalled();
      expect(component.isOpen).toBeFalse();
    });
  });

  // ===== dismiss =====
  describe('dismiss()', () => {
    it('should call service dismissNotification and stop event propagation', () => {
      const event = new Event('click');
      spyOn(event, 'stopPropagation');

      component.dismiss(event, 'test-1');

      expect(event.stopPropagation).toHaveBeenCalled();
      expect(notifServiceSpy.dismissNotification).toHaveBeenCalledWith('test-1');
    });
  });

  // ===== timeAgo =====
  describe('timeAgo()', () => {
    it('should return "Ahora" for very recent dates', () => {
      const now = new Date();
      expect(component.timeAgo(now)).toBe('Ahora');
    });

    it('should return minutes for dates within an hour', () => {
      const tenMinsAgo = new Date(Date.now() - 10 * 60 * 1000);
      expect(component.timeAgo(tenMinsAgo)).toBe('Hace 10m');
    });

    it('should return hours for dates within a day', () => {
      const threeHoursAgo = new Date(Date.now() - 3 * 60 * 60 * 1000);
      expect(component.timeAgo(threeHoursAgo)).toBe('Hace 3h');
    });

    it('should return days for older dates', () => {
      const twoDaysAgo = new Date(Date.now() - 2 * 24 * 60 * 60 * 1000);
      expect(component.timeAgo(twoDaysAgo)).toBe('Hace 2d');
    });
  });

  // ===== Click outside =====
  describe('onDocumentClick()', () => {
    it('should close panel on click outside', () => {
      component.isOpen = true;
      const outsideEvent = new Event('click');
      Object.defineProperty(outsideEvent, 'target', { value: document.body });
      component.onDocumentClick(outsideEvent);
      expect(component.isOpen).toBeFalse();
    });
  });

  // ===== Profile Incomplete Click =====
  describe('profile_incomplete notification click', () => {
    it('should emit profileEditRequested$ instead of navigating', () => {
      const profileNotif: AppNotification = {
        id: 'profile-1',
        type: 'profile_incomplete',
        title: 'Perfil incompleto',
        message: 'Datos pendientes.',
        icon: 'bi-person-exclamation',
        iconColor: '#f59e0b',
        timestamp: new Date(),
        read: false
      };

      spyOn(notifServiceSpy.profileEditRequested$, 'next');
      component.onNotificationClick(profileNotif);

      expect(notifServiceSpy.markAsRead).toHaveBeenCalledWith('profile-1');
      expect(notifServiceSpy.profileEditRequested$.next).toHaveBeenCalled();
      expect(routerSpy.navigate).not.toHaveBeenCalled();
      expect(component.isOpen).toBeFalse();
    });
  });
});
