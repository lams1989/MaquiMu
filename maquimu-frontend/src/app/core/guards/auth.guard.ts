// src/app/core/guards/auth.guard.ts
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private isBrowser: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    // During SSR, allow access (client will re-check)
    if (!this.isBrowser) {
      return true;
    }

    if (this.authService.isAuthenticated()) {
      return true;
    } else {
      // User is not authenticated, redirect to login page with the return URL
      return this.router.createUrlTree(['/auth/login'], { queryParams: { returnUrl: state.url } });
    }
  }
}