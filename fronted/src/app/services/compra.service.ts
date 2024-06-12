import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseUrl = 'http://localhost:8080/api/client';

  constructor(private http: HttpClient) {}

  comprar(eventId: number, cantidadEntradas: number): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.baseUrl}/comprar`, { eventId, cantidadEntradas }, { headers });
  }
}
