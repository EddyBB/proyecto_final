import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { EventosComponent } from './components/eventos/eventos.component';
// Importa otros componentes aquí

export const routes: Routes = [
  { path: '', component: EventosComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'evento/:id', component: EventosComponent },
  // otras rutas...
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
