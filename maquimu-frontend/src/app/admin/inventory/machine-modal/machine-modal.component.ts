import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MaquinariaService } from '../../../core/services/maquinaria.service';
import { Maquinaria, CrearMaquinariaRequest, ActualizarMaquinariaRequest } from '../../../core/models/maquinaria.model';
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-machine-modal',
  templateUrl: './machine-modal.component.html',
  styleUrls: ['./machine-modal.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class MachineModalComponent implements OnInit {
  @Input() maquinaria: Maquinaria | null = null;
  @Output() close = new EventEmitter<void>();

  machineForm!: FormGroup;
  isEditMode: boolean = false;
  estadosMaquinaria = ['DISPONIBLE', 'ALQUILADO', 'EN_MANTENIMIENTO'];

  constructor(
    private fb: FormBuilder,
    private maquinariaService: MaquinariaService
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.maquinaria;
    this.initializeForm();
  }

  initializeForm(): void {
    this.machineForm = this.fb.group({
      nombreEquipo: [this.maquinaria?.nombreEquipo || '', Validators.required],
      marca: [this.maquinaria?.marca || '', Validators.required],
      modelo: [this.maquinaria?.modelo || '', Validators.required],
      serial: [this.maquinaria?.serial || '', Validators.required],
      tarifaPorDia: [this.maquinaria?.tarifaPorDia || null, [Validators.required, Validators.min(0.01)]],
      tarifaPorHora: [this.maquinaria?.tarifaPorHora || null, [Validators.required, Validators.min(0.01)]],
      descripcion: [this.maquinaria?.descripcion || ''],
      estado: [this.maquinaria?.estado || 'DISPONIBLE']
    });

    if (!this.isEditMode) {
      this.machineForm.get('estado')?.disable(); // Estado fijo en creación
    }
  }

  saveMachine(): void {
    if (this.machineForm.invalid) {
      this.machineForm.markAllAsTouched();
      return;
    }

    const formValue = this.machineForm.getRawValue();

    if (this.isEditMode && this.maquinaria) {
      const updateRequest: ActualizarMaquinariaRequest = {
        nombreEquipo: formValue.nombreEquipo,
        marca: formValue.marca,
        modelo: formValue.modelo,
        serial: formValue.serial,
        tarifaPorDia: formValue.tarifaPorDia,
        tarifaPorHora: formValue.tarifaPorHora,
        descripcion: formValue.descripcion,
        estado: formValue.estado
      };
      this.maquinariaService.updateMaquinaria(this.maquinaria.maquinariaId, updateRequest).subscribe({
        next: () => {
          console.log('Maquinaria actualizada exitosamente');
          this.close.emit();
        },
        error: (error: any) => {
          console.error('Error al actualizar maquinaria', error);
          // Handle specific errors like serial duplication
        }
      });
    } else {
      const createRequest: CrearMaquinariaRequest = {
        nombreEquipo: formValue.nombreEquipo,
        marca: formValue.marca,
        modelo: formValue.modelo,
        serial: formValue.serial,
        tarifaPorDia: formValue.tarifaPorDia,
        tarifaPorHora: formValue.tarifaPorHora,
        descripcion: formValue.descripcion
      };
      this.maquinariaService.createMaquinaria(createRequest).subscribe({
        next: () => {
          console.log('Maquinaria creada exitosamente');
          this.close.emit();
        },
        error: (error: any) => {
          console.error('Error al crear maquinaria', error);
          // Handle specific errors like serial duplication
        }
      });
    }
  }

  cancel(): void {
    this.close.emit();
  }
}