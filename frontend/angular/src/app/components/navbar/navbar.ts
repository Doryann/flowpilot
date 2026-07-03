import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import {TranslatePipe} from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, TranslatePipe],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
  standalone: true
})
export class Navbar {
  menuOpen = signal(false);
  stackMenuOpen = signal(false);

  toggleStackMenu(): void {
    this.stackMenuOpen.update((v) => !v);
  }

  toggleMenu(): void {
    this.menuOpen.update((value) => !value);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }
}
