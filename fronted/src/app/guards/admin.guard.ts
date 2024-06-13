import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { map } from 'rxjs/operators';

export const adminGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.userRole$.pipe(
    map(role => {
      console.log('adminGuard - role:', role); // Log para verificar el rol
      if (role === 'ADMIN') {
        return true;
      } else {
        console.log('adminGuard - not admin, redirecting to home'); // Log para redirección
        router.navigate(['/']);
        return false;
      }
    })
  );
};
