// src/app/shared/forbidden/forbidden.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-forbidden',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="forbidden-container">
      <h1>403 Forbidden</h1>
      <p>No tienes permiso para acceder a esta página.</p>
      <a routerLink="/home">Volver al Inicio</a>
    </div>
  `,
  styles: [`
    .forbidden-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      text-align: center;
      background-color: #f8d7da; /* Light red background */
      color: #721c24; /* Dark red text */
      font-family: Arial, sans-serif;
    }
    h1 {
      font-size: 3em;
      margin-bottom: 0.5em;
    }
    p {
      font-size: 1.2em;
      margin-bottom: 1em;
    }
    a {
      color: #004085; /* Blue link */
      text-decoration: none;
      font-weight: bold;
    }
    a:hover {
      text-decoration: underline;
    }
  `]
})
export class ForbiddenComponent { }
