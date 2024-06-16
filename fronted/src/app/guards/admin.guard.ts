import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { map, switchMap, take } from 'rxjs/operators';
import { of } from 'rxjs';

export const adminGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated$.pipe(
    take(1), // Solo toma el primer valor emitido
    switchMap(isAuthenticated => {
      if (isAuthenticated) {
        return authService.userRole$.pipe(
          take(1),
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
      } else {
        console.log('adminGuard - not authenticated, redirecting to home');
        router.navigate(['/']);
        return of(false);
      }
    })
  );
};
