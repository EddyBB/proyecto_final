import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient, private router: Router) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/registro`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post<{ accessToken: string, clienteId: number }>(`${this.baseUrl}/auth/login`, credentials).pipe(
      tap(response => {
        this.saveToken(response.accessToken);
        this.saveClienteId(response.clienteId);
        this.isAuthenticatedSubject.next(true);
      })
    );
  }

  logout() {
    console.log('Logout called');
    if (this.isBrowser()) {
      localStorage.removeItem('token');
      localStorage.removeItem('clienteId');
    }
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/']);
  }

  saveToken(token: string) {
    if (this.isBrowser()) {
      console.log('Saving token:', token);
      localStorage.setItem('token', token);
    }
  }

  saveClienteId(clienteId: number) {
    if (this.isBrowser()) {
      console.log('Saving clienteId:', clienteId);
      localStorage.setItem('clienteId', clienteId.toString());
    }
  }

  getToken(): string | null {
    if (this.isBrowser()) {
      const token = localStorage.getItem('token');
      console.log('Retrieved token:', token);
      return token;
    }
    return null;
  }

  getClienteId(): number | null {
    if (this.isBrowser()) {
      const clienteId = localStorage.getItem('clienteId');
      console.log('Retrieved clienteId:', clienteId);
      return clienteId ? parseInt(clienteId) : null;
    }
    return null;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    console.log('Token in isAuthenticated:', token);
    return token !== null && token !== 'undefined' && token !== '';
  }

  hasToken(): boolean {
    return this.isBrowser() && !!localStorage.getItem('token');
  }

  get isAuthenticated$(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }
}
