import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';
import { SimpleEvent } from '../models/simple-event.model';
import { Discoteca } from '../models/discoteca.model';
import { Sala } from '../models/sala.model';

@Injectable({
  providedIn: 'root'
})
export class AdminEventService {
  private baseUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders() {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
  }

  getEvents(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/eventos`, { headers }).pipe(
      map((events: any[]) => {
        return events.map(event => ({
          ...event,
          discotecaNombre: event.discoteca ? event.discoteca.nombre : 'Not found',
          salaNombre: event.sala ? event.sala.nombre : 'Not found'
        }));
      })
    );
  }

  getEventById(id: number): Observable<SimpleEvent> {
    const headers = this.getAuthHeaders();
    return this.http.get<SimpleEvent>(`${this.baseUrl}/eventos/${id}`, { headers });
  }

  createEvent(event: SimpleEvent): Observable<SimpleEvent> {
    const headers = this.getAuthHeaders();
    console.log('Creating event with data:', event);
    return this.http.post<SimpleEvent>(`${this.baseUrl}/eventos`, event, { headers });
  }

  updateEvent(id: number, event: SimpleEvent): Observable<SimpleEvent> {
    const headers = this.getAuthHeaders();
    console.log('Updating event with data:', event);
    return this.http.put<SimpleEvent>(`${this.baseUrl}/eventos/${id}`, event, { headers });
  }

  deleteEvent(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.baseUrl}/eventos/${id}`, { headers });
  }

  getDiscotecas(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/discotecas`, { headers });
  }

  createDiscoteca(discoteca: Discoteca): Observable<Discoteca> {
    const headers = this.getAuthHeaders();
    console.log('Creating new discoteca with data:', discoteca);
    return this.http.post<Discoteca>(`${this.baseUrl}/discotecas`, discoteca, { headers });
  }

  updateDiscoteca(id: number, discoteca: Discoteca): Observable<Discoteca> {
    const headers = this.getAuthHeaders();
    console.log('Updating discoteca with data:', discoteca);
    return this.http.put<Discoteca>(`${this.baseUrl}/discotecas/${id}`, discoteca, { headers });
  }

  deleteDiscoteca(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.baseUrl}/discotecas/${id}`, { headers });
  }

  getSalas(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.baseUrl}/salas`, { headers });
  }

  createSala(sala: Sala): Observable<Sala> {
    const headers = this.getAuthHeaders();
    console.log('Creating new sala with data:', sala);
    return this.http.post<Sala>(`${this.baseUrl}/salas`, sala, { headers });
  }

  updateSala(id: number, sala: Sala): Observable<Sala> {
    const headers = this.getAuthHeaders();
    console.log('Updating sala with data:', sala);
    return this.http.put<Sala>(`${this.baseUrl}/salas/${id}`, sala, { headers });
  }

  deleteSala(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.baseUrl}/salas/${id}`, { headers });
  }
}
