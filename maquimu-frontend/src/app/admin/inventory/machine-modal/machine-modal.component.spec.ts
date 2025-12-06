import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MachineModalComponent } from '@app/admin/inventory/machine-modal/machine-modal.component';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { Maquinaria, CrearMaquinariaRequest, ActualizarMaquinariaRequest } from '@core/models/maquinaria.model';
import { of, throwError } from 'rxjs';
import { CommonModule } from '@angular/common'; // Import CommonModule

describe('MachineModalComponent', () => {
  let component: MachineModalComponent;
  let fixture: ComponentFixture<MachineModalComponent>;
  let maquinariaServiceSpy: jasmine.SpyObj<MaquinariaService>;

  const mockMaquinaria: Maquinaria = {
    maquinariaId: 1, nombreEquipo: 'Excavadora', marca: 'Cat', modelo: '320', serial: 'SN001', estado: 'DISPONIBLE', tarifaPorDia: 100, tarifaPorHora: 10
  };

  beforeEach(async () => {
    maquinariaServiceSpy = jasmine.createSpyObj('MaquinariaService', ['createMaquinaria', 'updateMaquinaria']);
    maquinariaServiceSpy.createMaquinaria.and.returnValue(of(mockMaquinaria));
    maquinariaServiceSpy.updateMaquinaria.and.returnValue(of(mockMaquinaria));

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, CommonModule, MachineModalComponent], // Import standalone component
      providers: [
        FormBuilder,
        { provide: MaquinariaService, useValue: maquinariaServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MachineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form for creation mode', () => {
    component.maquinaria = null;
    component.ngOnInit();
    expect(component.isEditMode).toBeFalse();
    expect(component.machineForm.get('nombreEquipo')?.value).toBe('');
    expect(component.machineForm.get('estado')?.disabled).toBeTrue();
  });

  it('should initialize form for edit mode', () => {
    component.maquinaria = mockMaquinaria;
    component.ngOnInit();
    expect(component.isEditMode).toBeTrue();
    expect(component.machineForm.get('nombreEquipo')?.value).toBe(mockMaquinaria.nombreEquipo);
    expect(component.machineForm.get('estado')?.value).toBe(mockMaquinaria.estado);
    expect(component.machineForm.get('estado')?.disabled).toBeFalse();
  });

  it('should call createMaquinaria on save in create mode', () => {
    component.maquinaria = null;
    component.ngOnInit();
    component.machineForm.patchValue({
      nombreEquipo: 'Nueva Maquinaria', marca: 'MarcaX', modelo: 'ModeloY', serial: 'NEW001', tarifaPorDia: 50, tarifaPorHora: 5
    });
    spyOn(component.close, 'emit');

    component.saveMachine();

    expect(maquinariaServiceSpy.createMaquinaria).toHaveBeenCalledWith(jasmine.objectContaining({
      nombreEquipo: 'Nueva Maquinaria', serial: 'NEW001'
    }));
    expect(component.close.emit).toHaveBeenCalled();
  });

  it('should call updateMaquinaria on save in edit mode', () => {
    component.maquinaria = mockMaquinaria;
    component.ngOnInit();
    component.machineForm.patchValue({ nombreEquipo: 'Excavadora Actualizada', estado: 'EN_MANTENIMIENTO' });
    spyOn(component.close, 'emit');

    component.saveMachine();

    expect(maquinariaServiceSpy.updateMaquinaria).toHaveBeenCalledWith(
      mockMaquinaria.maquinariaId,
      jasmine.objectContaining({ nombreEquipo: 'Excavadora Actualizada', estado: 'EN_MANTENIMIENTO' })
    );
    expect(component.close.emit).toHaveBeenCalled();
  });

  it('should emit close event on cancel', () => {
    spyOn(component.close, 'emit');
    component.cancel();
    expect(component.close.emit).toHaveBeenCalled();
  });

  it('should not save if form is invalid', () => {
    component.machineForm.patchValue({ nombreEquipo: '' }); // Make form invalid
    component.saveMachine();
    expect(maquinariaServiceSpy.createMaquinaria).not.toHaveBeenCalled();
    expect(maquinariaServiceSpy.updateMaquinaria).not.toHaveBeenCalled();
  });

  it('should handle creation error', () => {
    const createRequest: CrearMaquinariaRequest = { nombreEquipo: 'Nueva Maquinaria', marca: 'MarcaX', modelo: 'ModeloY', serial: 'NEW001', tarifaPorDia: 50, tarifaPorHora: 5 };
    maquinariaServiceSpy.createMaquinaria.and.returnValue(throwError(() => new Error('Error de creación')));
    component.maquinaria = null;
    component.ngOnInit();
    component.machineForm.patchValue(createRequest);
    spyOn(console, 'error');

    component.saveMachine();

    expect(console.error).toHaveBeenCalledWith('Error al crear maquinaria', jasmine.any(Error));
    expect(maquinariaServiceSpy.createMaquinaria).toHaveBeenCalled();
  });

  it('should handle update error', () => {
    const updateRequest: ActualizarMaquinariaRequest = { nombreEquipo: 'Excavadora Actualizada' };
    maquinariaServiceSpy.updateMaquinaria.and.returnValue(throwError(() => new Error('Error de actualización')));
    component.maquinaria = mockMaquinaria;
    component.ngOnInit();
    component.machineForm.patchValue(updateRequest);
    spyOn(console, 'error');

    component.saveMachine();

    expect(console.error).toHaveBeenCalledWith('Error al actualizar maquinaria', jasmine.any(Error));
    expect(maquinariaServiceSpy.updateMaquinaria).toHaveBeenCalled();
  });
});
