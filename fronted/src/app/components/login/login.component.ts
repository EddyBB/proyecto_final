import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule]
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;
  returnUrl: string;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(this.loginForm.value).subscribe({
      next: (response: { accessToken: string, clienteId: number, rol: string }) => {
        console.log('Login successful, token:', response.accessToken);
        if (response.accessToken) {
          this.authService.saveToken(response.accessToken);
          this.authService.saveClienteId(response.clienteId);
          this.authService.saveUserRole(response.rol);
          
          if (response.rol === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate([this.returnUrl]);
          }
        } else {
          this.errorMessage = 'No token found in response';
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Login failed';
      }
    });
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }
}
