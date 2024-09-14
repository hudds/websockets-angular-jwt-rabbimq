import { inject, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/login/pages/login/login.component';
import { RegistrationComponent } from './features/registration/pages/registration/registration.component';
import { DashboardComponent } from './features/dashboard/pages/dashboard/dashboard.component';
import { AuthGuard } from './features/security/guard/auth-guard.guard';

const routes: Routes = [
  
    {path: 'login', component: LoginComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: 'cadastro', component: RegistrationComponent},
    {
      path: 'dashboard', 
      component: DashboardComponent,
      canActivate:[AuthGuard]
    },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
