import { AlquilerService } from './alquiler.service';
import { AppNotification } from '@core/models/notification.model';
import { AuthService } from './auth/auth.service';
import { BehaviorSubject, Observable, Subscription, timer } from 'rxjs';
import { FacturaService } from './factura.service';
import { Inject, Injectable, OnDestroy, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { UsuarioService } from './usuario.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService implements OnDestroy {
  private notificationsSubject = new BehaviorSubject<AppNotification[]>([]);
  public notifications$: Observable<AppNotification[]> = this.notificationsSubject.asObservable();

  private pollSub?: Subscription;
  private authSub?: Subscription;
  private isBrowser: boolean;

  /** Tracking maps to detect state changes between polls */
  private knownRentalStates = new Map<number, string>();
  private knownInvoiceIds = new Set<number>();
  private knownPendingUserIds = new Set<number>();
  private knownResetUserIds = new Set<number>();
  private firstPoll = true;

  private readonly STORAGE_KEY = 'maquimu_notifications';
  private readonly POLL_INTERVAL = 30_000; // 30 seconds

  constructor(
    private authService: AuthService,
    private alquilerService: AlquilerService,
    private facturaService: FacturaService,
    private usuarioService: UsuarioService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
    this.loadFromStorage();

    this.authSub = this.authService.currentUser.subscribe(user => {
      if (user) {
        this.startPolling();
      } else {
        this.stopPolling();
        this.clear();
      }
    });
  }

  ngOnDestroy(): void {
    this.stopPolling();
    this.authSub?.unsubscribe();
  }

  // ========== Public API ==========

  get unreadCount(): number {
    return this.notificationsSubject.value.filter(n => !n.read).length;
  }

  markAsRead(id: string): void {
    const list = this.notificationsSubject.value.map(n =>
      n.id === id ? { ...n, read: true } : n
    );
    this.notificationsSubject.next(list);
    this.saveToStorage(list);
  }

  markAllAsRead(): void {
    const list = this.notificationsSubject.value.map(n => ({ ...n, read: true }));
    this.notificationsSubject.next(list);
    this.saveToStorage(list);
  }

  dismissNotification(id: string): void {
    const list = this.notificationsSubject.value.filter(n => n.id !== id);
    this.notificationsSubject.next(list);
    this.saveToStorage(list);
  }

  clearAll(): void {
    this.notificationsSubject.next([]);
    this.saveToStorage([]);
  }

  // ========== Polling ==========

  private startPolling(): void {
    this.stopPolling();
    // Immediate + every POLL_INTERVAL ms
    this.pollSub = timer(0, this.POLL_INTERVAL).subscribe(() => this.poll());
  }

  private stopPolling(): void {
    this.pollSub?.unsubscribe();
    this.pollSub = undefined;
  }

  private poll(): void {
    const user = this.authService.currentUserValue;
    if (!user) return;

    if (user.rol === 'OPERARIO') {
      this.pollAdmin();
    } else {
      this.pollClient();
    }
  }

  // ===== Admin poll =====
  private pollAdmin(): void {
    this.alquilerService.getAlquileres().subscribe({
      next: (rentals) => {
        if (this.firstPoll) {
          // Seed known states — don't generate notifications for existing data
          rentals.forEach(r => {
            if (r.alquilerId != null) this.knownRentalStates.set(r.alquilerId, r.estado);
          });
          // But show pending count as a summary notification
          const pending = rentals.filter(r => r.estado === 'PENDIENTE').length;
          if (pending > 0) {
            this.push({
              id: `admin-pending-summary-${Date.now()}`,
              type: 'rental_pending',
              title: 'Solicitudes pendientes',
              message: `Hay ${pending} solicitud(es) de alquiler esperando revisión.`,
              icon: 'bi-hourglass-split',
              iconColor: '#f59e0b',
              timestamp: new Date(),
              read: false,
              routerLink: '/admin/rentals'
            });
          }
        } else {
          // Detect new or changed rentals
          rentals.forEach(r => {
            if (r.alquilerId == null) return;
            const prev = this.knownRentalStates.get(r.alquilerId);
            if (prev === undefined && r.estado === 'PENDIENTE') {
              this.push({
                id: `rental-new-${r.alquilerId}-${Date.now()}`,
                type: 'rental_pending',
                title: 'Nueva solicitud de alquiler',
                message: `Se recibió la solicitud #${r.alquilerId}.`,
                icon: 'bi-bell-fill',
                iconColor: '#f59e0b',
                timestamp: new Date(),
                read: false,
                routerLink: '/admin/rentals'
              });
            }
            // Detect new extension requests
            if (prev && prev !== 'PENDIENTE_EXTENSION' && r.estado === 'PENDIENTE_EXTENSION') {
              this.push({
                id: `extension-new-${r.alquilerId}-${Date.now()}`,
                type: 'extension_pending',
                title: 'Solicitud de extensión',
                message: `El cliente solicita extensión del alquiler #${r.alquilerId}.`,
                icon: 'bi-calendar-plus',
                iconColor: '#6366f1',
                timestamp: new Date(),
                read: false,
                routerLink: '/admin/rentals'
              });
            }
          });
        }
        rentals.forEach(r => {
          if (r.alquilerId != null) this.knownRentalStates.set(r.alquilerId, r.estado);
        });
        this.firstPoll = false;
      }
    });

    // Poll for pending user registrations
    this.pollPendingRegistrations();

    // Poll for password reset requests
    this.pollResetRequests();
  }

  // ===== Pending registrations poll (admin only) =====
  private pollPendingRegistrations(): void {
    this.usuarioService.getUsuariosPendientes().subscribe({
      next: (users) => {
        if (this.knownPendingUserIds.size === 0 && users.length > 0) {
          // First time — seed known IDs and show summary
          users.forEach(u => this.knownPendingUserIds.add(u.usuarioId));
          this.push({
            id: `registration-pending-summary-${Date.now()}`,
            type: 'registration_pending',
            title: 'Registros pendientes',
            message: `Hay ${users.length} registro(s) de cliente pendientes de aprobación.`,
            icon: 'bi-person-plus-fill',
            iconColor: '#d97706',
            timestamp: new Date(),
            read: false,
            routerLink: '/admin/clients'
          });
        } else {
          // Detect new pending users
          users.forEach(u => {
            if (!this.knownPendingUserIds.has(u.usuarioId)) {
              this.knownPendingUserIds.add(u.usuarioId);
              this.push({
                id: `registration-new-${u.usuarioId}-${Date.now()}`,
                type: 'registration_pending',
                title: 'Nuevo registro de cliente',
                message: `Nuevo registro pendiente de aprobación: ${u.nombreCompleto}.`,
                icon: 'bi-person-plus-fill',
                iconColor: '#d97706',
                timestamp: new Date(),
                read: false,
                routerLink: '/admin/clients'
              });
            }
          });
        }
        // Update known set to current pending users (remove approved/rejected)
        const currentIds = new Set(users.map(u => u.usuarioId));
        this.knownPendingUserIds = currentIds;
      }
    });
  }

  // ===== Password reset requests poll (admin only) =====
  private pollResetRequests(): void {
    this.usuarioService.getUsuariosRestablecer().subscribe({
      next: (users) => {
        if (this.knownResetUserIds.size === 0 && users.length > 0) {
          users.forEach(u => this.knownResetUserIds.add(u.usuarioId));
          this.push({
            id: `reset-pending-summary-${Date.now()}`,
            type: 'password_reset_request',
            title: 'Solicitudes de restablecimiento',
            message: `Hay ${users.length} solicitud(es) de restablecimiento de contraseña.`,
            icon: 'bi-key-fill',
            iconColor: '#6366f1',
            timestamp: new Date(),
            read: false,
            routerLink: '/admin/clients'
          });
        } else {
          users.forEach(u => {
            if (!this.knownResetUserIds.has(u.usuarioId)) {
              this.knownResetUserIds.add(u.usuarioId);
              this.push({
                id: `reset-new-${u.usuarioId}-${Date.now()}`,
                type: 'password_reset_request',
                title: 'Solicitud de restablecimiento',
                message: `Solicitud de restablecimiento de contraseña: ${u.nombreCompleto} (${u.email}).`,
                icon: 'bi-key-fill',
                iconColor: '#6366f1',
                timestamp: new Date(),
                read: false,
                routerLink: '/admin/clients'
              });
            }
          });
        }
        const currentIds = new Set(users.map(u => u.usuarioId));
        this.knownResetUserIds = currentIds;
      }
    });
  }

  // ===== Client poll =====
  private pollClient(): void {
    this.alquilerService.getMisAlquileres().subscribe({
      next: (rentals) => {
        if (this.firstPoll) {
          rentals.forEach(r => {
            if (r.alquilerId != null) this.knownRentalStates.set(r.alquilerId, r.estado);
          });
          // Show summary for client
          const active = rentals.filter(r => r.estado === 'ACTIVO').length;
          const pending = rentals.filter(r => r.estado === 'PENDIENTE').length;
          if (pending > 0) {
            this.push({
              id: `client-pending-summary-${Date.now()}`,
              type: 'rental_pending',
              title: 'Solicitudes en espera',
              message: `Tienes ${pending} solicitud(es) pendientes de aprobación.`,
              icon: 'bi-hourglass-split',
              iconColor: '#f59e0b',
              timestamp: new Date(),
              read: false,
              routerLink: '/client/my-rentals'
            });
          }
          if (active > 0) {
            this.push({
              id: `client-active-summary-${Date.now()}`,
              type: 'rental_active',
              title: 'Alquileres activos',
              message: `Tienes ${active} alquiler(es) activos actualmente.`,
              icon: 'bi-truck-front-fill',
              iconColor: '#3b82f6',
              timestamp: new Date(),
              read: false,
              routerLink: '/client/my-rentals'
            });
          }
        } else {
          // Detect state changes
          rentals.forEach(r => {
            if (r.alquilerId == null) return;
            const prev = this.knownRentalStates.get(r.alquilerId);
            if (prev && prev !== r.estado) {
              this.pushRentalChangeNotification(r.alquilerId, r.estado, prev);
            }
          });
        }
        rentals.forEach(r => {
          if (r.alquilerId != null) this.knownRentalStates.set(r.alquilerId, r.estado);
        });
        this.firstPoll = false;
      }
    });

    // Also poll invoices for client
    this.facturaService.getMisFacturas().subscribe({
      next: (invoices) => {
        invoices.forEach(f => {
          if (f.facturaId != null && !this.knownInvoiceIds.has(f.facturaId)) {
            if (!this.firstPoll) {
              this.push({
                id: `invoice-new-${f.facturaId}-${Date.now()}`,
                type: 'invoice_generated',
                title: 'Nueva factura',
                message: `Se generó la factura #${f.facturaId} por $${f.monto.toLocaleString()}.`,
                icon: 'bi-receipt',
                iconColor: '#f43f5e',
                timestamp: new Date(),
                read: false,
                routerLink: '/client/my-invoices'
              });
            }
            this.knownInvoiceIds.add(f.facturaId);
          }
        });
      }
    });
  }

  private pushRentalChangeNotification(id: number, newState: string, oldState: string): void {
    // Special case: extension approved (PENDIENTE_EXTENSION -> ACTIVO)
    if (oldState === 'PENDIENTE_EXTENSION' && newState === 'ACTIVO') {
      this.push({
        id: `extension-result-${id}-${Date.now()}`,
        type: 'extension_approved',
        title: 'Extensión aprobada',
        message: `Tu extensión para el alquiler #${id} fue aprobada. Fecha y costo actualizados.`,
        icon: 'bi-calendar-check',
        iconColor: '#22c55e',
        timestamp: new Date(),
        read: false,
        routerLink: '/client/my-rentals'
      });
      return;
    }

    const map: Record<string, { title: string; msg: string; icon: string; color: string }> = {
      'APROBADO': {
        title: 'Alquiler aprobado',
        msg: `Tu solicitud #${id} fue aprobada. ¡Pronto recibirás la maquinaria!`,
        icon: 'bi-check-circle-fill',
        color: '#22c55e'
      },
      'RECHAZADO': {
        title: 'Alquiler rechazado',
        msg: `Tu solicitud #${id} fue rechazada.`,
        icon: 'bi-x-circle-fill',
        color: '#ef4444'
      },
      'ACTIVO': {
        title: 'Maquinaria entregada',
        msg: `El alquiler #${id} está activo. La maquinaria fue entregada.`,
        icon: 'bi-truck-front-fill',
        color: '#3b82f6'
      },
      'FINALIZADO': {
        title: 'Alquiler finalizado',
        msg: `El alquiler #${id} ha sido finalizado correctamente.`,
        icon: 'bi-flag-fill',
        color: '#8b5cf6'
      },
      'CANCELADO': {
        title: 'Alquiler cancelado',
        msg: `El alquiler #${id} fue cancelado.`,
        icon: 'bi-slash-circle-fill',
        color: '#64748b'
      },
      'PENDIENTE_EXTENSION': {
        title: 'Extensión en revisión',
        msg: `Tu solicitud de extensión para el alquiler #${id} está siendo revisada.`,
        icon: 'bi-calendar-plus',
        color: '#6366f1'
      }
    };

    const info = map[newState];
    if (!info) return;

    this.push({
      id: `rental-change-${id}-${newState}-${Date.now()}`,
      type: newState === 'APROBADO' ? 'rental_approved' :
            newState === 'RECHAZADO' ? 'rental_rejected' :
            newState === 'ACTIVO' ? 'rental_active' :
            newState === 'FINALIZADO' ? 'rental_finalized' :
            newState === 'PENDIENTE_EXTENSION' ? 'extension_pending' : 'system',
      title: info.title,
      message: info.msg,
      icon: info.icon,
      iconColor: info.color,
      timestamp: new Date(),
      read: false,
      routerLink: '/client/my-rentals'
    });
  }

  // ========== Helpers ==========

  private push(notification: AppNotification): void {
    const current = this.notificationsSubject.value;
    // Keep max 50 notifications, newest first
    const updated = [notification, ...current].slice(0, 50);
    this.notificationsSubject.next(updated);
    this.saveToStorage(updated);
  }

  private clear(): void {
    this.knownRentalStates.clear();
    this.knownInvoiceIds.clear();
    this.knownPendingUserIds.clear();
    this.knownResetUserIds.clear();
    this.firstPoll = true;
    this.notificationsSubject.next([]);
    if (this.isBrowser) {
      localStorage.removeItem(this.STORAGE_KEY);
    }
  }

  private saveToStorage(list: AppNotification[]): void {
    if (!this.isBrowser) return;
    try {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(list));
    } catch { /* quota exceeded — ignore */ }
  }

  private loadFromStorage(): void {
    if (!this.isBrowser) return;
    try {
      const raw = localStorage.getItem(this.STORAGE_KEY);
      if (raw) {
        const parsed: AppNotification[] = JSON.parse(raw).map((n: any) => ({
          ...n,
          timestamp: new Date(n.timestamp)
        }));
        this.notificationsSubject.next(parsed);
      }
    } catch { /* corrupt data — ignore */ }
  }
}
