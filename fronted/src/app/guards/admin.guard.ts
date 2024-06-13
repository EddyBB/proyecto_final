import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { map } from 'rxjs/operators';

export const adminGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.userRole$.pipe(
    map(role => {
      if (role === 'ADMIN') {
        return true;
      } else {
        router.navigate(['/']);
        return false;
      }
    })
  );
};
