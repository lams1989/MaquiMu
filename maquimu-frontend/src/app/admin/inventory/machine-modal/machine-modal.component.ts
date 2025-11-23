import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Maquinaria } from '../../../core/models/maquinaria.model';
import { MaquinariaService } from '../../../core/services/maquinaria.service';

@Component({
    selector: 'app-machine-modal',
    templateUrl: './machine-modal.component.html',
    styleUrls: ['./machine-modal.component.css']
})
export class MachineModalComponent {
    @Output() save = new EventEmitter<void>();

    isVisible = false;
    isEditMode = false;
    machineForm: FormGroup;
    currentId?: number;

    constructor(
        private fb: FormBuilder,
        private maquinariaService: MaquinariaService
    ) {
        this.machineForm = this.fb.group({
            nombre: ['', Validators.required],
            marca: [''],
            modelo: [''],
            serial: ['', Validators.required],
            estado: ['DISPONIBLE', Validators.required],
            tarifaDia: [0, [Validators.required, Validators.min(0)]],
            tarifaHora: [0, [Validators.required, Validators.min(0)]],
            descripcion: ['']
        });
    }

    open(maquinaria?: Maquinaria): void {
        this.isVisible = true;
        this.isEditMode = !!maquinaria;
        this.currentId = maquinaria?.id;

        if (maquinaria) {
            this.machineForm.patchValue(maquinaria);
        } else {
            this.machineForm.reset({
                estado: 'DISPONIBLE',
                tarifaDia: 0,
                tarifaHora: 0
            });
        }
    }

    close(): void {
        this.isVisible = false;
    }

    onSubmit(): void {
        if (this.machineForm.invalid) return;

        const maquinaria: Maquinaria = this.machineForm.value;

        if (this.isEditMode && this.currentId) {
            this.maquinariaService.updateMaquinaria(this.currentId, maquinaria).subscribe({
                next: () => {
                    this.save.emit();
                    this.close();
                },
                error: (err: any) => alert('Error al actualizar: ' + (err.error || err.message))
            });
        } else {
            this.maquinariaService.createMaquinaria(maquinaria).subscribe({
                next: () => {
                    this.save.emit();
                    this.close();
                },
                error: (err: any) => alert('Error al crear: ' + (err.error || err.message))
            });
        }
    }
}
