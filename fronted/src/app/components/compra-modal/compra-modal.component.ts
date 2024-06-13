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
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

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
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatSnackBarModule
  ]
})
export class CompraModalComponent {
  cantidadEntradas = 1;

  constructor(
    public dialogRef: MatDialogRef<CompraModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private compraService: CompraService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    console.log('Constructor data:', this.data); // Verifica si el eventId está presente en el constructor
  }

  close(): void {
    this.dialogRef.close();
  }

  buy(): void {
    if (!this.authService.isAuthenticated()) {
      console.log('User not authenticated, redirecting to login');
      this.dialogRef.close();
      this.router.navigate(['/login'], { queryParams: { returnUrl: this.router.url } });
    } else {
      const clienteId = this.authService.getClienteId();
      const eventoId = this.data.eventId;  // Cambia idEvento a eventId
      const cantidad = this.cantidadEntradas;
      const fechaCompra = new Date();  // Añade la fecha de compra actual

      console.log('Retrieved clienteId:', clienteId); // Log para verificar clienteId
      console.log('Retrieved eventoId:', eventoId); // Log para verificar eventoId
      console.log('Fecha de compra:', fechaCompra); // Log para verificar fechaCompra

      if (clienteId && eventoId) {
        const compraData = {
          clienteId: clienteId,
          eventoId: eventoId,
          cantidadEntradas: cantidad,
          fechaCompra: fechaCompra.toISOString()  // Formatea la fecha a ISO string
        };

        this.compraService.comprar(compraData).subscribe({
          next: response => {
            console.log('Compra exitosa:', response);
            this.snackBar.open('Compra exitosa', 'Cerrar', {
              duration: 3000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            });
            this.dialogRef.close();
          },
          error: err => {
            console.error('Error al realizar la compra:', err);
            this.snackBar.open('Error al realizar la compra. Inténtalo de nuevo o más tarde.', 'Cerrar', {
              duration: 3000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            });
          }
        });
      } else {
        console.error('Cliente ID or Event ID is null');
        this.snackBar.open('Cliente ID o Event ID es nulo. Inténtalo de nuevo.', 'Cerrar', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center'
        });
      }
    }
  }
}
