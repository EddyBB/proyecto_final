import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { adminGuard } from './guards/admin.guard';
import { AdminEventListComponent } from './components/admin-event-list/admin-event-list.component';
import { AdminPurchaseListComponent } from './components/admin-purchase-list/admin-purchase-list.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin', component: AdminPanelComponent , canActivate: [adminGuard]},
  {  path: 'admin/events', component: AdminEventListComponent, canActivate: [adminGuard]},
  { path: 'admin/purchases', component: AdminPurchaseListComponent, canActivate: [adminGuard] },
  { path: '**', redirectTo: '' }
];
