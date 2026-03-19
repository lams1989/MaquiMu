import { environment } from '@environments/environment';
import { EstadoPago, Factura } from '@core/models/factura.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
  private apiUrl = `${environment.apiUrl}/v1/facturas`;

  constructor(private http: HttpClient) {}

  /**
   * Genera una factura para un alquiler finalizado.
   */
  generarFactura(alquilerId: number): Observable<Factura> {
    return this.http.post<Factura>(this.apiUrl, { alquilerId });
  }

  /**
   * Lista todas las facturas, opcionalmente filtradas por estado o cliente.
   */
  getFacturas(estadoPago?: EstadoPago, clienteId?: number): Observable<Factura[]> {
    let params = new HttpParams();
    if (estadoPago) {
      params = params.set('estadoPago', estadoPago);
    }
    if (clienteId) {
      params = params.set('clienteId', clienteId.toString());
    }
    return this.http.get<Factura[]>(this.apiUrl, { params });
  }

  /**
   * Obtiene una factura por ID.
   */
  getFactura(id: number): Observable<Factura> {
    return this.http.get<Factura>(`${this.apiUrl}/${id}`);
  }

  /**
   * Obtiene la factura de un alquiler específico.
   */
  getFacturaPorAlquiler(alquilerId: number): Observable<Factura> {
    return this.http.get<Factura>(`${this.apiUrl}/alquiler/${alquilerId}`);
  }

  /**
   * Actualiza el estado de pago de una factura.
   */
  actualizarEstadoPago(facturaId: number, estadoPago: EstadoPago): Observable<Factura> {
    return this.http.patch<Factura>(`${this.apiUrl}/${facturaId}/estado`, { estadoPago });
  }

  /**
   * Obtiene la URL para descargar el PDF de una factura.
   */
  getUrlPdf(facturaId: number): string {
    return `${this.apiUrl}/${facturaId}/pdf`;
  }

  /**
   * Descarga el PDF de una factura como blob.
   */
  descargarPdf(facturaId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${facturaId}/pdf`, {
      responseType: 'blob'
    });
  }

  // ========== Endpoints para CLIENTE ==========

  /**
   * Lista las facturas del cliente autenticado.
   */
  getMisFacturas(): Observable<Factura[]> {
    return this.http.get<Factura[]>(`${this.apiUrl}/mis-facturas`);
  }

  /**
   * Descarga el PDF de una factura del cliente autenticado.
   */
  descargarMiPdf(facturaId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/mis-facturas/${facturaId}/pdf`, {
      responseType: 'blob'
    });
  }
}
