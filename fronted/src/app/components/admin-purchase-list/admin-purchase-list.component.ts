import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { Compra } from '../../models/compra.model';
import { AdminEventService } from '../../services/admin-event.service';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { AdminFooterComponent } from '../admin-footer/admin-footer.component';

@Component({
  selector: 'app-admin-purchase-list',
  templateUrl: './admin-purchase-list.component.html',
  styleUrls: ['./admin-purchase-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    AdminHeaderComponent,
    AdminFooterComponent
  ]
})
export class AdminPurchaseListComponent implements OnInit {
  purchases: Compra[] = [];
  displayedColumns: string[] = ['idCompra', 'clienteNombre', 'eventoNombre', 'fechaCompra', 'precioEntrada', 'cantidadEntradas', 'precioTotal', 'acciones'];

  constructor(private purchaseService: AdminEventService) {}

  ngOnInit(): void {
    this.loadPurchases();
  }

  loadPurchases(): void {
    this.purchaseService.getPurchases().subscribe({
      next: (data) => {
        this.purchases = data;
      },
      error: (err) => {
        console.error('Error loading purchases', err);
      }
    });
  }

  deletePurchase(id: number): void {
    this.purchaseService.deletePurchase(id).subscribe({
      next: () => {
        this.loadPurchases();
      },
      error: (err) => {
        console.error('Error deleting purchase', err);
      }
    });
  }
}
