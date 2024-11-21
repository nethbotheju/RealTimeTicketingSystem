import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './configuration-form.component.html',
  styleUrl: './configuration-form.component.css',
})
export class ConfigurationFormComponent {
  numOfCustomers: number = 0;
  totalNumberOfTickets: number = 0;
  ticketReleaseRate: number = 0;
  customerRetrivalRate: number = 0;
  maximumTicketCapacity: number = 0;
  numOfVendors: number = 0;

  customers: { id: number; priority: string }[] = [];

  ngOnInit() {
    this.fetchConfigurationData();
  }

  fetchConfigurationData() {
    const apiUrl = 'http://localhost:8080/api/loadConfig';

    this.http.get(apiUrl).subscribe(
      (response: any) => {
        // Extract and store necessary values from the response
        this.maximumTicketCapacity = response.maxTicketCapacity;
        this.totalNumberOfTickets = response.totalNumberOfTickets;
        this.ticketReleaseRate = response.ticketReleaseRate;
        this.customerRetrivalRate = response.ticketRetrivalRate;
        this.numOfVendors = response.numOfVendors;
        this.numOfCustomers = response.numOfCustomers;

        // Update customers
        this.customers = response.listOfCustomers.map((customer: any) => ({
          id: customer.id,
          priority: customer.priority.toString(), // Convert to string if needed
        }));
      },
      (error) => {
        console.error('Error fetching configuration data:', error);
      }
    );
  }

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
    // Construct the JSON payload
    const payload = {
      maxTicketCapacity: this.maximumTicketCapacity,
      totalNumberOfTickets: this.totalNumberOfTickets,
      ticketRetrivalRate: this.customerRetrivalRate,
      ticketReleaseRate: this.ticketReleaseRate,
      numOfVendors: this.numOfVendors,
      numOfCustomers: this.numOfCustomers,
      Customers: this.customers.map((customer) => ({
        id: customer.id,
        priority: parseInt(customer.priority), // Convert priority back to a number
      })),
    };

    // API endpoint
    const apiUrl = 'http://localhost:8080/api/updateConfig/';

    // Send POST request
    this.http.post(apiUrl, payload, { responseType: 'text' }).subscribe(
      (response) => {
        console.log('Response received:', response);
      },
      (error) => {
        console.error('Error occurred:', error);
      }
    );
  }

  constructor(private router: Router, private http: HttpClient) {}

  navigateToHome() {
    this.router.navigate(['/']);
  }
}
