import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/auth/login',
        pathMatch: 'full'
    },
    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
    },
    {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
    },
    {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule)
    },
    {
        path: '**',
        redirectTo: '/auth/login'
    }
];
