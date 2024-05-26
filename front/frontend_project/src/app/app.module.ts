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


@NgModule({
  declarations: [
    AppComponent,
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
