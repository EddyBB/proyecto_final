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
  private userRoleSubject = new BehaviorSubject<string | null>(this.getUserRole());

  constructor(private http: HttpClient, private router: Router) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/registro`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post<{ accessToken: string, clienteId: number, rol: string }>(`${this.baseUrl}/auth/login`, credentials).pipe(
      tap(response => {
        this.saveToken(response.accessToken);
        this.saveClienteId(response.clienteId);
        this.saveUserRole(response.rol);
        this.isAuthenticatedSubject.next(true);
        this.userRoleSubject.next(response.rol);
        this.updateUserRole(response.rol);
        console.log('Role updated in login:', response.rol);
      })
    );
  }

  logout() {
    if (this.isBrowser()) {
      localStorage.removeItem('token');
      localStorage.removeItem('clienteId');
      localStorage.removeItem('userRole');
    }
    this.isAuthenticatedSubject.next(false);
    this.userRoleSubject.next(null);
    this.updateUserRole(null);
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

  saveUserRole(role: string) {
    if (this.isBrowser()) {
      console.log('Saving role:', role);
      localStorage.setItem('userRole', role);
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

  getUserRole(): string | null {
    const role = this.isBrowser() ? localStorage.getItem('userRole') : null;
    console.log('Retrieved userRole:', role); // Log para verificar el rol del usuario
    return role;
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

  get userRole$(): Observable<string | null> {
    return this.userRoleSubject.asObservable();
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

    // Nuevo método para actualizar el rol del usuario
    updateUserRole(role: string | null) {
      console.log('Updating userRoleSubject:', role);
      this.userRoleSubject.next(role);
    }
}
