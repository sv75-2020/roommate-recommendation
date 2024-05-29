import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from 'src/infrastructure/material.module';
import { SharedModule } from '../shared/shared.module';
import { EditUserComponent } from './edit-user/edit-user.component';
import { RoommatesPageComponent } from './roommates-page/roommates-page.component';



@NgModule({
  declarations: [EditUserComponent, RoommatesPageComponent],
  imports: [
    CommonModule,
    MaterialModule,
    SharedModule,
    HttpClientModule
  ]
})
export class UserModule { }
