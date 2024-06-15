import { Component, Inject, Output, EventEmitter, AfterViewInit } from '@angular/core';
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
import { firstValueFrom } from 'rxjs';

declare var paypal: any; // Declarar el objeto paypal

@Component({
  selector: 'app-compra-modal',
  template: `
    <div class="compra-modal-container">
      <h2>Comprar Entradas</h2>
      <mat-form-field appearance="fill">
        <mat-label>Cantidad de Entradas</mat-label>
        <input matInput type="number" [(ngModel)]="cantidadEntradas" min="1">
      </mat-form-field>
      <div id="paypal-button-container"></div>
      <div class="compra-modal-actions">
        <button mat-button (click)="close()">Cancelar</button>
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
    .no-entradas {
      color: red;
      font-weight: bold;
      margin-top: 10px;
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
export class CompraModalComponent implements AfterViewInit {
  @Output() compraRealizada = new EventEmitter<void>(); // Añadido EventEmitter
  cantidadEntradas = 1;

  constructor(
    public dialogRef: MatDialogRef<CompraModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private compraService: CompraService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngAfterViewInit(): void {
    paypal.Buttons({
      createOrder: async (data: any, actions: any) => {
        try {
          const disponible = await firstValueFrom(this.compraService.verificarDisponibilidad(this.data.eventId, this.cantidadEntradas));
          if (disponible) {
            return actions.order.create({
              purchase_units: [{
                amount: {
                  value: this.data.precio * this.cantidadEntradas // Total a pagar
                }
              }]
            });
          } else {
            this.snackBar.open('No hay suficientes entradas disponibles.', 'Cerrar', {
              duration: 3000,
              verticalPosition: 'top',
              horizontalPosition: 'center'
            });
            throw new Error('No hay suficientes entradas disponibles.');
          }
        } catch (error) {
          console.error(error);
          throw error;
        }
      },
      onApprove: (data: any, actions: any) => {
        return actions.order.capture().then((details: any) => {
          this.processPurchase();
        });
      },
      onError: (err: any) => {
        console.error(err);
        this.snackBar.open('Error al realizar el pago con PayPal. Inténtalo de nuevo.', 'Cerrar', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'center'
        });
      }
    }).render('#paypal-button-container');
  }

  close(): void {
    this.dialogRef.close();
  }

  processPurchase(): void {
    const clienteId = this.authService.getClienteId();
    const eventoId = this.data.eventId;
    const cantidad = this.cantidadEntradas;
    const fechaCompra = new Date();

    if (clienteId && eventoId) {
      const compraData = {
        clienteId: clienteId,
        eventoId: eventoId,
        cantidadEntradas: cantidad,
        fechaCompra: fechaCompra.toISOString()
      };

      this.compraService.comprar(compraData).subscribe({
        next: response => {
          console.log('Compra exitosa:', response);
          this.snackBar.open('Compra exitosa', 'Cerrar', {
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'center'
          });
          this.compraRealizada.emit(); // Emitir evento de compra realizada
          this.dialogRef.close();
        },
        error: err => {
          console.error('Error al realizar la compra:', err);
          this.snackBar.open(err.error || 'Error al realizar la compra. Inténtalo de nuevo o más tarde.', 'Cerrar', {
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
