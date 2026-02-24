import { Alquiler, EstadoAlquiler, SolicitudAlquiler } from '@core/models/alquiler.model';
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

  // ===== HU 07: Gestión de Alquileres (Operario) =====

  /**
   * Lista todos los alquileres, opcionalmente filtrados por estado
   */
  getAlquileres(estado?: EstadoAlquiler): Observable<Alquiler[]> {
    let params = new HttpParams();
    if (estado) {
      params = params.set('estado', estado);
    }
    return this.http.get<Alquiler[]>(`${this.apiUrl}/alquileres`, { params });
  }

  /**
   * Obtiene el detalle de un alquiler por ID
   */
  getAlquilerDetalle(id: number): Observable<Alquiler> {
    return this.http.get<Alquiler>(`${this.apiUrl}/alquileres/${id}`);
  }

  // ===== HU 08: Consultar Alquileres (Cliente) =====

  /**
   * Obtiene los alquileres del cliente autenticado (JWT)
   */
  getMisAlquileres(): Observable<Alquiler[]> {
    return this.http.get<Alquiler[]>(`${this.apiUrl}/alquileres/mis-alquileres`);
  }

  /**
   * Obtiene el detalle de un alquiler del cliente autenticado con validación de propiedad
   */
  getMiAlquilerDetalle(id: number): Observable<Alquiler> {
    return this.http.get<Alquiler>(`${this.apiUrl}/alquileres/mis-alquileres/${id}`);
  }

  /**
   * Aprueba un alquiler pendiente
   */
  aprobarAlquiler(id: number, usuarioId: number): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.apiUrl}/alquileres/${id}/aprobar`, { usuarioId });
  }

  /**
   * Rechaza un alquiler pendiente
   */
  rechazarAlquiler(id: number, motivoRechazo?: string): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.apiUrl}/alquileres/${id}/rechazar`, { motivoRechazo });
  }

  /**
   * Registra la entrega de maquinaria (activa el alquiler)
   */
  entregarAlquiler(id: number): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.apiUrl}/alquileres/${id}/entregar`, {});
  }

  /**
   * Finaliza un alquiler activo (devolución de maquinaria)
   */
  finalizarAlquiler(id: number): Observable<Alquiler> {
    return this.http.patch<Alquiler>(`${this.apiUrl}/alquileres/${id}/finalizar`, {});
  }
}
