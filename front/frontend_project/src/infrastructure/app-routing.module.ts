import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../app/modules/auth/login/login.component';
import { SignupComponent } from '../app/modules/auth/signup/signup.component';
import { EditUserComponent } from 'src/app/modules/user/edit-user/edit-user.component';
import { UserInfoComponent } from 'src/app/modules/user/user-info/user-info.component';
import { AccommodationPageComponent } from 'src/app/modules/accomodation/accommodation-page/accommodation-page.component';
import { AccommodationDetailsComponent } from 'src/app/modules/accomodation/accommodation-details/accommodation-details.component';


const routes: Routes = [
    {path: 'home', component: LoginComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'signup', component: SignupComponent},
  { path: 'editUser', component: EditUserComponent},
  { path: 'user', component: UserInfoComponent},
  { path: 'accomodation-page', component: AccommodationPageComponent},
  { path: 'accommodation-page/:id', component: AccommodationDetailsComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }