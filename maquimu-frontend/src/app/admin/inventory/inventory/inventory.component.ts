import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MachineModalComponent } from '../machine-modal/machine-modal.component';
import { Maquinaria } from '@core/models/maquinaria.model';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { NavbarComponent } from '@shared/navbar/navbar.component';
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

  constructor(private maquinariaService: MaquinariaService) { }

  ngOnInit(): void {
    this.loadMaquinarias();
  }

  loadMaquinarias(): void {
    this.maquinariaService.getMaquinarias().subscribe({
      next: (data: Maquinaria[]) => {
        this.maquinarias = data;
      },
      error: (error: any) => {
        console.error('Error al cargar maquinarias', error);
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
    if (confirm('¿Está seguro de que desea eliminar esta maquinaria?')) {
      this.maquinariaService.deleteMaquinaria(id).subscribe({
        next: () => {
          console.log('Maquinaria eliminada exitosamente');
          this.loadMaquinarias();
        },
        error: (error: any) => {
          console.error('Error al eliminar maquinaria', error);
        }
      });
    }
  }
}
