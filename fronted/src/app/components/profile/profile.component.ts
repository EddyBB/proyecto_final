import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ComprasUsuarioModalComponent } from '../compras-usuario-modal/compras-usuario-modal.component';
import { PasswordModalComponent } from '../password-modal/password-modal.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    HeaderComponent,
    FooterComponent,
    CommonModule
  ]
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  userProfile: any;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private authService: AuthService,
    private router: Router,
    public dialog: MatDialog
  ) {
    this.profileForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      imagenPerfil: ['']
    });
  }

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(isAuthenticated => {
      if (!isAuthenticated) {
        this.router.navigate(['/']);
      } else {
        this.loadUserProfile();
      }
    });
  }

  loadUserProfile() {
    this.clienteService.getProfile().subscribe({
      next: data => {
        this.userProfile = data;
        this.profileForm.patchValue(this.userProfile);
      },
      error: err => {
        console.error('Error loading user profile', err);
      }
    });
  }

  updateProfile() {
    if (this.profileForm.valid && this.profileForm.dirty) {
      const updatedProfile = { ...this.userProfile, ...this.profileForm.value };
      delete updatedProfile.password; // Eliminar la contraseña del objeto actualizado
      this.clienteService.updateProfile(updatedProfile).subscribe({
        next: response => {
          console.log('Profile updated successfully', response);
          this.profileForm.markAsPristine();
        },
        error: err => {
          console.error('Error updating profile', err);
        }
      });
    } else {
      console.log('No changes detected in the form');
    }
  }

  openComprasModal(): void {
    this.dialog.open(ComprasUsuarioModalComponent, {
      width: '80%',
      data: { clienteId: this.userProfile.idCliente }
    });
  }

  openPasswordModal(): void {
    const dialogRef = this.dialog.open(PasswordModalComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(newPassword => {
      if (newPassword) {
        this.clienteService.updateProfile({ ...this.userProfile, password: newPassword }).subscribe({
          next: response => {
            console.log('Password updated successfully', response);
          },
          error: err => {
            console.error('Error updating password', err);
          }
        });
      }
    });
  }
}
