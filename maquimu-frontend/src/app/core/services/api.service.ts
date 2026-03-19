import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api/maquimu/v1'; // TODO: Move to environment config

  constructor(private http: HttpClient) { }

  /**
   * Generic GET request - stub
   */
  get<T>(endpoint: string): Observable<T> {
    console.log(`ApiService.get() - Stub method called for ${endpoint}`);
    return this.http.get<T>(`${this.baseUrl}${endpoint}`);
  }

  /**
   * Generic POST request - stub
   */
  post<T>(endpoint: string, data: any): Observable<T> {
    console.log(`ApiService.post() - Stub method called for ${endpoint}`);
    return this.http.post<T>(`${this.baseUrl}${endpoint}`, data);
  }

  /**
   * Generic PUT request - stub
   */
  put<T>(endpoint: string, data: any): Observable<T> {
    console.log(`ApiService.put() - Stub method called for ${endpoint}`);
    return this.http.put<T>(`${this.baseUrl}${endpoint}`, data);
  }

  /**
   * Generic DELETE request - stub
   */
  delete<T>(endpoint: string): Observable<T> {
    console.log(`ApiService.delete() - Stub method called for ${endpoint}`);
    return this.http.delete<T>(`${this.baseUrl}${endpoint}`);
  }
}
