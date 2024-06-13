import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private baseUrl = 'http://localhost:8080/api/clientes';
  private adminBaseUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getProfile(): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.get(`${this.baseUrl}/me`, { headers });
  }

  updateProfile(profileData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.put(`${this.baseUrl}/update`, profileData, { headers });
  }

  getCompras(): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.get(`${this.baseUrl}/me/compras`, { headers });
  }

  getAdminProfile(adminId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    console.log(`Headers for getAdminProfile: ${JSON.stringify(headers)}`);  // Log para verificar los headers
    return this.http.get(`${this.adminBaseUrl}/clientes/${adminId}`, { headers });
  }

  updateAdminProfile(adminId: number, profileData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.put(`${this.adminBaseUrl}/clientes/${adminId}`, profileData, { headers });
  }
}
