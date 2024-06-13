import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private router: Router) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/registro`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post<{ accessToken: string, clienteId: number }>(`${this.baseUrl}/auth/login`, credentials).pipe(
      tap(response => {
        this.saveToken(response.accessToken);
        this.saveClienteId(response.clienteId);
      })
    );
  }

  logout() {
    console.log('Logout called');
    localStorage.removeItem('token');
    localStorage.removeItem('clienteId');
    this.router.navigate(['/login']);
  }

  saveToken(token: string) {
    console.log('Saving token:', token);
    localStorage.setItem('token', token);
  }

  saveClienteId(clienteId: number) {
    console.log('Saving clienteId:', clienteId);
    localStorage.setItem('clienteId', clienteId.toString());
  }

  getToken(): string | null {
    const token = localStorage.getItem('token');
    console.log('Retrieved token:', token);
    return token;
  }

  getClienteId(): number | null {
    const clienteId = localStorage.getItem('clienteId');
    console.log('Retrieved clienteId:', clienteId);
    return clienteId ? parseInt(clienteId) : null;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    console.log('Token in isAuthenticated:', token);
    return token !== null && token !== 'undefined' && token !== '';
  }
}
