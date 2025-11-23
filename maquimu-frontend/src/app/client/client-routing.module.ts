import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortalComponent } from './portal/portal.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'portal',
    pathMatch: 'full'
  },
  {
    path: 'portal',
    component: PortalComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
