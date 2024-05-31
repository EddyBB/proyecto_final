import { Injectable } from '@angular/core';
import { clientes } from '../models/clientes';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private clientes: clientes[] =[{

    idCliente: 1,
    nombre:  'Eddy',
    apellidos: 'Gonzalez',
    email: 'eddy@gmail.com',
    telefono: 123456789,
    password: 'eddy'
  },
  {
    idCliente: 2,
    nombre:  'Paco',
    apellidos: 'Rodriguez',
    email: 'paco@gmail.com',
    telefono: 123456789,
    password: 'paco'
  },
  {
    idCliente: 3,
    nombre:  'Puiu',
    apellidos: 'Marius',
    email: 'puiu@gmail.com',
    telefono: 123456789,
    password: 'puiu'
  }]

  constructor() { }

  findAll(): Observable<clientes[]> {
    return of(this.clientes);
  }
}
