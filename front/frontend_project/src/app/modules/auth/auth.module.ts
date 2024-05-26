import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MaterialModule } from '../../../infrastructure/material.module';
import { SharedModule } from '../shared/shared.module';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [
    LoginComponent,
    SignupComponent,
    
  ],
  imports: [
    CommonModule,
    MaterialModule,
    SharedModule,
    HttpClientModule
  ]
})
export class AuthModule { }
