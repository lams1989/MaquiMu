import { AppNotification } from '@core/models/notification.model';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener, OnDestroy, OnInit } from '@angular/core';
import { NotificationService } from '@core/services/notification.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-notification-dropdown',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-dropdown.component.html',
  styleUrl: './notification-dropdown.component.css'
})
export class NotificationDropdownComponent implements OnInit, OnDestroy {
  notifications: AppNotification[] = [];
  unreadCount = 0;
  isOpen = false;

  private sub?: Subscription;

  constructor(
    public notifService: NotificationService,
    private router: Router,
    private elRef: ElementRef
  ) {}

  ngOnInit(): void {
    this.sub = this.notifService.notifications$.subscribe(list => {
      this.notifications = list;
      this.unreadCount = list.filter(n => !n.read).length;
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  toggle(): void {
    this.isOpen = !this.isOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (!this.elRef.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }

  onNotificationClick(notif: AppNotification): void {
    this.notifService.markAsRead(notif.id);
    this.isOpen = false;
    if (notif.type === 'profile_incomplete') {
      this.notifService.profileEditRequested$.next();
      return;
    }
    if (notif.routerLink) {
      this.router.navigate([notif.routerLink]);
    }
  }

  markAllRead(): void {
    this.notifService.markAllAsRead();
  }

  clearAll(): void {
    this.notifService.clearAll();
    this.isOpen = false;
  }

  dismiss(event: Event, id: string): void {
    event.stopPropagation();
    this.notifService.dismissNotification(id);
  }

  timeAgo(date: Date): string {
    const now = new Date();
    const diff = now.getTime() - new Date(date).getTime();
    const mins = Math.floor(diff / 60000);
    if (mins < 1) return 'Ahora';
    if (mins < 60) return `Hace ${mins}m`;
    const hours = Math.floor(mins / 60);
    if (hours < 24) return `Hace ${hours}h`;
    const days = Math.floor(hours / 24);
    return `Hace ${days}d`;
  }
}
