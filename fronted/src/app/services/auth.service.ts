import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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
    return this.http.post<{ accessToken: string }>(`${this.baseUrl}/auth/login`, credentials);
  }

  logout() {
    console.log('Logout called');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  saveToken(token: string) {
    console.log('Saving token:', token);
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    const token = localStorage.getItem('token');
    console.log('Retrieved token:', token);
    return token;
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    const authenticated = token !== null && token !== undefined;
    console.log('Is authenticated:', authenticated);
    return authenticated;
  }
}
