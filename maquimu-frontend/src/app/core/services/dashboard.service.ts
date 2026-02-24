import { environment } from '@environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface DashboardOperario {
  maquinariaDisponible: number;
  alquileresActivos: number;
  facturasPendientes: number;
}

export interface DashboardCliente {
  alquileresActivos: number;
  facturasPendientes: number;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = `${environment.apiUrl}/v1/dashboard`;

  constructor(private http: HttpClient) {}

  getOperarioStats(): Observable<DashboardOperario> {
    return this.http.get<DashboardOperario>(`${this.apiUrl}/operario`);
  }

  getClienteStats(): Observable<DashboardCliente> {
    return this.http.get<DashboardCliente>(`${this.apiUrl}/cliente`);
  }
}
