import { Component, OnInit } from '@angular/core';
import { StartService } from './start-service.service';
import { StopService } from './stop-service.service';
import { AddVendorService } from './addVendor.service';
import { AddCustomerService } from './addCustomer.service';
import { RemoveVendorService } from './removeVendor.service';
import { RemoveCustomerService } from './removeCustomer.service';
import { WebSocketService } from './web-socket.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
})
export class ControlPanelComponent implements OnInit {
  vendors: any[] = [];
  customers: any[] = [];
  isStarted: boolean = false;
  isStopped: boolean = true;

  constructor(
    private startService: StartService,
    private stopService: StopService,
    private addVendorService: AddVendorService,
    private addCustomerService: AddCustomerService,
    private removeVendorService: RemoveVendorService,
    private removeCustomerService: RemoveCustomerService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.webSocketService.connect();
    this.listenForUpdates();
  }

  // WebSocket listener to update vendors and customers dynamically
  private listenForUpdates(): void {
    this.webSocketService.getMessages().subscribe((message) => {
      console.log('Received update from server:', message);
      // Update the vendors/customers based on the message if needed
      // You can implement custom logic to update vendors/customers based on the WebSocket message
    });
  }

  // Start function
  start(): void {
    this.startService.startFuntion().subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.vendors) {
          this.vendors = response.vendors;
        }
        if (response && response.customers) {
          this.customers = response.customers;
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });

    this.isStarted = true;
    this.isStopped = false;
  }

  // Stop function
  stop(): void {
    this.stopService.stopFuntion().subscribe({
      next: (response) => {
        console.log(response);
        this.vendors = [];
        this.customers = [];
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });

    this.isStarted = false;
    this.isStopped = true;
  }

  // Add Vendor
  addv(): void {
    this.addVendorService.addVendorFuntion().subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.vendors) {
          this.vendors = response.vendors;
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  // Remove Vendor
  stopv(id: number): void {
    this.removeVendorService.removeVendorFunction(id).subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.vendors) {
          this.vendors = response.vendors;
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  // Add Customer
  addc(priority: string): void {
    const priorityNumber = parseInt(priority, 10);
    this.addCustomerService.addCustomerFunction(priorityNumber).subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.customers) {
          this.customers = response.customers;
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  // Remove Customer
  stopc(id: number): void {
    this.removeCustomerService.removeCustomerFunction(id).subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.customers) {
          this.customers = response.customers;
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  // Cleanup on component destruction
  ngOnDestroy(): void {
    this.webSocketService.close();
  }
}
