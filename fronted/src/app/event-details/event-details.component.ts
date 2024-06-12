import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CompraModalComponent } from '../components/compra-modal/compra-modal.component';

@Component({
  selector: 'app-event-details',
  template: `
    <div class="event-details-container">
      <div class="event-card">
        <h2>{{ data.nombre }}</h2>
        <p>{{ data.descripcion }}</p>
        <p><strong>Fecha:</strong> {{ data.fecha | date }}</p>
        <p><strong>Precio:</strong> {{ data.precio | currency }}</p>
        <img [src]="data.imagenUrl || 'assets/default-image.png'" alt="Event image">
        <div class="event-details-actions">
          <button mat-raised-button color="primary" (click)="buy()">Comprar</button>
          <button mat-raised-button (click)="close()">Cerrar</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .event-details-container {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 20px;
    }
    .event-card {
      background-color: #444;
      color: #fff;
      padding: 20px;
      border-radius: 10px;
      text-align: center;
      width: 300px;
    }
    .event-card img {
      width: 100%;
      height: auto;
      border-radius: 10px;
      margin: 20px 0;
    }
    .event-details-actions {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
    button {
      width: 100px;
    }
  `],
  standalone: true,
  imports: [CommonModule, MatButtonModule]
})
export class EventDetailsComponent {
  constructor(
    public dialogRef: MatDialogRef<EventDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialog: MatDialog,
    private authService: AuthService,
    private router: Router
  ) {}

  close(): void {
    this.dialogRef.close();
  }

  buy(): void {
    console.log('Buy button clicked');
    if (!this.authService.isAuthenticated()) {
      console.log('User not authenticated, redirecting to login');
      this.router.navigate(['/login'], { queryParams: { returnUrl: this.router.url } });
    } else {
      console.log('User is authenticated, opening compra modal');
      this.dialog.open(CompraModalComponent, {
        data: { eventId: this.data.id }
      });
    }
  }
}
