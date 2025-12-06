import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';
import { Maquinaria, CrearMaquinariaRequest, ActualizarMaquinariaRequest } from '@core/models/maquinaria.model';

@Injectable({
  providedIn: 'root'
})
export class MaquinariaService {
  private apiUrl = `${environment.apiUrl}/maquinaria`;

  constructor(private http: HttpClient) { }

  getMaquinarias(): Observable<Maquinaria[]> {
    return this.http.get<Maquinaria[]>(this.apiUrl);
  }

  getMaquinariaById(id: number): Observable<Maquinaria> {
    return this.http.get<Maquinaria>(`${this.apiUrl}/${id}`);
  }

  createMaquinaria(data: CrearMaquinariaRequest): Observable<Maquinaria> {
    return this.http.post<Maquinaria>(this.apiUrl, data);
  }

  updateMaquinaria(id: number, data: ActualizarMaquinariaRequest): Observable<Maquinaria> {
    return this.http.put<Maquinaria>(`${this.apiUrl}/${id}`, data);
  }

  deleteMaquinaria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}