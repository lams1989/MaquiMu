import { Alquiler, EstadoAlquiler } from '@core/models/alquiler.model';
import { AlquilerService } from '@core/services/alquiler.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Maquinaria } from '@core/models/maquinaria.model';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { RouterModule } from '@angular/router';
import { SensitiveDataService } from '@core/services/sensitive-data.service';

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
  showDetalle = false;

  // Extension modal
  showExtensionModal = false;
  extensionAlquiler: Alquiler | null = null;
  extensionMaquinaria: Maquinaria | null = null;
  nuevaFechaFin = '';
  costoAdicionalEstimado = 0;
  loadingExtension = false;
  extensionError = '';
  extensionSuccess = '';

  estados: { value: EstadoAlquiler | ''; label: string }[] = [
    { value: '', label: 'Todos' },
    { value: 'PENDIENTE', label: 'Pendientes' },
    { value: 'APROBADO', label: 'Aprobados' },
    { value: 'ACTIVO', label: 'Activos' },
    { value: 'PENDIENTE_EXTENSION', label: 'Extensión Pendiente' },
    { value: 'FINALIZADO', label: 'Finalizados' },
    { value: 'RECHAZADO', label: 'Rechazados' },
    { value: 'CANCELADO', label: 'Cancelados' }
  ];

  constructor(
    private alquilerService: AlquilerService,
    private maquinariaService: MaquinariaService,
    public sensitiveData: SensitiveDataService
  ) {}

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
    this.showDetalle = true;
    this.loadingDetalle = true;
    this.alquilerService.getMiAlquilerDetalle(id).subscribe({
      next: (data) => {
        this.alquilerDetalle = data;
        this.loadingDetalle = false;
      },
      error: (err) => {
        console.error('Error al cargar detalle', err);
        this.loadingDetalle = false;
        this.showDetalle = false;
      }
    });
  }

  cerrarDetalle(): void {
    this.showDetalle = false;
    this.alquilerDetalle = null;
  }

  getBadgeClass(estado: string): string {
    const clases: Record<string, string> = {
      'PENDIENTE': 'bg-warning text-dark',
      'APROBADO': 'bg-info text-dark',
      'ACTIVO': 'bg-success',
      'PENDIENTE_EXTENSION': 'bg-primary',
      'FINALIZADO': 'bg-secondary',
      'RECHAZADO': 'bg-danger',
      'CANCELADO': 'bg-dark'
    };
    return clases[estado] || 'bg-secondary';
  }

  getEstadoLabel(estado: string): string {
    const labels: Record<string, string> = {
      'PENDIENTE': 'Pendiente',
      'APROBADO': 'Aprobado',
      'ACTIVO': 'Activo',
      'PENDIENTE_EXTENSION': 'Extensión Pendiente',
      'FINALIZADO': 'Finalizado',
      'RECHAZADO': 'Rechazado',
      'CANCELADO': 'Cancelado'
    };
    return labels[estado] || estado;
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', minimumFractionDigits: 0 }).format(value);
  }

  // ===== Extensión de alquiler =====

  abrirExtensionModal(alquiler: Alquiler): void {
    this.extensionAlquiler = alquiler;
    this.extensionMaquinaria = null;
    this.nuevaFechaFin = '';
    this.costoAdicionalEstimado = 0;
    this.extensionError = '';
    this.extensionSuccess = '';
    this.showExtensionModal = true;

    // Cargar datos de la maquinaria para calcular tarifa
    this.maquinariaService.getMaquinariaById(alquiler.maquinariaId).subscribe({
      next: (maq) => this.extensionMaquinaria = maq,
      error: () => this.extensionError = 'No se pudo cargar la información de la maquinaria.'
    });
  }

  calcularCostoAdicional(): void {
    if (!this.extensionAlquiler || !this.extensionMaquinaria || !this.nuevaFechaFin) {
      this.costoAdicionalEstimado = 0;
      return;
    }

    const fechaFinActual = new Date(this.extensionAlquiler.fechaFin);
    const nuevaFecha = new Date(this.nuevaFechaFin + 'T00:00:00');

    if (nuevaFecha <= fechaFinActual) {
      this.costoAdicionalEstimado = 0;
      return;
    }

    const diffTime = nuevaFecha.getTime() - fechaFinActual.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    this.costoAdicionalEstimado = diffDays * this.extensionMaquinaria.tarifaPorDia;
  }

  getMinFechaExtension(): string {
    if (!this.extensionAlquiler) return '';
    // Next day after current end date
    const fechaFin = new Date(this.extensionAlquiler.fechaFin);
    fechaFin.setDate(fechaFin.getDate() + 1);
    return fechaFin.toISOString().split('T')[0];
  }

  confirmarExtension(): void {
    if (!this.extensionAlquiler || !this.nuevaFechaFin) return;

    this.loadingExtension = true;
    this.extensionError = '';

    const nuevaFechaFinISO = this.nuevaFechaFin + 'T23:59:59';

    this.alquilerService.solicitarExtension(this.extensionAlquiler.alquilerId!, {
      nuevaFechaFin: nuevaFechaFinISO
    }).subscribe({
      next: () => {
        this.loadingExtension = false;
        this.showExtensionModal = false;
        this.extensionSuccess = 'Solicitud de extensión enviada exitosamente. El operario revisará tu petición.';
        this.cargarAlquileres();
        setTimeout(() => this.extensionSuccess = '', 5000);
      },
      error: (err) => {
        this.loadingExtension = false;
        this.extensionError = err.error?.message || err.error?.error || 'Error al solicitar la extensión. Intenta nuevamente.';
      }
    });
  }

  cerrarExtensionModal(): void {
    this.showExtensionModal = false;
    this.extensionAlquiler = null;
    this.extensionMaquinaria = null;
  }
}
