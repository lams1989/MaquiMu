import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { ForbiddenComponent } from './shared/forbidden/forbidden.component'; // Import the ForbiddenComponent

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
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
        canActivate: [AuthGuard, RoleGuard],
        data: { roles: ['OPERARIO'] } // Only OPERARIO can access admin routes
    },
    {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
        canActivate: [AuthGuard, RoleGuard],
        data: { roles: ['CLIENTE'] } // Only CLIENTE can access client routes
    },
    {
        path: 'forbidden', // New route for forbidden access
        component: ForbiddenComponent
    },
    {
        path: '**',
        redirectTo: '/auth/login'
    }
];

