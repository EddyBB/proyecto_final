import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CompraService } from '../../services/compra.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-compra-modal',
  template: `
    <div class="compra-modal-container">
      <h2>Comprar Entradas</h2>
      <mat-form-field appearance="fill">
        <mat-label>Cantidad de Entradas</mat-label>
        <input matInput type="number" [(ngModel)]="cantidadEntradas" min="1">
      </mat-form-field>
      <div class="compra-modal-actions">
        <button mat-button (click)="close()">Cancelar</button>
        <button mat-raised-button color="primary" (click)="buy()">Comprar</button>
      </div>
    </div>
  `,
  styles: [`
    .compra-modal-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 20px;
    }
    .compra-modal-actions {
      display: flex;
      justify-content: space-between;
      width: 100%;
      margin-top: 20px;
    }
  `],
  standalone: true,
  imports: [CommonModule, FormsModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatDialogModule]
})
export class CompraModalComponent {
  cantidadEntradas = 1;

  constructor(
    public dialogRef: MatDialogRef<CompraModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private compraService: CompraService,
    private authService: AuthService,
    private router: Router
  ) {}

  close(): void {
    this.dialogRef.close();
  }

  buy(): void {
    if (!this.authService.isAuthenticated()) {
      console.log('User not authenticated, redirecting to login');
      this.dialogRef.close();
      this.router.navigate(['/login'], { queryParams: { returnUrl: this.router.url } });
    } else {
      this.compraService.comprar(this.data.eventId, this.cantidadEntradas).subscribe(response => {
        // Handle the response, show a success message, etc.
        this.dialogRef.close();
      });
    }
  }
}
