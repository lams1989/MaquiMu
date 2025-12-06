import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MaquinariaService } from '@core/services/maquinaria.service';
import { Maquinaria, CrearMaquinariaRequest, ActualizarMaquinariaRequest } from '@core/models/maquinaria.model';
import { environment } from '@environments/environment';

describe('MaquinariaService', () => {
  let service: MaquinariaService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MaquinariaService]
    });
    service = TestBed.inject(MaquinariaService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify(); // Ensure that there are no outstanding requests.
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all maquinarias', () => {
    const mockMaquinarias: Maquinaria[] = [
      { maquinariaId: 1, nombreEquipo: 'Excavadora', marca: 'Cat', modelo: '320', serial: 'SN001', estado: 'DISPONIBLE', tarifaPorDia: 100, tarifaPorHora: 10 },
      { maquinariaId: 2, nombreEquipo: 'Retroexcavadora', marca: 'JCB', modelo: '3CX', serial: 'SN002', estado: 'ALQUILADO', tarifaPorDia: 80, tarifaPorHora: 8 }
    ];

    service.getMaquinarias().subscribe(maquinarias => {
      expect(maquinarias).toEqual(mockMaquinarias);
    });

    const req = httpTestingController.expectOne(`${environment.apiUrl}/maquinaria`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMaquinarias);
  });

  it('should get maquinaria by id', () => {
    const mockMaquinaria: Maquinaria = { maquinariaId: 1, nombreEquipo: 'Excavadora', marca: 'Cat', modelo: '320', serial: 'SN001', estado: 'DISPONIBLE', tarifaPorDia: 100, tarifaPorHora: 10 };

    service.getMaquinariaById(1).subscribe(maquinaria => {
      expect(maquinaria).toEqual(mockMaquinaria);
    });

    const req = httpTestingController.expectOne(`${environment.apiUrl}/maquinaria/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMaquinaria);
  });

  it('should create maquinaria', () => {
    const createRequest: CrearMaquinariaRequest = { nombreEquipo: 'Excavadora', marca: 'Cat', modelo: '320', serial: 'SN001', tarifaPorDia: 100, tarifaPorHora: 10 };
    const mockMaquinaria: Maquinaria = { ...createRequest, maquinariaId: 1, estado: 'DISPONIBLE' };

    service.createMaquinaria(createRequest).subscribe(maquinaria => {
      expect(maquinaria).toEqual(mockMaquinaria);
    });

    const req = httpTestingController.expectOne(`${environment.apiUrl}/maquinaria`);
    expect(req.request.method).toBe('POST');
    req.flush(mockMaquinaria);
  });

  it('should update maquinaria', () => {
    const updateRequest: ActualizarMaquinariaRequest = { nombreEquipo: 'Excavadora Updated' };
    const mockMaquinaria: Maquinaria = { maquinariaId: 1, nombreEquipo: 'Excavadora Updated', marca: 'Cat', modelo: '320', serial: 'SN001', estado: 'DISPONIBLE', tarifaPorDia: 100, tarifaPorHora: 10 };

    service.updateMaquinaria(1, updateRequest).subscribe(maquinaria => {
      expect(maquinaria).toEqual(mockMaquinaria);
    });

    const req = httpTestingController.expectOne(`${environment.apiUrl}/maquinaria/1`);
    expect(req.request.method).toBe('PUT');
    req.flush(mockMaquinaria);
  });

  it('should delete maquinaria', () => {
    service.deleteMaquinaria(1).subscribe(() => {
      expect().nothing(); // Expect no specific return
    });

    const req = httpTestingController.expectOne(`${environment.apiUrl}/maquinaria/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // No content
  });
});
