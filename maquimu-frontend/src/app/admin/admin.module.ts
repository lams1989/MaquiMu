import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { InventoryComponent } from './inventory/inventory/inventory.component'; // Updated path
import { MachineModalComponent } from './inventory/machine-modal/machine-modal.component'; // Updated path
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    // Remove standalone components from declarations
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    DashboardComponent,
    SidebarComponent,
    NavbarComponent
    // Removed InventoryComponent and MachineModalComponent as they are standalone and imported elsewhere
  ]
})
export class AdminModule { }
