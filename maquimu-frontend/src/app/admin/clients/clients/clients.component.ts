import { Cliente } from '@core/models/cliente.model';
import { ClienteService } from '@core/services/cliente.service';
import { ClientModalComponent } from '../client-modal/client-modal.component';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { SidebarComponent } from '@shared/sidebar/sidebar.component';
import { NavbarComponent } from '@shared/navbar/navbar.component';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
  standalone: true,
  imports: [CommonModule, ClientModalComponent, SidebarComponent, NavbarComponent]
})
export class ClientsComponent implements OnInit {
  clientes: Cliente[] = [];
  selectedCliente: Cliente | null = null;
  showModal: boolean = false;

  constructor(private clienteService: ClienteService) { }

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.clienteService.getClientes().subscribe({
      next: (data: Cliente[]) => {
        this.clientes = data;
      },
      error: (error: any) => {
        console.error('Error al cargar clientes', error);
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
    if (confirm('¿Está seguro de que desea eliminar este cliente?')) {
      this.clienteService.deleteCliente(id).subscribe({
        next: () => {
          console.log('Cliente eliminado exitosamente');
          this.loadClientes();
        },
        error: (error: any) => {
          console.error('Error al eliminar cliente', error);
        }
      });
    }
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-CO', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    });
  }
}
