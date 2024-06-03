import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogActions } from '@angular/material/dialog';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { clientes } from '../../models/clientes';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './clientes-form.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatDialogActions]
})
export class ClienteFormComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ClienteFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { cliente: clientes }
  ) {
    this.form = this.fb.group({
      idCliente: [data.cliente ? data.cliente.idCliente : ''],
      nombre: [data.cliente ? data.cliente.nombre : ''],
      apellidos: [data.cliente ? data.cliente.apellidos : ''],
      email: [data.cliente ? data.cliente.email : ''],
      telefono: [data.cliente ? data.cliente.telefono : ''],
      password: [data.cliente ? data.cliente.password : ''],
      rol: this.fb.group({
        idRol: [data.cliente ? data.cliente.rol.idRol : 2], // ID del rol "cliente"
        tipoRol: [data.cliente ? data.cliente.rol.tipoRol : 'cliente']
      })
    });
  }

  onSave(): void {
    this.dialogRef.close(this.form.value);
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
