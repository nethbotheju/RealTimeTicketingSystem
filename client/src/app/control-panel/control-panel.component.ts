import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { StartService } from './start-service.service';
import { StopService } from './stop-service.service';
import { AddVendorService } from './addVendor.service';
import { AddCustomerService } from './addCustomer.service';
import { RemoveVendorService } from './removeVendor.service';
import { RemoveCustomerService } from './removeCustomer.service';
import { WebSocketService } from './web-socket.service';
import { CommonModule } from '@angular/common';
import { ResetService } from './reset-service.service';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
})
export class ControlPanelComponent implements OnInit {
  @Output('stopClicked') public stopClicked = new EventEmitter();

  vendors: any[] = [];
  customers: any[] = [];
  isStarted: boolean = false;
  isStopped: boolean = true;
  isResetted: boolean = false;

  constructor(
    private startService: StartService,
    private stopService: StopService,
    private addVendorService: AddVendorService,
    private addCustomerService: AddCustomerService,
    private removeVendorService: RemoveVendorService,
    private removeCustomerService: RemoveCustomerService,
    private webSocketService: WebSocketService,
    private resetService: ResetService
  ) {}

  ngOnInit(): void {
    this.webSocketService.connect();
    this.listenForUpdates();
  }

  // WebSocket listener to update vendors and customers dynamically
  private listenForUpdates(): void {
    this.webSocketService.getStartMessages().subscribe((message) => {
      console.log('Received update from server:', message);
      // Parse the message
      const parsedMessage = JSON.parse(message);

      // Extract customers and vendors arrays
      this.customers = parsedMessage.customers;
      this.vendors = parsedMessage.vendors;

      this.isStarted = true;
      this.isStopped = false;
      this.isResetted = true;
    });

    this.webSocketService.getStopMessages().subscribe((message) => {
      console.log('Recive message from stop: ' + message);
      this.vendors = [];
      this.customers = [];
      this.isStarted = false;
      this.isStopped = true;
      this.stopClicked.next(true);
      this.isResetted = false;
    });
  }

  // Start function
  start(): void {
    this.startService.startFuntion().subscribe((response) => {
      console.log(response);
    });
  }

  // Stop function
  stop(): void {
    this.stopService.stopFuntion().subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
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

  reset(): void {
    this.resetService.resetFuntion().subscribe({
      next: (response) => {
        console.log(response);
        if (response) {
          this.isStarted = false;
          this.isStopped = true;
          this.isResetted = false;
        }
      },
    });
  }
}
