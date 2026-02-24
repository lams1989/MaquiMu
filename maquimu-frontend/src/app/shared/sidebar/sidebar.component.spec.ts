import { AuthService } from '../../core/services/auth/auth.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { SidebarComponent } from './sidebar.component';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['logout']);

    await TestBed.configureTestingModule({
      imports: [SidebarComponent, RouterTestingModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('rendering', () => {
    it('should render sidebar-pro element', () => {
      const sidebar = fixture.nativeElement.querySelector('.sidebar-pro');
      expect(sidebar).toBeTruthy();
    });

    it('should render sidebar-menu', () => {
      const menu = fixture.nativeElement.querySelector('.sidebar-menu');
      expect(menu).toBeTruthy();
    });

    it('should render all 5 navigation links', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      expect(links.length).toBe(5);
    });

    it('should render footer with version', () => {
      const footer = fixture.nativeElement.querySelector('.sidebar-footer');
      expect(footer).toBeTruthy();
      expect(footer.textContent).toContain('MaquiMu');
    });
  });

  describe('section labels', () => {
    it('should render 3 section labels', () => {
      const labels = fixture.nativeElement.querySelectorAll('.sidebar-section-label');
      expect(labels.length).toBe(3);
    });

    it('should have "Principal" as first section', () => {
      const labels = fixture.nativeElement.querySelectorAll('.sidebar-section-label');
      expect(labels[0].textContent.trim()).toBe('Principal');
    });

    it('should have "Gestión" as second section', () => {
      const labels = fixture.nativeElement.querySelectorAll('.sidebar-section-label');
      expect(labels[1].textContent.trim()).toBe('Gestión');
    });

    it('should have "Finanzas" as third section', () => {
      const labels = fixture.nativeElement.querySelectorAll('.sidebar-section-label');
      expect(labels[2].textContent.trim()).toBe('Finanzas');
    });
  });

  describe('navigation links', () => {
    it('should have Dashboard link', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      const dashLink = Array.from(links).find((l: any) => l.textContent.includes('Dashboard'));
      expect(dashLink).toBeTruthy();
    });

    it('should have Maquinaria link', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      const link = Array.from(links).find((l: any) => l.textContent.includes('Maquinaria'));
      expect(link).toBeTruthy();
    });

    it('should have Clientes link', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      const link = Array.from(links).find((l: any) => l.textContent.includes('Clientes'));
      expect(link).toBeTruthy();
    });

    it('should have Alquileres link', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      const link = Array.from(links).find((l: any) => l.textContent.includes('Alquileres'));
      expect(link).toBeTruthy();
    });

    it('should have Finanzas link', () => {
      const links = fixture.nativeElement.querySelectorAll('.sidebar-menu a');
      const link = Array.from(links).find((l: any) => l.textContent.includes('Finanzas'));
      expect(link).toBeTruthy();
    });
  });

  describe('icon wrappers', () => {
    it('should render colored icon wrappers for each link', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons.length).toBe(5);
    });

    it('should have blue icon for Dashboard', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons[0].classList.contains('si-blue')).toBeTrue();
    });

    it('should have emerald icon for Maquinaria', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons[1].classList.contains('si-emerald')).toBeTrue();
    });

    it('should have violet icon for Clientes', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons[2].classList.contains('si-violet')).toBeTrue();
    });

    it('should have amber icon for Alquileres', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons[3].classList.contains('si-amber')).toBeTrue();
    });

    it('should have rose icon for Finanzas', () => {
      const icons = fixture.nativeElement.querySelectorAll('.sidebar-icon');
      expect(icons[4].classList.contains('si-rose')).toBeTrue();
    });
  });
});
