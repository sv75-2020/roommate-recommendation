import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule} from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from '../infrastructure/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Interceptor } from './modules/auth/interceptor/interceptor.interceptor';
import { AppRoutingModule } from 'src/infrastructure/app-routing.module';
import { AppComponent } from './app.component';
import { DatePipe } from '@angular/common';
import { LayoutModule } from './modules/layout/layout.module';
import {AuthModule} from './modules/auth/auth.module';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { SharedModule } from './modules/shared/shared.module';
import { MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material/dialog';
import { ReasonDialogComponent } from './modules/shared/reason-dialog/reason-dialog.component';
import { OAuthModule, OAuthService } from 'angular-oauth2-oidc';
import { EditUserComponent } from './modules/user/edit-user/edit-user.component';
import { UserInfoComponent } from './modules/user/user-info/user-info.component';
import { AccommodationPageComponent } from './modules/accomodation/accommodation-page/accommodation-page.component';
import { AccommodationDetailsComponent } from './modules/accomodation/accommodation-details/accommodation-details.component';
import { RoommatesPageComponent } from './modules/user/roommates-page/roommates-page.component';
import { NotificationsComponent } from './modules/user/notifications/notifications.component';
import { NotificationItemComponent } from './modules/user/notification-item/notification-item.component';
import { AccommodationHistoryComponent } from './modules/review/accommodation-history/accommodation-history.component';
import { RateModuleComponent } from './modules/review/rate-module/rate-module.component';
import { RateDialogComponent } from './modules/review/rate-dialog/rate-dialog.component';
import { HomeComponent } from './modules/home/home.component';
import { AccommodationPreferencesComponent } from './modules/accomodation/accommodation-preferences/accommodation-preferences.component';
import { ReportsComponent } from './modules/report/reports/reports.component';


@NgModule({
  declarations: [
    AppComponent,
    EditUserComponent,
    UserInfoComponent,
    AccommodationPageComponent,
    AccommodationDetailsComponent,
    RoommatesPageComponent,
    NotificationsComponent,
    NotificationItemComponent,
    AccommodationHistoryComponent,
    RateModuleComponent,
    RateDialogComponent
    HomeComponent,
    AccommodationPreferencesComponent,
    ReportsComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    AuthModule,
    LayoutModule,
    SharedModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSortModule,
    MatTableModule,

  ],
  providers: [
    OAuthService,
    {provide: HTTP_INTERCEPTORS,
    useClass: Interceptor,
    multi: true,
    },
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    DatePipe,
    
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
