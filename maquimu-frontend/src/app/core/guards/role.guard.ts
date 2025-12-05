// src/app/core/guards/role.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    const expectedRoles = route.data['roles'] as Array<string>;
    const userRole = this.authService.getUserRole();

    if (!userRole) {
      // Not authenticated, redirect to login
      return this.router.createUrlTree(['/auth/login'], { queryParams: { returnUrl: state.url } });
    }

    if (expectedRoles && expectedRoles.includes(userRole)) {
      return true;
    } else {
      // Role not authorized, redirect to a forbidden page or home
      return this.router.createUrlTree(['/forbidden']); // Or '/home'
    }
  }
}