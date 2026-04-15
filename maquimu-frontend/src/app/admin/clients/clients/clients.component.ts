import { Cliente } from '@core/models/cliente.model';
import { ClienteService } from '@core/services/cliente.service';
import { ClientModalComponent } from '@shared/client-modal/client-modal.component';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '@shared/navbar/navbar.component';
import { SidebarComponent } from '@shared/sidebar/sidebar.component';
import { UsuarioPendiente } from '@core/models/usuario.model';
import { UsuarioService } from '@core/services/usuario.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ClientModalComponent, SidebarComponent, NavbarComponent]
})
export class ClientsComponent implements OnInit {
  clientes: Cliente[] = [];
  selectedCliente: Cliente | null = null;
  showModal: boolean = false;
  showDeleteConfirmModal = false;
  clienteAEliminarId: number | null = null;
  errorMessage = '';
  clientesLoading = false;

  // Tab y aprobación
  activeTab: 'clientes' | 'pendientes' | 'restablecer' = 'clientes';
  pendientes: UsuarioPendiente[] = [];
  restablecer: UsuarioPendiente[] = [];
  showRejectModal = false;
  usuarioARechazar: UsuarioPendiente | null = null;
  motivoRechazo = '';
  pendientesLoading = false;
  restablecerLoading = false;

  // Asignar password modal
  showAssignPasswordModal = false;
  usuarioAsignarPassword: UsuarioPendiente | null = null;
  passwordTemporal = '';
  confirmPasswordTemporal = '';
  assignPasswordError = '';
  assignPasswordLoading = false;
  assignPasswordSuccess = false;

  constructor(
    private clienteService: ClienteService,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit(): void {
    this.loadClientes();
    this.loadPendientes();
    this.loadRestablecer();
  }

  // ========== Tab navigation ==========

  setActiveTab(tab: 'clientes' | 'pendientes' | 'restablecer'): void {
    this.activeTab = tab;
    if (tab === 'pendientes') {
      this.loadPendientes();
    } else if (tab === 'restablecer') {
      this.loadRestablecer();
    }
  }

  // ========== Clientes CRUD ==========

  loadClientes(): void {
    this.clientesLoading = true;
    this.clienteService.getClientes().subscribe({
      next: (data: Cliente[]) => {
        this.clientes = data;
        this.clientesLoading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar clientes', error);
        this.clientesLoading = false;
      }
    });
  }

  openCreateModal(): void {
    this.selectedCliente = null;
    this.showModal = true;
  }

  openEditModal(cliente: Cliente): void {
    this.selectedCliente = { ...cliente };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.loadClientes();
  }

  deleteCliente(id: number): void {
    this.clienteAEliminarId = id;
    this.showDeleteConfirmModal = true;
  }

  confirmarEliminacion(): void {
    if (this.clienteAEliminarId == null) {
      return;
    }

    this.clienteService.deleteCliente(this.clienteAEliminarId).subscribe({
      next: () => {
        this.errorMessage = '';
        this.cerrarConfirmacion();
        this.loadClientes();
      },
      error: (error: any) => {
        console.error('Error al eliminar cliente', error);
        this.errorMessage = error.error?.message || 'No se pudo eliminar el cliente.';
        this.cerrarConfirmacion();
      }
    });
  }

  cerrarConfirmacion(): void {
    this.showDeleteConfirmModal = false;
    this.clienteAEliminarId = null;
  }

  // ========== Pendientes de aprobación ==========

  loadPendientes(): void {
    this.pendientesLoading = true;
    this.usuarioService.getUsuariosPendientes().subscribe({
      next: (data: UsuarioPendiente[]) => {
        this.pendientes = data;
        this.pendientesLoading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar pendientes', error);
        this.pendientesLoading = false;
      }
    });
  }

  aprobarUsuario(usuario: UsuarioPendiente): void {
    this.usuarioService.aprobarUsuario(usuario.usuarioId).subscribe({
      next: () => {
        this.loadPendientes();
        this.loadClientes();
      },
      error: (error: any) => {
        console.error('Error al aprobar usuario', error);
        this.errorMessage = error.error?.message || 'No se pudo aprobar el usuario.';
      }
    });
  }

  abrirModalRechazo(usuario: UsuarioPendiente): void {
    this.usuarioARechazar = usuario;
    this.motivoRechazo = '';
    this.showRejectModal = true;
  }

  confirmarRechazo(): void {
    if (!this.usuarioARechazar || !this.motivoRechazo.trim()) return;

    this.usuarioService.rechazarUsuario(this.usuarioARechazar.usuarioId, this.motivoRechazo.trim()).subscribe({
      next: () => {
        this.cerrarModalRechazo();
        this.loadPendientes();
      },
      error: (error: any) => {
        console.error('Error al rechazar usuario', error);
        this.errorMessage = error.error?.message || 'No se pudo rechazar el usuario.';
      }
    });
  }

  cerrarModalRechazo(): void {
    this.showRejectModal = false;
    this.usuarioARechazar = null;
    this.motivoRechazo = '';
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-CO', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    });
  }

  // ========== Restablecimiento de contraseña ==========

  loadRestablecer(): void {
    this.restablecerLoading = true;
    this.usuarioService.getUsuariosRestablecer().subscribe({
      next: (data: UsuarioPendiente[]) => {
        this.restablecer = data;
        this.restablecerLoading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar solicitudes de restablecimiento', error);
        this.restablecerLoading = false;
      }
    });
  }

  abrirModalAsignarPassword(usuario: UsuarioPendiente): void {
    this.usuarioAsignarPassword = usuario;
    this.passwordTemporal = '';
    this.confirmPasswordTemporal = '';
    this.assignPasswordError = '';
    this.assignPasswordSuccess = false;
    this.showAssignPasswordModal = true;
  }

  generarPasswordAutomatica(): void {
    const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789';
    let result = '';
    for (let i = 0; i < 8; i++) {
      result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    this.passwordTemporal = result;
    this.confirmPasswordTemporal = result;
  }

  confirmarAsignarPassword(): void {
    if (!this.usuarioAsignarPassword || !this.passwordTemporal || this.passwordTemporal.length < 6 || this.passwordTemporal !== this.confirmPasswordTemporal) return;

    this.assignPasswordLoading = true;
    this.assignPasswordError = '';

    this.usuarioService.asignarPasswordTemporal(this.usuarioAsignarPassword.usuarioId, this.passwordTemporal).subscribe({
      next: () => {
        this.assignPasswordLoading = false;
        this.assignPasswordSuccess = true;
        this.loadRestablecer();
      },
      error: (error: any) => {
        this.assignPasswordLoading = false;
        this.assignPasswordError = error.error?.message || 'No se pudo asignar la contraseña temporal.';
      }
    });
  }

  cerrarModalAsignarPassword(): void {
    this.showAssignPasswordModal = false;
    this.usuarioAsignarPassword = null;
    this.passwordTemporal = '';
    this.confirmPasswordTemporal = '';
  }
}
