import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Maquinaria } from '../models/maquinaria.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class MaquinariaService {
    private apiUrl = `${environment.apiUrl}/maquinarias`;

    constructor(private http: HttpClient) { }

    getAllMaquinarias(): Observable<Maquinaria[]> {
        return this.http.get<Maquinaria[]>(this.apiUrl);
    }

    getMaquinariaById(id: number): Observable<Maquinaria> {
        return this.http.get<Maquinaria>(`${this.apiUrl}/${id}`);
    }

    createMaquinaria(maquinaria: Maquinaria): Observable<Maquinaria> {
        return this.http.post<Maquinaria>(this.apiUrl, maquinaria);
    }

    updateMaquinaria(id: number, maquinaria: Maquinaria): Observable<Maquinaria> {
        return this.http.put<Maquinaria>(`${this.apiUrl}/${id}`, maquinaria);
    }

    deleteMaquinaria(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
