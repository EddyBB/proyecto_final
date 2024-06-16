import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class AdminHeaderComponent {

  constructor(private router: Router, private authService: AuthService) {}

  navigateToAdminPanel(event: MouseEvent) {
    event.preventDefault(); // Evitar la recarga de la página
    console.log('Navigating to Admin Panel');
    this.router.navigate(['/admin']);
  }

  logout(event: MouseEvent) {
    event.preventDefault(); // Evitar la recarga de la página
    this.authService.logout();
  }
}
