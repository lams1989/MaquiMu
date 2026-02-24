import { Alquiler, EstadoAlquiler } from '@core/models/alquiler.model';
import { AlquilerService } from '@core/services/alquiler.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FacturaService } from '@core/services/factura.service';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '@shared/navbar/navbar.component';
import { SidebarComponent } from '@shared/sidebar/sidebar.component';

@Component({
  selector: 'app-admin-rentals',
  templateUrl: './admin-rentals.component.html',
  styleUrls: ['./admin-rentals.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, SidebarComponent, NavbarComponent]
})
export class AdminRentalsComponent implements OnInit {
  alquileres: Alquiler[] = [];
  filtroEstado: EstadoAlquiler | '' = '';
  loading = false;

  // Modal de rechazo
  showRejectModal = false;
  alquilerSeleccionado: Alquiler | null = null;
  motivoRechazo = '';

  // Detalle
  showDetail = false;
  alquilerDetalle: Alquiler | null = null;

  estados: { value: EstadoAlquiler | ''; label: string }[] = [
    { value: '', label: 'Todos' },
    { value: 'PENDIENTE', label: 'Pendientes' },
    { value: 'APROBADO', label: 'Aprobados' },
    { value: 'ACTIVO', label: 'Activos' },
    { value: 'FINALIZADO', label: 'Finalizados' },
    { value: 'RECHAZADO', label: 'Rechazados' },
    { value: 'CANCELADO', label: 'Cancelados' }
  ];

  constructor(
    private alquilerService: AlquilerService,
    private facturaService: FacturaService
  ) {}

  ngOnInit(): void {
    this.cargarAlquileres();
  }

  cargarAlquileres(): void {
    this.loading = true;
    const estado = this.filtroEstado || undefined;
    this.alquilerService.getAlquileres(estado as EstadoAlquiler | undefined).subscribe({
      next: (data) => {
        this.alquileres = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar alquileres', error);
        this.loading = false;
      }
    });
  }

  onFiltroChange(): void {
    this.cargarAlquileres();
  }

  // ===== Acciones =====

  aprobar(alquiler: Alquiler): void {
    if (!confirm('¿Está seguro de aprobar este alquiler?')) return;
    // TODO: Obtener usuarioId del contexto de autenticación
    const usuarioId = 1; // Placeholder - se reemplazará con el usuario autenticado
    this.alquilerService.aprobarAlquiler(alquiler.alquilerId!, usuarioId).subscribe({
      next: () => {
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al aprobar alquiler', error);
        alert('Error al aprobar el alquiler: ' + (error.error?.message || error.message));
      }
    });
  }

  abrirModalRechazo(alquiler: Alquiler): void {
    this.alquilerSeleccionado = alquiler;
    this.motivoRechazo = '';
    this.showRejectModal = true;
  }

  confirmarRechazo(): void {
    if (!this.alquilerSeleccionado) return;
    this.alquilerService.rechazarAlquiler(
      this.alquilerSeleccionado.alquilerId!,
      this.motivoRechazo || undefined
    ).subscribe({
      next: () => {
        this.showRejectModal = false;
        this.alquilerSeleccionado = null;
        this.motivoRechazo = '';
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al rechazar alquiler', error);
        alert('Error al rechazar el alquiler: ' + (error.error?.message || error.message));
      }
    });
  }

  cancelarRechazo(): void {
    this.showRejectModal = false;
    this.alquilerSeleccionado = null;
    this.motivoRechazo = '';
  }

  entregar(alquiler: Alquiler): void {
    if (!confirm('¿Confirma la entrega de la maquinaria al cliente? El estado cambiará a ACTIVO.')) return;
    this.alquilerService.entregarAlquiler(alquiler.alquilerId!).subscribe({
      next: () => {
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al entregar alquiler', error);
        alert('Error al registrar la entrega: ' + (error.error?.message || error.message));
      }
    });
  }

  finalizar(alquiler: Alquiler): void {
    if (!confirm('¿Confirma la devolución de la maquinaria? El alquiler se finalizará.')) return;
    this.alquilerService.finalizarAlquiler(alquiler.alquilerId!).subscribe({
      next: () => {
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al finalizar alquiler', error);
        alert('Error al finalizar el alquiler: ' + (error.error?.message || error.message));
      }
    });
  }

  generarFactura(alquiler: Alquiler): void {
    if (!confirm('¿Desea generar la factura para este alquiler?')) return;
    this.facturaService.generarFactura(alquiler.alquilerId!).subscribe({
      next: () => {
        alert('Factura generada exitosamente.');
      },
      error: (error) => {
        console.error('Error al generar factura', error);
        const msg = error.status === 409
          ? 'Ya existe una factura para este alquiler.'
          : 'Error al generar la factura: ' + (error.error?.message || error.message);
        alert(msg);
      }
    });
  }

  verDetalle(alquiler: Alquiler): void {
    this.alquilerDetalle = alquiler;
    this.showDetail = true;
  }

  cerrarDetalle(): void {
    this.showDetail = false;
    this.alquilerDetalle = null;
  }

  // ===== Helpers de estado =====

  getBadgeClass(estado: EstadoAlquiler): string {
    switch (estado) {
      case 'PENDIENTE': return 'bg-warning text-dark';
      case 'APROBADO': return 'bg-info text-white';
      case 'RECHAZADO': return 'bg-danger text-white';
      case 'ACTIVO': return 'bg-success text-white';
      case 'FINALIZADO': return 'bg-secondary text-white';
      case 'CANCELADO': return 'bg-dark text-white';
      default: return 'bg-light text-dark';
    }
  }

  getEstadoLabel(estado: EstadoAlquiler): string {
    switch (estado) {
      case 'PENDIENTE': return 'Pendiente';
      case 'APROBADO': return 'Aprobado';
      case 'RECHAZADO': return 'Rechazado';
      case 'ACTIVO': return 'Activo';
      case 'FINALIZADO': return 'Finalizado';
      case 'CANCELADO': return 'Cancelado';
      default: return estado;
    }
  }
}
