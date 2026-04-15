import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SensitiveDataService {
  private _visible = true;

  get visible(): boolean {
    return this._visible;
  }

  toggle(): void {
    this._visible = !this._visible;
  }
}
