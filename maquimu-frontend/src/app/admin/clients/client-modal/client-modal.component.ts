import { ActualizarClienteRequest, Cliente, CrearClienteRequest } from '@core/models/cliente.model';
import { ClienteService } from '@core/services/cliente.service';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-client-modal',
  templateUrl: './client-modal.component.html',
  styleUrls: ['./client-modal.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class ClientModalComponent implements OnInit {
  @Input() cliente: Cliente | null = null;
  @Output() close = new EventEmitter<void>();

  clientForm!: FormGroup;
  isEditMode: boolean = false;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.cliente;
    this.initializeForm();
  }

  initializeForm(): void {
    this.clientForm = this.fb.group({
      tipoCliente: ['JURIDICA'],
      nombreCliente: [this.cliente?.nombreCliente || '', Validators.required],
      identificacion: [this.cliente?.identificacion || '', Validators.required],
      telefono: [this.cliente?.telefono || '', Validators.required],
      email: [this.cliente?.email || '', [Validators.required, Validators.email]],
      direccion: [this.cliente?.direccion || '', Validators.required],
      autorizaDatos: [this.isEditMode ? true : false, Validators.requiredTrue]
    });
  }

  saveClient(): void {
    if (this.clientForm.invalid) {
      this.clientForm.markAllAsTouched();
      return;
    }

    this.errorMessage = '';

    const formValue = this.clientForm.getRawValue();

    if (this.isEditMode && this.cliente) {
      const updateRequest: ActualizarClienteRequest = {
        nombreCliente: formValue.nombreCliente,
        identificacion: formValue.identificacion,
        telefono: formValue.telefono,
        email: formValue.email,
        direccion: formValue.direccion
      };
      this.clienteService.updateCliente(this.cliente.clienteId, updateRequest).subscribe({
        next: () => {
          console.log('Cliente actualizado exitosamente');
          this.close.emit();
        },
        error: (error: any) => {
          console.error('Error al actualizar cliente', error);
          this.errorMessage = error.error?.message || 'No se pudo actualizar el cliente. Intente nuevamente.';
        }
      });
    } else {
      const createRequest: CrearClienteRequest = {
        nombreCliente: formValue.nombreCliente,
        identificacion: formValue.identificacion,
        telefono: formValue.telefono,
        email: formValue.email,
        direccion: formValue.direccion
      };
      this.clienteService.createCliente(createRequest).subscribe({
        next: () => {
          console.log('Cliente creado exitosamente');
          this.close.emit();
        },
        error: (error: any) => {
          console.error('Error al crear cliente', error);
          this.errorMessage = error.error?.message || 'No se pudo crear el cliente. Intente nuevamente.';
        }
      });
    }
  }

  cancel(): void {
    this.close.emit();
  }
}
