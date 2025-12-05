import { Component, OnInit, ViewChild } from '@angular/core';
import { Maquinaria } from '../../core/models/maquinaria.model';
import { MaquinariaService } from '../../core/services/maquinaria.service';
import { MachineModalComponent } from './machine-modal/machine-modal.component';

@Component({
    selector: 'app-inventory',
    templateUrl: './inventory.component.html',
    styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {
    maquinarias: Maquinaria[] = [];
    @ViewChild(MachineModalComponent) modal!: MachineModalComponent;

    constructor(private maquinariaService: MaquinariaService) { }

    ngOnInit(): void {
        this.loadMaquinarias();
    }

    loadMaquinarias(): void {
        // TODO: HU-04 - Descomentar esta línea cuando el backend de maquinaria esté implementado.
        // this.maquinariaService.getAllMaquinarias().subscribe({
        //     next: (data) => this.maquinarias = data,
        //     error: (err: any) => console.error('Error loading maquinarias', err)
        // });
    }

    openCreateModal(): void {
        this.modal.open();
    }

    openEditModal(maquinaria: Maquinaria): void {
        this.modal.open(maquinaria);
    }

    deleteMaquinaria(id: number): void {
        if (confirm('¿Estás seguro de eliminar esta maquinaria?')) {
            this.maquinariaService.deleteMaquinaria(id).subscribe({
                next: () => this.loadMaquinarias(),
                error: (err: any) => console.error('Error deleting maquinaria', err)
            });
        }
    }

    onModalSave(): void {
        this.loadMaquinarias();
    }
}
