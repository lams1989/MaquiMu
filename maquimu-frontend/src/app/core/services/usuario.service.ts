import { environment } from '@environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioPendiente } from '@core/models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiUrl}/v1/usuarios`;

  constructor(private http: HttpClient) { }

  getUsuariosPendientes(): Observable<UsuarioPendiente[]> {
    return this.http.get<UsuarioPendiente[]>(`${this.apiUrl}/pendientes`);
  }

  aprobarUsuario(usuarioId: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${usuarioId}/aprobar`, {});
  }

  rechazarUsuario(usuarioId: number, motivo: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/${usuarioId}/rechazar`, { motivo });
  }
}
