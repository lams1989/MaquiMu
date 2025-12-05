import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http'; // Import withInterceptorsFromDi

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { authInterceptorProvider } from './core/interceptors/auth.interceptor'; // Import the interceptor provider

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi()), // Configure HttpClient to use DI-based interceptors
    authInterceptorProvider // Add the interceptor provider to the application providers
  ]
};
