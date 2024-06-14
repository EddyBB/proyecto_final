import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from '../models/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  getEventsByDate(date?: string | null): Observable<Event[]> {
    const url = date ? `${this.baseUrl}?date=${date}` : this.baseUrl;
    return this.http.get<Event[]>(url);
  }

  getEventsBySearchQuery(query: string): Observable<Event[]> {
    const url = `${this.baseUrl}?search=${query}`;
    return this.http.get<Event[]>(url);
  }
}
