// src/app/services/evento.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { eventos } from '../models/eventos';


@Injectable({
  providedIn: 'root'
})
export class EventoService {
  private apiUrl = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) { }

  getEventos(): Observable<eventos[]> {
    return this.http.get<eventos[]>(this.apiUrl);
  }

  getEventoById(id: number): Observable<eventos> {
    return this.http.get<eventos>(`${this.apiUrl}/${id}`);
  }
}

