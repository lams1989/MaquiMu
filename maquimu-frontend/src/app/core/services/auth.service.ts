import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  /**
   * Login stub - to be implemented
   */
  login(email: string, password: string): Observable<any> {
    // TODO: Implement actual authentication logic
    console.log('AuthService.login() - Stub method called');
    return of({ token: 'fake-jwt-token', user: { email } });
  }

  /**
   * Logout stub - to be implemented
   */
  logout(): void {
    // TODO: Implement actual logout logic
    console.log('AuthService.logout() - Stub method called');
  }

  /**
   * Check if user is authenticated - stub
   */
  isAuthenticated(): boolean {
    // TODO: Implement actual authentication check
    return false;
  }
}
