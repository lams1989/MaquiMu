import { AlquilerService } from '@core/services/alquiler.service';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Maquinaria } from '@core/models/maquinaria.model';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { Router } from '@angular/router';
import { SolicitudAlquiler } from '@core/models/alquiler.model';

@Component({
  selector: 'app-request-rental',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './request-rental.component.html',
  styleUrl: './request-rental.component.css'
})
export class RequestRentalComponent implements OnInit {
  rentalForm: FormGroup;
  maquinariasDisponibles: Maquinaria[] = [];
  maquinariaSeleccionada: Maquinaria | null = null;
  costoEstimado: number = 0;
  loading = false;
  searching = false;
  errorMessage = '';
  successMessage = '';
  minDate: string;
  clienteId: number | null = null;
  private isBrowser: boolean;

  constructor(
    private fb: FormBuilder,
    private alquilerService: AlquilerService,
    private maquinariaService: MaquinariaService,
    private router: Router,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    // Establecer fecha mínima como hoy
    const today = new Date();
    this.minDate = today.toISOString().slice(0, 16);

    this.rentalForm = this.fb.group({
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required],
      maquinariaId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      // Obtener clienteId del usuario logueado
      const userJson = localStorage.getItem('currentUser');
      if (userJson) {
        const user = JSON.parse(userJson);
        // El clienteId viene en la respuesta de login para usuarios CLIENTE
        this.clienteId = user.clienteId;
      }
    }
  }

  buscarMaquinariasDisponibles(): void {
    const { fechaInicio, fechaFin } = this.rentalForm.value;

    if (!fechaInicio || !fechaFin) {
      this.errorMessage = 'Por favor seleccione las fechas de inicio y fin';
      return;
    }

    if (new Date(fechaFin) <= new Date(fechaInicio)) {
      this.errorMessage = 'La fecha de fin debe ser posterior a la fecha de inicio';
      return;
    }

    this.searching = true;
    this.errorMessage = '';
    this.maquinariasDisponibles = [];
    this.maquinariaSeleccionada = null;

    this.alquilerService.getMaquinariasDisponibles(fechaInicio, fechaFin).subscribe({
      next: (maquinarias) => {
        this.maquinariasDisponibles = maquinarias;
        this.searching = false;
        if (maquinarias.length === 0) {
          this.errorMessage = 'No hay maquinarias disponibles para las fechas seleccionadas';
        }
      },
      error: (error) => {
        console.error('Error al buscar maquinarias:', error);
        this.errorMessage = 'Error al buscar maquinarias disponibles';
        this.searching = false;
      }
    });
  }

  seleccionarMaquinaria(maquinaria: Maquinaria): void {
    this.maquinariaSeleccionada = maquinaria;
    this.rentalForm.patchValue({ maquinariaId: maquinaria.maquinariaId });
    this.calcularCostoEstimado();
  }

  calcularCostoEstimado(): void {
    if (!this.maquinariaSeleccionada) return;

    const { fechaInicio, fechaFin } = this.rentalForm.value;
    if (!fechaInicio || !fechaFin) return;

    const inicio = new Date(fechaInicio);
    const fin = new Date(fechaFin);
    const diffTime = Math.abs(fin.getTime() - inicio.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    this.costoEstimado = diffDays * this.maquinariaSeleccionada.tarifaPorDia;
  }

  onSubmit(): void {
    if (this.rentalForm.invalid) {
      this.errorMessage = 'Por favor complete todos los campos';
      return;
    }

    if (!this.clienteId) {
      this.errorMessage = 'Error: No se pudo identificar el cliente';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const solicitud: SolicitudAlquiler = {
      clienteId: this.clienteId,
      maquinariaId: this.rentalForm.value.maquinariaId,
      fechaInicio: this.rentalForm.value.fechaInicio,
      fechaFin: this.rentalForm.value.fechaFin
    };

    this.alquilerService.solicitarAlquiler(solicitud).subscribe({
      next: (alquiler) => {
        this.loading = false;
        this.successMessage = `¡Solicitud de alquiler creada exitosamente! ID: ${alquiler.alquilerId}. Estado: ${alquiler.estado}`;

        // Limpiar formulario
        this.rentalForm.reset();
        this.maquinariasDisponibles = [];
        this.maquinariaSeleccionada = null;
        this.costoEstimado = 0;
      },
      error: (error) => {
        console.error('Error al solicitar alquiler:', error);
        this.loading = false;
        this.errorMessage = error.error?.message || 'Error al procesar la solicitud de alquiler';
      }
    });
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      minimumFractionDigits: 0
    }).format(value);
  }
}
