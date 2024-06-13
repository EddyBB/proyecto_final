import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { ClienteService } from '../../services/cliente.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-compras-usuario-modal',
  templateUrl: './compras-usuario-modal.component.html',
  styleUrls: ['./compras-usuario-modal.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatCardModule
  ]
})
export class ComprasUsuarioModalComponent implements OnInit {
  compras: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ComprasUsuarioModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.loadCompras();
  }

  loadCompras() {
    this.clienteService.getCompras().subscribe({
      next: data => {
        this.compras = data;
      },
      error: err => {
        console.error('Error loading compras', err);
      }
    });
  }

  close(): void {
    this.dialogRef.close();
  }
}
