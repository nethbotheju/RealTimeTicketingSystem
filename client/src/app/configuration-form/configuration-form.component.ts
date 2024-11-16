import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css',
})
export class ConfigurationFormComponent {
  constructor(private router: Router) {}

  navigateToHome() {
    this.router.navigate(['/']);
  }
}
