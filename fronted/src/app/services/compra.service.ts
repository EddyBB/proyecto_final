import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseUrl = 'http://localhost:8080/api/client';

  constructor(private http: HttpClient) {}

  comprar(compraData: { clienteId: number, eventoId: number, cantidadEntradas: number, fechaCompra: string }): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.baseUrl}/comprar`, compraData, { headers });
  }

  verificarDisponibilidad(eventoId: number, cantidad: number): Observable<boolean> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<boolean>(`${this.baseUrl}/verificar-disponibilidad?eventoId=${eventoId}&cantidad=${cantidad}`, { headers });
  }
}
