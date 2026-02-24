import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { filter } from 'rxjs/operators';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { NotificationDropdownComponent } from '../../shared/notification-dropdown/notification-dropdown.component';

@Component({
  selector: 'app-client-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, NotificationDropdownComponent],
  templateUrl: './client-layout.component.html',
  styleUrl: './client-layout.component.css'
})
export class ClientLayoutComponent implements OnInit {
  showSidebar = true;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.checkRoute(this.router.url);
    this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe((e: any) => this.checkRoute(e.urlAfterRedirects || e.url));
  }

  private checkRoute(url: string): void {
    this.showSidebar = !url.endsWith('/portal') && !url.endsWith('/client');
  }

  logout(): void {
    this.authService.logout();
  }
}
