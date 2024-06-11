import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { RegisterService } from '../../services/register.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule]
})
export class RegisterComponent {
  registerForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private registerService: RegisterService
  ) {
    this.registerForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      password: ['', Validators.required],
      imagenPerfil: [null]
    });
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      return;
    }

    const formData = new FormData();
    formData.append('nombre', this.registerForm.get('nombre')?.value);
    formData.append('apellidos', this.registerForm.get('apellidos')?.value);
    formData.append('email', this.registerForm.get('email')?.value);
    formData.append('telefono', this.registerForm.get('telefono')?.value);
    formData.append('password', this.registerForm.get('password')?.value);
    if (this.selectedFile) {
      formData.append('imagenPerfil', this.selectedFile);
    }

    this.registerService.register(formData).subscribe(
      response => {
        console.log('Usuario registrado exitosamente', response);
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Error al registrar usuario', error);
      }
    );
  }
}
