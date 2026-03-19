import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { DashboardOperario, DashboardService } from '@core/services/dashboard.service';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  stats: DashboardOperario | null = null;
  loading = true;
  errorMessage = '';

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.cargarStats();
  }

  cargarStats(): void {
    this.loading = true;
    this.errorMessage = '';
    this.dashboardService.getOperarioStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar dashboard', err);
        this.errorMessage = 'No se pudieron cargar las estadísticas.';
        this.loading = false;
      }
    });
  }
}
