import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EstadoPago, Factura } from '@core/models/factura.model';
import { FacturaService } from '@core/services/factura.service';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '@shared/navbar/navbar.component';
import { SidebarComponent } from '@shared/sidebar/sidebar.component';

@Component({
  selector: 'app-financial',
  standalone: true,
  imports: [CommonModule, FormsModule, SidebarComponent, NavbarComponent],
  templateUrl: './financial.component.html',
  styleUrl: './financial.component.css'
})
export class FinancialComponent implements OnInit {
  facturas: Factura[] = [];
  facturasFiltradas: Factura[] = [];
  filtroEstado: EstadoPago | '' = '';
  loading = true;
  errorMessage = '';

  // Modal detalle
  showDetalle = false;
  facturaDetalle: Factura | null = null;

  // Modal cambiar estado
  showCambiarEstado = false;
  facturaEstado: Factura | null = null;
  nuevoEstado: EstadoPago = 'PAGADO';
  procesandoEstado = false;

  estadosFiltro: { value: EstadoPago | ''; label: string }[] = [
    { value: '', label: 'Todos' },
    { value: 'PENDIENTE', label: 'Pendientes' },
    { value: 'PAGADO', label: 'Pagadas' },
    { value: 'ANULADO', label: 'Anuladas' }
  ];

  constructor(private facturaService: FacturaService) {}

  ngOnInit(): void {
    this.cargarFacturas();
  }

  cargarFacturas(): void {
    this.loading = true;
    this.errorMessage = '';
    this.facturaService.getFacturas().subscribe({
      next: (data) => {
        this.facturas = data;
        this.aplicarFiltro();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar facturas', err);
        this.errorMessage = 'No se pudieron cargar las facturas. Intenta nuevamente.';
        this.loading = false;
      }
    });
  }

  aplicarFiltro(): void {
    if (this.filtroEstado) {
      this.facturasFiltradas = this.facturas.filter(f => f.estadoPago === this.filtroEstado);
    } else {
      this.facturasFiltradas = [...this.facturas];
    }
  }

  // === MODAL DETALLE ===

  verDetalle(factura: Factura): void {
    this.facturaDetalle = factura;
    this.showDetalle = true;
  }

  cerrarDetalle(): void {
    this.showDetalle = false;
    this.facturaDetalle = null;
  }

  // === DESCARGAR PDF ===

  descargarPdf(facturaId: number): void {
    this.facturaService.descargarPdf(facturaId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `factura-${String(facturaId).padStart(6, '0')}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error('Error al descargar PDF', err);
        this.errorMessage = 'Error al descargar el PDF de la factura.';
      }
    });
  }

  // === CAMBIAR ESTADO ===

  abrirCambiarEstado(factura: Factura): void {
    this.facturaEstado = factura;
    this.nuevoEstado = factura.estadoPago === 'PENDIENTE' ? 'PAGADO' : 'ANULADO';
    this.showCambiarEstado = true;
  }

  cerrarCambiarEstado(): void {
    this.showCambiarEstado = false;
    this.facturaEstado = null;
    this.procesandoEstado = false;
  }

  confirmarCambiarEstado(): void {
    if (!this.facturaEstado) return;
    this.procesandoEstado = true;
    this.facturaService.actualizarEstadoPago(this.facturaEstado.facturaId!, this.nuevoEstado).subscribe({
      next: (facturaActualizada) => {
        // Actualizar en lista local
        const idx = this.facturas.findIndex(f => f.facturaId === facturaActualizada.facturaId);
        if (idx !== -1) {
          this.facturas[idx] = facturaActualizada;
        }
        this.aplicarFiltro();
        this.cerrarCambiarEstado();
      },
      error: (err) => {
        console.error('Error al actualizar estado', err);
        this.procesandoEstado = false;
        this.errorMessage = err.error?.message || 'Error al actualizar el estado de pago.';
      }
    });
  }

  // === HELPERS ===

  getBadgeClass(estado: string): string {
    const clases: Record<string, string> = {
      'PENDIENTE': 'bg-warning text-dark',
      'PAGADO': 'bg-success',
      'ANULADO': 'bg-danger'
    };
    return clases[estado] || 'bg-secondary';
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(value);
  }

  getTotalMonto(): number {
    return this.facturasFiltradas.reduce((sum, f) => sum + f.monto, 0);
  }

  getTotalPendiente(): number {
    return this.facturas.filter(f => f.estadoPago === 'PENDIENTE').reduce((sum, f) => sum + f.monto, 0);
  }

  getTotalPagado(): number {
    return this.facturas.filter(f => f.estadoPago === 'PAGADO').reduce((sum, f) => sum + f.monto, 0);
  }
}
