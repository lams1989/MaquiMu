import { Alquiler } from '@core/models/alquiler.model';
import { AlquilerService } from '@core/services/alquiler.service';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MyRentalsComponent } from './my-rentals.component';
import { of, throwError } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

describe('MyRentalsComponent', () => {
  let component: MyRentalsComponent;
  let fixture: ComponentFixture<MyRentalsComponent>;
  let alquilerServiceSpy: jasmine.SpyObj<AlquilerService>;

  const mockAlquileres: Alquiler[] = [
    {
      alquilerId: 1,
      clienteId: 10,
      maquinariaId: 100,
      fechaInicio: '2026-01-01',
      fechaFin: '2026-01-15',
      costoTotal: 5000000,
      estado: 'ACTIVO'
    },
    {
      alquilerId: 2,
      clienteId: 10,
      maquinariaId: 101,
      fechaInicio: '2026-02-01',
      fechaFin: '2026-02-10',
      costoTotal: 3000000,
      estado: 'PENDIENTE'
    },
    {
      alquilerId: 3,
      clienteId: 10,
      maquinariaId: 102,
      fechaInicio: '2025-12-01',
      fechaFin: '2025-12-20',
      costoTotal: 8000000,
      estado: 'FINALIZADO'
    }
  ];

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('AlquilerService', ['getMisAlquileres', 'getMiAlquilerDetalle']);
    spy.getMisAlquileres.and.returnValue(of(mockAlquileres));

    await TestBed.configureTestingModule({
      imports: [MyRentalsComponent, RouterTestingModule, HttpClientTestingModule],
      providers: [{ provide: AlquilerService, useValue: spy }]
    })
    .compileComponents();

    alquilerServiceSpy = TestBed.inject(AlquilerService) as jasmine.SpyObj<AlquilerService>;
    fixture = TestBed.createComponent(MyRentalsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load alquileres on init', () => {
    fixture.detectChanges();
    expect(component.alquileres.length).toBe(3);
    expect(component.alquileresFiltrados.length).toBe(3);
    expect(component.loading).toBeFalse();
  });

  it('should filter by estado ACTIVO', () => {
    fixture.detectChanges();
    component.filtroEstado = 'ACTIVO';
    component.aplicarFiltro();
    expect(component.alquileresFiltrados.length).toBe(1);
    expect(component.alquileresFiltrados[0].estado).toBe('ACTIVO');
  });

  it('should filter by estado PENDIENTE', () => {
    fixture.detectChanges();
    component.filtroEstado = 'PENDIENTE';
    component.aplicarFiltro();
    expect(component.alquileresFiltrados.length).toBe(1);
    expect(component.alquileresFiltrados[0].alquilerId).toBe(2);
  });

  it('should show all when filter cleared', () => {
    fixture.detectChanges();
    component.filtroEstado = 'ACTIVO';
    component.aplicarFiltro();
    expect(component.alquileresFiltrados.length).toBe(1);

    component.filtroEstado = '';
    component.aplicarFiltro();
    expect(component.alquileresFiltrados.length).toBe(3);
  });

  it('should handle error loading alquileres', () => {
    alquilerServiceSpy.getMisAlquileres.and.returnValue(throwError(() => new Error('Server error')));
    fixture.detectChanges();
    expect(component.loading).toBeFalse();
    expect(component.errorMessage).toBeTruthy();
  });

  it('should load detail on verDetalle', () => {
    const detalle: Alquiler = mockAlquileres[0];
    alquilerServiceSpy.getMiAlquilerDetalle.and.returnValue(of(detalle));
    fixture.detectChanges();

    component.verDetalle(1);
    expect(alquilerServiceSpy.getMiAlquilerDetalle).toHaveBeenCalledWith(1);
    expect(component.alquilerDetalle).toEqual(detalle);
    expect(component.loadingDetalle).toBeFalse();
  });

  it('should clear detail on cerrarDetalle', () => {
    component.alquilerDetalle = mockAlquileres[0];
    component.cerrarDetalle();
    expect(component.alquilerDetalle).toBeNull();
  });

  it('should return correct badge classes', () => {
    expect(component.getBadgeClass('PENDIENTE')).toContain('bg-warning');
    expect(component.getBadgeClass('ACTIVO')).toContain('bg-success');
    expect(component.getBadgeClass('RECHAZADO')).toContain('bg-danger');
    expect(component.getBadgeClass('FINALIZADO')).toContain('bg-secondary');
  });

  it('should format currency in COP', () => {
    const formatted = component.formatCurrency(5000000);
    expect(formatted).toContain('5.000.000');
  });
});
