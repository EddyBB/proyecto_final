import { Component, OnInit } from '@angular/core';
import { clientes } from '../../models/clientes';
import { ClientesService } from '../../services/clientes.service';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { ClienteFormComponent } from '../clientes-form/clientes-form.component';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatButtonModule, MatDialogModule],
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit {

  title: string = 'Listado de clientes';

  clientes: clientes[] = [];
  displayedColumns: string[] = ['idCliente', 'nombre', 'apellidos', 'email', 'telefono', 'acciones'];

  constructor(private service: ClientesService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.service.findAll().subscribe(clientes => this.clientes = clientes);
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(ClienteFormComponent, {
      width: '300px',
      data: { cliente: null }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.create(result).subscribe(() => this.loadClientes());
      }
    });
  }

  openEditDialog(cliente: clientes): void {
    const dialogRef = this.dialog.open(ClienteFormComponent, {
      width: '300px',
      data: { cliente: cliente }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.service.update(cliente.idCliente, result).subscribe(() => this.loadClientes());
      }
    });
  }

  deleteCliente(id: number): void {
    this.service.delete(id).subscribe(() => this.loadClientes());
  }
}
