import { Alquiler, EstadoAlquiler } from '@core/models/alquiler.model';
import { AlquilerService } from '@core/services/alquiler.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-my-rentals',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './my-rentals.component.html',
  styleUrl: './my-rentals.component.css'
})
export class MyRentalsComponent implements OnInit {
  alquileres: Alquiler[] = [];
  alquileresFiltrados: Alquiler[] = [];
  filtroEstado: EstadoAlquiler | '' = '';
  loading = true;
  errorMessage = '';

  alquilerDetalle: Alquiler | null = null;
  loadingDetalle = false;

  estados: { value: EstadoAlquiler | ''; label: string }[] = [
    { value: '', label: 'Todos' },
    { value: 'PENDIENTE', label: 'Pendientes' },
    { value: 'APROBADO', label: 'Aprobados' },
    { value: 'ACTIVO', label: 'Activos' },
    { value: 'FINALIZADO', label: 'Finalizados' },
    { value: 'RECHAZADO', label: 'Rechazados' },
    { value: 'CANCELADO', label: 'Cancelados' }
  ];

  constructor(private alquilerService: AlquilerService) {}

  ngOnInit(): void {
    this.cargarAlquileres();
  }

  cargarAlquileres(): void {
    this.loading = true;
    this.errorMessage = '';
    this.alquilerService.getMisAlquileres().subscribe({
      next: (data) => {
        this.alquileres = data;
        this.aplicarFiltro();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar alquileres', err);
        this.errorMessage = 'No se pudieron cargar tus alquileres. Intenta nuevamente.';
        this.loading = false;
      }
    });
  }

  aplicarFiltro(): void {
    if (this.filtroEstado) {
      this.alquileresFiltrados = this.alquileres.filter(a => a.estado === this.filtroEstado);
    } else {
      this.alquileresFiltrados = [...this.alquileres];
    }
  }

  verDetalle(id: number): void {
    this.loadingDetalle = true;
    this.alquilerService.getMiAlquilerDetalle(id).subscribe({
      next: (data) => {
        this.alquilerDetalle = data;
        this.loadingDetalle = false;
      },
      error: (err) => {
        console.error('Error al cargar detalle', err);
        this.loadingDetalle = false;
      }
    });
  }

  cerrarDetalle(): void {
    this.alquilerDetalle = null;
  }

  getBadgeClass(estado: string): string {
    const clases: Record<string, string> = {
      'PENDIENTE': 'bg-warning text-dark',
      'APROBADO': 'bg-info text-dark',
      'ACTIVO': 'bg-success',
      'FINALIZADO': 'bg-secondary',
      'RECHAZADO': 'bg-danger',
      'CANCELADO': 'bg-dark'
    };
    return clases[estado] || 'bg-secondary';
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', minimumFractionDigits: 0 }).format(value);
  }
}
