import { ClientLayoutComponent } from './layout/client-layout.component';
import { MyInvoicesComponent } from './my-invoices/my-invoices.component';
import { MyRentalsComponent } from './my-rentals/my-rentals.component';
import { NgModule } from '@angular/core';
import { PortalComponent } from './portal/portal.component';
import { RequestRentalComponent } from './request-rental/request-rental.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: ClientLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'portal',
        pathMatch: 'full'
      },
      {
        path: 'portal',
        component: PortalComponent
      },
      {
        path: 'request-rental',
        component: RequestRentalComponent
      },
      {
        path: 'my-rentals',
        component: MyRentalsComponent
      },
      {
        path: 'my-invoices',
        component: MyInvoicesComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
