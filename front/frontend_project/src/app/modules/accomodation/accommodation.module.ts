import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccommodationDetailsComponent } from './accommodation-details/accommodation-details.component';
import { MaterialModule } from 'src/infrastructure/material.module';



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class AccommodationModule { }
