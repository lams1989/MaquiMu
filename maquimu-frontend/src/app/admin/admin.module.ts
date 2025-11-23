import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { InventoryComponent } from './inventory/inventory.component';
import { MachineModalComponent } from './inventory/machine-modal/machine-modal.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    InventoryComponent,
    MachineModalComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    DashboardComponent,
    SidebarComponent,
    NavbarComponent
  ]
})
export class AdminModule { }
