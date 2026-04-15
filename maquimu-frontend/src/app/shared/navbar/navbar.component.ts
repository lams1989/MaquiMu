import { AuthService } from '../../core/services/auth/auth.service';
import { Cliente } from '../../core/models/cliente.model';
import { ClienteService } from '../../core/services/cliente.service';
import { ClientModalComponent } from '../client-modal/client-modal.component';
import { CommonModule } from '@angular/common';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NotificationDropdownComponent } from '../notification-dropdown/notification-dropdown.component';
import { NotificationService } from '../../core/services/notification.service';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { Usuario } from '../../core/models/auth/login-register.models';
import { UsuarioService } from '../../core/services/usuario.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NotificationDropdownComponent, ClientModalComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit, OnDestroy {
  currentUser: Usuario | null = null;

  // User dropdown
  showUserMenu = false;

  // Change password modal
  showChangePasswordModal = false;
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';
  changePasswordError = '';
  changePasswordLoading = false;
  changePasswordSuccess = false;

  // Edit profile modal
  showEditProfileModal = false;
  clienteParaEditar: Cliente | null = null;

  private profileEditSub?: Subscription;

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private clienteService: ClienteService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
    this.profileEditSub = this.notificationService.profileEditRequested$.subscribe(() => {
      this.openEditProfileModal();
    });
  }

  ngOnDestroy(): void {
    this.profileEditSub?.unsubscribe();
  }

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.user-pill')) {
      this.showUserMenu = false;
    }
  }

  openChangePasswordModal(event: Event): void {
    event.stopPropagation();
    this.showUserMenu = false;
    this.showChangePasswordModal = true;
    this.currentPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.changePasswordError = '';
    this.changePasswordSuccess = false;
  }

  closeChangePasswordModal(): void {
    this.showChangePasswordModal = false;
  }

  openEditProfileModal(event?: Event): void {
    if (event) event.stopPropagation();
    this.showUserMenu = false;
    if (!this.currentUser?.clienteId) return;
    this.clienteService.getClienteById(this.currentUser.clienteId).subscribe({
      next: (cliente) => {
        this.clienteParaEditar = cliente;
        this.showEditProfileModal = true;
      },
      error: (err) => {
        console.error('Error al cargar datos del perfil', err);
      }
    });
  }

  onProfileModalClose(): void {
    this.showEditProfileModal = false;
    if (this.clienteParaEditar && this.currentUser) {
      this.clienteService.getClienteById(this.currentUser.clienteId!).subscribe({
        next: (cliente) => {
          const nombreCompleto = cliente.apellido
            ? `${cliente.nombreCliente} ${cliente.apellido}`
            : cliente.nombreCliente;
          this.authService.updateCurrentUser({
            nombreCompleto,
            email: cliente.email
          });
        }
      });
    }
    this.clienteParaEditar = null;
  }

  submitChangePassword(): void {
    if (!this.currentPassword || !this.newPassword || this.newPassword.length < 6 || this.newPassword !== this.confirmPassword) return;

    this.changePasswordLoading = true;
    this.changePasswordError = '';

    this.usuarioService.cambiarPassword(this.currentPassword, this.newPassword).subscribe({
      next: () => {
        this.changePasswordLoading = false;
        this.changePasswordSuccess = true;
      },
      error: (err) => {
        this.changePasswordLoading = false;
        this.changePasswordError = err.error?.message || 'No se pudo cambiar la contraseña.';
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
