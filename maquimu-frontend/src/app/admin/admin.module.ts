import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import { NavbarComponent } from '../shared/navbar/navbar.component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdminRoutingModule,
    DashboardComponent,
    SidebarComponent,
    NavbarComponent
  ]
})
export class AdminModule { }
