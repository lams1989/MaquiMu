import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { DashboardCliente, DashboardService } from '../../core/services/dashboard.service';
import { RouterModule } from '@angular/router';
import { Usuario } from '../../core/models/auth/login-register.models';

@Component({
  selector: 'app-portal',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './portal.component.html',
  styleUrl: './portal.component.css'
})
export class PortalComponent implements OnInit {
  currentUser: Usuario | null = null;
  stats: DashboardCliente | null = null;
  loading = true;
  error = '';

  constructor(
    private authService: AuthService,
    private dashboardService: DashboardService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.currentUser = user;
    });

    this.dashboardService.getClienteStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando dashboard cliente:', err);
        this.error = 'No se pudieron cargar las estadísticas';
        this.loading = false;
      }
    });
  }
}
