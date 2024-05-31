import { Component, OnInit } from '@angular/core';
import { clientes } from '../../models/clientes';
import { ClientesService } from '../../services/clientes.service';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [MatTableModule, MatIconModule],
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit{

  title: string = 'Listado de clientes'

  clientes: clientes[] = [];
  displayedColumns: string[] = ['idCliente', 'nombre', 'apellidos', 'email', 'telefono', 'acciones'];

  constructor(private service: ClientesService) {

  }

  ngOnInit(): void {
    this.service.findAll().subscribe(clientes => this.clientes = clientes);
  }
}
