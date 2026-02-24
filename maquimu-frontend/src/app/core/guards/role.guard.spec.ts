import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { RoleGuard } from './role.guard';
import { TestBed } from '@angular/core/testing';

describe('RoleGuard', () => {
  let guard: RoleGuard;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;
  let routeSnapshot: ActivatedRouteSnapshot;
  let stateSnapshot: RouterStateSnapshot;

  beforeEach(() => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['getUserRole']);
    const routerSpy = jasmine.createSpyObj('Router', ['createUrlTree']);

    TestBed.configureTestingModule({
      providers: [
        RoleGuard,
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    guard = TestBed.inject(RoleGuard);
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    routeSnapshot = {
      data: { roles: ['OPERARIO'] }
    } as any;
    stateSnapshot = { url: '/admin/dashboard' } as RouterStateSnapshot;
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access if user has the required role', () => {
    authService.getUserRole.and.returnValue('OPERARIO');

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    expect(result).toBe(true);
    expect(authService.getUserRole).toHaveBeenCalled();
  });

  it('should allow access if user role is in the list of expected roles', () => {
    routeSnapshot.data['roles'] = ['OPERARIO', 'CLIENTE'];
    authService.getUserRole.and.returnValue('CLIENTE');

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    expect(result).toBe(true);
  });

  it('should redirect to forbidden if user role does not match', () => {
    authService.getUserRole.and.returnValue('CLIENTE');
    const mockUrlTree = {} as UrlTree;
    router.createUrlTree.and.returnValue(mockUrlTree);

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    expect(result).toBe(mockUrlTree);
    expect(router.createUrlTree).toHaveBeenCalledWith(['/forbidden']);
  });

  it('should redirect to login if user is not authenticated (null role)', () => {
    authService.getUserRole.and.returnValue(null);
    const mockUrlTree = {} as UrlTree;
    router.createUrlTree.and.returnValue(mockUrlTree);

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    expect(result).toBe(mockUrlTree);
    expect(router.createUrlTree).toHaveBeenCalledWith(
      ['/auth/login'],
      { queryParams: { returnUrl: '/admin/dashboard' } }
    );
  });

  it('should handle routes without role requirements', () => {
    routeSnapshot.data['roles'] = [];
    authService.getUserRole.and.returnValue('OPERARIO');
    const mockUrlTree = {} as UrlTree;
    router.createUrlTree.and.returnValue(mockUrlTree);

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    // Empty array: includes() always returns false → redirect to forbidden
    expect(result).toBe(mockUrlTree);
    expect(router.createUrlTree).toHaveBeenCalledWith(['/forbidden']);
  });

  it('should be case-sensitive with roles', () => {
    routeSnapshot.data['roles'] = ['operario']; // lowercase
    authService.getUserRole.and.returnValue('OPERARIO'); // uppercase
    const mockUrlTree = {} as UrlTree;
    router.createUrlTree.and.returnValue(mockUrlTree);

    const result = guard.canActivate(routeSnapshot, stateSnapshot);

    expect(result).toBe(mockUrlTree);
    expect(router.createUrlTree).toHaveBeenCalledWith(['/forbidden']);
  });
});
