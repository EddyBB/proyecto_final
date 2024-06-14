import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin', component: AdminPanelComponent , canActivate: [adminGuard]},
  { path: 'admin/events', component: AdminPanelComponent, canActivate: [adminGuard] },
  { path: 'admin/purchases', component: AdminPanelComponent, canActivate: [adminGuard] },
  { path: 'admin/discotecas', component: AdminPanelComponent, canActivate: [adminGuard] },
  { path: 'admin/salas', component: AdminPanelComponent, canActivate: [adminGuard] },
  { path: '**', redirectTo: '' }
];
