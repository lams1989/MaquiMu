import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InventoryComponent } from '@app/admin/inventory/inventory/inventory.component';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { of } from 'rxjs';
import { Maquinaria } from '@core/models/maquinaria.model';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { MachineModalComponent } from '@app/admin/inventory/machine-modal/machine-modal.component';

describe('InventoryComponent', () => {
  let component: InventoryComponent;
  let fixture: ComponentFixture<InventoryComponent>;
  let maquinariaServiceSpy: jasmine.SpyObj<MaquinariaService>;

  const mockMaquinarias: Maquinaria[] = [
    { maquinariaId: 1, nombreEquipo: 'Excavadora', marca: 'Cat', modelo: '320', serial: 'SN001', estado: 'DISPONIBLE', tarifaPorDia: 100, tarifaPorHora: 10 },
    { maquinariaId: 2, nombreEquipo: 'Retroexcavadora', marca: 'JCB', modelo: '3CX', serial: 'SN002', estado: 'ALQUILADO', tarifaPorDia: 80, tarifaPorHora: 8 }
  ];

  beforeEach(async () => {
    maquinariaServiceSpy = jasmine.createSpyObj('MaquinariaService', ['getMaquinarias', 'deleteMaquinaria']);
    maquinariaServiceSpy.getMaquinarias.and.returnValue(of(mockMaquinarias));
    maquinariaServiceSpy.deleteMaquinaria.and.returnValue(of(void 0)); // For delete operation

    await TestBed.configureTestingModule({
      imports: [InventoryComponent, MachineModalComponent], // Import standalone component
      providers: [
        { provide: MaquinariaService, useValue: maquinariaServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(InventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load maquinarias on init', () => {
    expect(maquinariaServiceSpy.getMaquinarias).toHaveBeenCalledTimes(1);
    expect(component.maquinarias).toEqual(mockMaquinarias);
  });

  it('should open create modal', () => {
    component.openCreateModal();
    expect(component.selectedMaquinaria).toBeNull();
    expect(component.showModal).toBeTrue();
  });

  it('should open edit modal with selected maquinaria', () => {
    const maquinariaToEdit = mockMaquinarias[0];
    component.openEditModal(maquinariaToEdit);
    expect(component.selectedMaquinaria).toEqual(maquinariaToEdit);
    expect(component.showModal).toBeTrue();
  });

  it('should close modal and reload maquinarias', () => {
    component.showModal = true;
    component.closeModal();
    expect(component.showModal).toBeFalse();
    expect(maquinariaServiceSpy.getMaquinarias).toHaveBeenCalledTimes(2); // Initial load + reload after close
  });

  it('should delete maquinaria after confirmation', () => {
    spyOn(window, 'confirm').and.returnValue(true); // Mock user confirmation
    component.deleteMaquinaria(1);
    expect(maquinariaServiceSpy.deleteMaquinaria).toHaveBeenCalledWith(1);
    expect(maquinariaServiceSpy.getMaquinarias).toHaveBeenCalledTimes(2); // Reload after delete
  });

  it('should not delete maquinaria if not confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(false); // Mock user denial
    component.deleteMaquinaria(1);
    expect(maquinariaServiceSpy.deleteMaquinaria).not.toHaveBeenCalled();
  });

  it('should render machinery in the table', () => {
    fixture.detectChanges();
    const tableRows: DebugElement[] = fixture.debugElement.queryAll(By.css('tbody tr'));
    expect(tableRows.length).toBe(mockMaquinarias.length);

    const firstRowColumns: DebugElement[] = tableRows[0].queryAll(By.css('td'));
    expect(firstRowColumns[0].nativeElement.textContent).toContain(mockMaquinarias[0].nombreEquipo);
    expect(firstRowColumns[1].nativeElement.textContent).toContain(mockMaquinarias[0].marca);
  });
});
