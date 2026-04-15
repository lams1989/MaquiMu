import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MachineModalComponent } from '../machine-modal/machine-modal.component';
import { Maquinaria } from '@core/models/maquinaria.model';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { NavbarComponent } from '@shared/navbar/navbar.component';
import { SensitiveDataService } from '@core/services/sensitive-data.service';
import { SidebarComponent } from '@shared/sidebar/sidebar.component';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss'],
  standalone: true,
  imports: [CommonModule, MachineModalComponent, SidebarComponent, NavbarComponent]
})
export class InventoryComponent implements OnInit {
  maquinarias: Maquinaria[] = [];
  selectedMaquinaria: Maquinaria | null = null;
  showModal: boolean = false;
  showDeleteConfirmModal = false;
  maquinariaAEliminarId: number | null = null;
  errorMessage = '';
  isLoading = false;

  constructor(
    private maquinariaService: MaquinariaService,
    public sensitiveData: SensitiveDataService
  ) { }

  ngOnInit(): void {
    this.loadMaquinarias();
  }

  loadMaquinarias(): void {
    this.isLoading = true;
    this.maquinariaService.getMaquinarias().subscribe({
      next: (data: Maquinaria[]) => {
        this.maquinarias = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error al cargar maquinarias', error);
        this.isLoading = false;
      }
    });
  }

  openCreateModal(): void {
    this.selectedMaquinaria = null;
    this.showModal = true;
  }

  openEditModal(maquinaria: Maquinaria): void {
    this.selectedMaquinaria = { ...maquinaria }; // Create a copy for editing
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.loadMaquinarias(); // Reload data after modal closes
  }

  deleteMaquinaria(id: number): void {
    this.maquinariaAEliminarId = id;
    this.showDeleteConfirmModal = true;
  }

  confirmarEliminacion(): void {
    if (this.maquinariaAEliminarId == null) {
      return;
    }

    this.maquinariaService.deleteMaquinaria(this.maquinariaAEliminarId).subscribe({
      next: () => {
        this.errorMessage = '';
        this.cerrarConfirmacion();
        this.loadMaquinarias();
      },
      error: (error: any) => {
        console.error('Error al eliminar maquinaria', error);
        this.errorMessage = error.error?.message || 'No se pudo eliminar la maquinaria.';
        this.cerrarConfirmacion();
      }
    });
  }

  cerrarConfirmacion(): void {
    this.showDeleteConfirmModal = false;
    this.maquinariaAEliminarId = null;
  }
}
