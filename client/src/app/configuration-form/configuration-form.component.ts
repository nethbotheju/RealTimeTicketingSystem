import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css',
})
export class ConfigurationFormComponent {
  numOfCustomers: number = 10; // Default value
  customers: { id: number; priority: string }[] = [];

  updateCustomers() {
    const newCustomers = [];
    for (let i = 1; i <= this.numOfCustomers; i++) {
      const existing = this.customers.find((cust) => cust.id === i);
      newCustomers.push({
        id: i,
        priority: '3',
      });
    }
    this.customers = newCustomers;
  }

  // Submit data
  submit() {
    console.log(this.customers);
  }

  constructor(private router: Router) {}

  navigateToHome() {
    this.router.navigate(['/']);
  }
}
