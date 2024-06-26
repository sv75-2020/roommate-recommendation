import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../app/modules/auth/login/login.component';
import { SignupComponent } from '../app/modules/auth/signup/signup.component';
import { EditUserComponent } from 'src/app/modules/user/edit-user/edit-user.component';
import { UserInfoComponent } from 'src/app/modules/user/user-info/user-info.component';
import { AccommodationPageComponent } from 'src/app/modules/accomodation/accommodation-page/accommodation-page.component';
import { AccommodationDetailsComponent } from 'src/app/modules/accomodation/accommodation-details/accommodation-details.component';
import { RoommatesPageComponent } from 'src/app/modules/user/roommates-page/roommates-page.component';
import { NotificationsComponent } from 'src/app/modules/user/notifications/notifications.component';
import { AccommodationHistoryComponent } from 'src/app/modules/review/accommodation-history/accommodation-history.component';
import { HomeComponent } from 'src/app/modules/home/home.component';
import { AccommodationPreferencesComponent } from 'src/app/modules/accomodation/accommodation-preferences/accommodation-preferences.component';
import { ReportsComponent } from 'src/app/modules/report/reports/reports.component';
import { RecommendedRoommatesComponent } from 'src/app/modules/user/recommended-roommates/recommended-roommates.component';


const routes: Routes = [
    {path: 'home', component: HomeComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'signup', component: SignupComponent},
  { path: 'editUser', component: EditUserComponent},
  { path: 'user', component: UserInfoComponent},
  { path: 'accomodation-page', component: AccommodationPageComponent},
  { path: 'accommodation-page/:id', component: AccommodationDetailsComponent},
  { path: 'roommates-page', component: RoommatesPageComponent},
  { path: 'notifications', component: NotificationsComponent},
  { path: 'accommodation-history', component: AccommodationHistoryComponent},
  { path: 'accommodationPreferences', component: AccommodationPreferencesComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'recommended', component: RecommendedRoommatesComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }