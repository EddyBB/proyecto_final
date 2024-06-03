import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { clientes } from '../models/clientes';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private baseUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) { }

  findAll(): Observable<clientes[]> {
    return this.http.get<clientes[]>(this.baseUrl);
  }

  create(cliente: clientes): Observable<clientes> {
    return this.http.post<clientes>(this.baseUrl, cliente);
  }

  update(id: number, cliente: clientes): Observable<clientes> {
    return this.http.put<clientes>(`${this.baseUrl}/${id}`, cliente);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
