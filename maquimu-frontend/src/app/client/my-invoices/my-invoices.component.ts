import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EstadoPago, Factura } from '@core/models/factura.model';
import { FacturaService } from '@core/services/factura.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-invoices',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-invoices.component.html',
  styleUrl: './my-invoices.component.css'
})
export class MyInvoicesComponent implements OnInit {
  facturas: Factura[] = [];
  facturasFiltradas: Factura[] = [];
  filtroEstado: EstadoPago | '' = '';
  loading = true;
  errorMessage = '';

  // Detalle
  showDetalle = false;
  facturaDetalle: Factura | null = null;

  estados: { value: EstadoPago | ''; label: string }[] = [
    { value: '', label: 'Todos' },
    { value: 'PENDIENTE', label: 'Pendientes' },
    { value: 'PAGADO', label: 'Pagados' },
    { value: 'ANULADO', label: 'Anulados' }
  ];

  constructor(private facturaService: FacturaService) {}

  ngOnInit(): void {
    this.cargarFacturas();
  }

  cargarFacturas(): void {
    this.loading = true;
    this.errorMessage = '';
    this.facturaService.getMisFacturas().subscribe({
      next: (data) => {
        this.facturas = data;
        this.aplicarFiltro();
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar facturas', err);
        this.errorMessage = 'No se pudieron cargar tus facturas. Intenta nuevamente.';
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

  verDetalle(factura: Factura): void {
    this.facturaDetalle = factura;
    this.showDetalle = true;
  }

  cerrarDetalle(): void {
    this.showDetalle = false;
    this.facturaDetalle = null;
  }

  descargarPdf(facturaId: number): void {
    this.facturaService.descargarMiPdf(facturaId).subscribe({
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
        alert('No se pudo descargar el PDF. Intenta nuevamente.');
      }
    });
  }

  getBadgeClass(estado: EstadoPago): string {
    switch (estado) {
      case 'PENDIENTE': return 'bg-warning text-dark';
      case 'PAGADO': return 'bg-success';
      case 'ANULADO': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }

  formatCurrency(monto: number): string {
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', minimumFractionDigits: 0 }).format(monto);
  }
}
