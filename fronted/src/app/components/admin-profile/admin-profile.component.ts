import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { AdminFooterComponent } from '../admin-footer/admin-footer.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, AdminHeaderComponent, AdminFooterComponent, MatFormFieldModule, MatInputModule, MatButtonModule]
})
export class AdminProfileComponent implements OnInit {
  profileForm: FormGroup;
  adminProfile: any;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private authService: AuthService,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      password: ['', Validators.required],
      imagenPerfil: ['']
    });
  }

  ngOnInit(): void {
    const adminId = this.authService.getClienteId();
    if (adminId === null) {
      console.error('Admin ID is null');
      this.router.navigate(['/login']);
      return;
    }
    this.clienteService.getAdminProfile(adminId).subscribe({
      next: data => {
        this.adminProfile = data;
        this.profileForm.patchValue(this.adminProfile);
      },
      error: err => {
        console.error('Error loading admin profile', err);
      }
    });
  }

  updateProfile() {
    if (this.profileForm.valid) {
      const adminId = this.authService.getClienteId();
      if (adminId === null) {
        console.error('Admin ID is null');
        this.router.navigate(['/login']);
        return;
      }
      this.clienteService.updateAdminProfile(adminId, this.profileForm.value).subscribe({
        next: response => {
          console.log('Profile updated successfully', response);
        },
        error: err => {
          console.error('Error updating profile', err);
        }
      });
    }
  }
}
