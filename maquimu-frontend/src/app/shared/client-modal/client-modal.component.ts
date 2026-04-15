import { ActualizarClienteRequest, Cliente, CrearClienteRequest } from '@core/models/cliente.model';
import { ClienteService } from '@core/services/cliente.service';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { forkJoin } from 'rxjs';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuarioService } from '@core/services/usuario.service';

@Component({
  selector: 'app-client-modal',
  templateUrl: './client-modal.component.html',
  styleUrls: ['./client-modal.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class ClientModalComponent implements OnInit {
  @Input() cliente: Cliente | null = null;
  @Input() modoAutoservicio: boolean = false;
  @Output() close = new EventEmitter<void>();

  clientForm!: FormGroup;
  isEditMode: boolean = false;
  errorMessage: string = '';
  rolActual: string = '';
  roles: string[] = ['CLIENTE', 'OPERARIO'];

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.cliente;
    this.initializeForm();
    if (this.isEditMode && this.cliente?.usuarioId && !this.modoAutoservicio) {
      this.cargarRolUsuario(this.cliente.usuarioId);
    }
  }

  initializeForm(): void {
    const tipoClienteInicial = this.cliente?.apellido ? 'NATURAL' : 'JURIDICA';

    this.clientForm = this.fb.group({
      tipoCliente: [tipoClienteInicial],
      nombreCliente: [this.cliente?.nombreCliente || '', Validators.required],
      apellido: [this.cliente?.apellido || ''],
      identificacion: [this.cliente?.identificacion || '', Validators.required],
      telefono: [this.cliente?.telefono || '', Validators.required],
      email: [this.cliente?.email || '', [Validators.required, Validators.email]],
      direccion: [this.cliente?.direccion || '', Validators.required],
      autorizaDatos: [this.isEditMode ? true : false, Validators.requiredTrue],
      rol: [{ value: '', disabled: !this.isEditMode }]
    });

    this.actualizarValidacionApellido();
    this.clientForm.get('tipoCliente')?.valueChanges.subscribe(() => this.actualizarValidacionApellido());
  }

  cargarRolUsuario(usuarioId: number): void {
    this.usuarioService.getUsuarioPorId(usuarioId).subscribe({
      next: (usuario) => {
        this.rolActual = usuario.rol;
        this.clientForm.get('rol')?.setValue(usuario.rol);
        this.clientForm.get('rol')?.enable();
      },
      error: (error: any) => {
        console.error('Error al cargar rol del usuario', error);
      }
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
        usuarioId: this.cliente.usuarioId,
        nombreCliente: formValue.nombreCliente,
        apellido: formValue.apellido,
        identificacion: formValue.identificacion,
        telefono: formValue.telefono,
        email: formValue.email,
        direccion: formValue.direccion
      };

      const nuevoRol = this.clientForm.get('rol')?.value;
      const cambioRol = !this.modoAutoservicio && this.cliente.usuarioId && nuevoRol && nuevoRol !== this.rolActual;

      const updateCliente$ = this.clienteService.updateCliente(this.cliente.clienteId, updateRequest);

      if (cambioRol) {
        forkJoin([
          updateCliente$,
          this.usuarioService.cambiarRol(this.cliente!.usuarioId!, nuevoRol)
        ]).subscribe({
          next: () => {
            console.log('Cliente y rol actualizados exitosamente');
            this.close.emit();
          },
          error: (error: any) => {
            console.error('Error al actualizar cliente o rol', error);
            this.errorMessage = error.error?.message || 'No se pudo actualizar el cliente. Intente nuevamente.';
          }
        });
      } else {
        updateCliente$.subscribe({
          next: () => {
            console.log('Cliente actualizado exitosamente');
            this.close.emit();
          },
          error: (error: any) => {
            console.error('Error al actualizar cliente', error);
            this.errorMessage = error.error?.message || 'No se pudo actualizar el cliente. Intente nuevamente.';
          }
        });
      }
    } else {
      const createRequest: CrearClienteRequest = {
        nombreCliente: formValue.nombreCliente,
        apellido: formValue.apellido,
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

  private actualizarValidacionApellido(): void {
    const apellidoControl = this.clientForm.get('apellido');
    const tipoCliente = this.clientForm.get('tipoCliente')?.value;

    if (!apellidoControl) {
      return;
    }

    if (tipoCliente === 'NATURAL') {
      apellidoControl.setValidators([Validators.required]);
    } else {
      apellidoControl.clearValidators();
    }

    apellidoControl.updateValueAndValidity();
  }
}
