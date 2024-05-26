import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { HomeComponent } from './home/home.component';
import { UserToolbarComponent } from './user-toolbar/user-toolbar.component';
import { AdminToolbarComponent } from './admin-toolbar/admin-toolbar.component';
import { SharedModule } from "../shared/shared.module";
import { MaterialModule } from 'src/infrastructure/material.module';



@NgModule({
    declarations: [
        ToolbarComponent,
        HomeComponent,
        UserToolbarComponent,
        AdminToolbarComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        MaterialModule
    ],
    exports: [
        ToolbarComponent,
        HomeComponent,
        UserToolbarComponent,
        AdminToolbarComponent
    ],
})
export class LayoutModule { }
