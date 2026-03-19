import { ApplicationConfig } from '@angular/core';
import { authInterceptorProvider } from './core/interceptors/auth.interceptor';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi(), withFetch()), // withFetch() for SSR compatibility
    authInterceptorProvider
  ]
};
