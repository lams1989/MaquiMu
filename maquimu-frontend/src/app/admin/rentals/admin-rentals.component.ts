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
  feedbackMessage = '';
  feedbackType: 'success' | 'danger' = 'danger';

  // Modal de rechazo
  showRejectModal = false;
  alquilerSeleccionado: Alquiler | null = null;
  motivoRechazo = '';

  // Detalle
  showDetail = false;
  alquilerDetalle: Alquiler | null = null;

  // Modal de confirmación
  showConfirmModal = false;
  confirmMessage = '';
  confirmAction: 'aprobar' | 'entregar' | 'finalizar' | 'facturar' | null = null;
  confirmAlquiler: Alquiler | null = null;

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

  solicitarAprobar(alquiler: Alquiler): void {
    this.abrirConfirmacion('aprobar', alquiler, '¿Está seguro de aprobar este alquiler?');
  }

  private aprobar(alquiler: Alquiler): void {
    // TODO: Obtener usuarioId del contexto de autenticación
    const usuarioId = 1; // Placeholder - se reemplazará con el usuario autenticado
    this.alquilerService.aprobarAlquiler(alquiler.alquilerId!, usuarioId).subscribe({
      next: () => {
        this.setFeedback('success', 'Alquiler aprobado exitosamente.');
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al aprobar alquiler', error);
        this.setFeedback('danger', 'Error al aprobar el alquiler: ' + (error.error?.message || error.message));
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
        this.setFeedback('success', 'Alquiler rechazado correctamente.');
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al rechazar alquiler', error);
        this.setFeedback('danger', 'Error al rechazar el alquiler: ' + (error.error?.message || error.message));
      }
    });
  }

  cancelarRechazo(): void {
    this.showRejectModal = false;
    this.alquilerSeleccionado = null;
    this.motivoRechazo = '';
  }

  solicitarEntregar(alquiler: Alquiler): void {
    this.abrirConfirmacion('entregar', alquiler, '¿Confirma la entrega de la maquinaria al cliente? El estado cambiará a ACTIVO.');
  }

  private entregar(alquiler: Alquiler): void {
    this.alquilerService.entregarAlquiler(alquiler.alquilerId!).subscribe({
      next: () => {
        this.setFeedback('success', 'Entrega registrada correctamente.');
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al entregar alquiler', error);
        this.setFeedback('danger', 'Error al registrar la entrega: ' + (error.error?.message || error.message));
      }
    });
  }

  solicitarFinalizar(alquiler: Alquiler): void {
    this.abrirConfirmacion('finalizar', alquiler, '¿Confirma la devolución de la maquinaria? El alquiler se finalizará.');
  }

  private finalizar(alquiler: Alquiler): void {
    this.alquilerService.finalizarAlquiler(alquiler.alquilerId!).subscribe({
      next: () => {
        this.setFeedback('success', 'Alquiler finalizado correctamente.');
        this.cargarAlquileres();
      },
      error: (error) => {
        console.error('Error al finalizar alquiler', error);
        this.setFeedback('danger', 'Error al finalizar el alquiler: ' + (error.error?.message || error.message));
      }
    });
  }

  solicitarGenerarFactura(alquiler: Alquiler): void {
    this.abrirConfirmacion('facturar', alquiler, '¿Desea generar la factura para este alquiler?');
  }

  private generarFactura(alquiler: Alquiler): void {
    this.facturaService.generarFactura(alquiler.alquilerId!).subscribe({
      next: () => {
        this.setFeedback('success', 'Factura generada exitosamente.');
      },
      error: (error) => {
        console.error('Error al generar factura', error);
        const backendMessage = error?.error?.message || error?.error?.error;
        const msg = backendMessage
          ? backendMessage
          : 'Error al generar la factura: ' + (error.message || 'Intenta nuevamente.');
        this.setFeedback('danger', msg);
      }
    });
  }

  confirmarAccion(): void {
    if (!this.confirmAction || !this.confirmAlquiler) {
      return;
    }

    const alquiler = this.confirmAlquiler;
    const action = this.confirmAction;
    this.cerrarConfirmacion();

    if (action === 'aprobar') {
      this.aprobar(alquiler);
    } else if (action === 'entregar') {
      this.entregar(alquiler);
    } else if (action === 'finalizar') {
      this.finalizar(alquiler);
    } else if (action === 'facturar') {
      this.generarFactura(alquiler);
    }
  }

  cerrarConfirmacion(): void {
    this.showConfirmModal = false;
    this.confirmMessage = '';
    this.confirmAction = null;
    this.confirmAlquiler = null;
  }

  private abrirConfirmacion(action: 'aprobar' | 'entregar' | 'finalizar' | 'facturar', alquiler: Alquiler, message: string): void {
    this.confirmAction = action;
    this.confirmAlquiler = alquiler;
    this.confirmMessage = message;
    this.showConfirmModal = true;
  }

  private setFeedback(type: 'success' | 'danger', message: string): void {
    this.feedbackType = type;
    this.feedbackMessage = message;
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
