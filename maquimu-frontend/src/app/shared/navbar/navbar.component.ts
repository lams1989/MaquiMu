import { AuthService } from '../../core/services/auth/auth.service';
import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NotificationDropdownComponent } from '../notification-dropdown/notification-dropdown.component';
import { RouterModule } from '@angular/router';
import { Usuario } from '../../core/models/auth/login-register.models';
import { UsuarioService } from '../../core/services/usuario.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NotificationDropdownComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
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

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
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
