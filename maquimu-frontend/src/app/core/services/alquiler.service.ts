import { Alquiler, SolicitudAlquiler } from '@core/models/alquiler.model';
import { environment } from '@environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Maquinaria } from '@core/models/maquinaria.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlquilerService {
  private apiUrl = `${environment.apiUrl}/v1`;

  constructor(private http: HttpClient) { }

  /**
   * Obtiene las maquinarias disponibles en un rango de fechas
   */
  getMaquinariasDisponibles(fechaInicio: string, fechaFin: string): Observable<Maquinaria[]> {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);

    return this.http.get<Maquinaria[]>(`${this.apiUrl}/maquinarias/disponibles`, { params });
  }

  /**
   * Solicita un nuevo alquiler
   */
  solicitarAlquiler(solicitud: SolicitudAlquiler): Observable<Alquiler> {
    return this.http.post<Alquiler>(`${this.apiUrl}/alquileres`, solicitud);
  }

  /**
   * Obtiene los alquileres de un cliente
   */
  getAlquileresCliente(clienteId: number): Observable<Alquiler[]> {
    return this.http.get<Alquiler[]>(`${this.apiUrl}/clientes/${clienteId}/alquileres`);
  }
}
