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
import { Console, error } from 'console';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
})
export class ControlPanelComponent implements OnInit {
  // Passing values to parent component (home component)
  @Output('startClicked') public startClicked = new EventEmitter();
  @Output('stopClicked') public stopClicked = new EventEmitter();

  vendors: any[] = [];
  customers: any[] = [];

  isStarted: boolean = false;
  isStopped: boolean = true;
  isResetted: boolean = false;

  constructor(
    private startService: StartService,
    private stopService: StopService,
    private resetService: ResetService,
    private addVendorService: AddVendorService,
    private addCustomerService: AddCustomerService,
    private removeVendorService: RemoveVendorService,
    private removeCustomerService: RemoveCustomerService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.webSocketService.initialize();
    this.webSocketService.connect();
    this.listenForUpdates();
  }

  // WebSocket listener to update vendors and customers dynamically
  private listenForUpdates(): void {
    this.webSocketService.getStartData().subscribe((message) => {
      // Parse the message
      const parsedMessage = JSON.parse(message);

      // Extract customers and vendors arrays
      this.customers = parsedMessage.customers;
      this.vendors = parsedMessage.vendors;

      this.isStarted = true;
      this.isStopped = false;
      this.isResetted = true;

      this.startClicked.next(true);
    });

    this.webSocketService.getStopData().subscribe((message) => {
      this.vendors = [];
      this.customers = [];

      this.isStarted = false;
      this.isStopped = true;
      this.isResetted = false;

      this.stopClicked.next(true);
    });
  }

  // Start function
  start(): void {
    this.startService.startFuntion().subscribe(
      (response) => {
        if (response == true) {
          console.log(
            'Control-panel-component start-service: successfully started the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel start-service error: ', error);
        }
      }
    );
  }

  // Stop function
  stop(): void {
    this.stopService.stopFuntion().subscribe(
      (response) => {
        if (response == true) {
          console.log(
            'Control-panel-component stop-service: successfully stoped the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel stop-service error: ', error);
        }
      }
    );
  }

  reset(): void {
    this.resetService.resetFuntion().subscribe(
      (response) => {
        if (response) {
          this.isStarted = false;
          this.isStopped = true;
          this.isResetted = false;

          console.log(
            'Control-panel-component reset-service: successfully resetted the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel reset-service error: ', error);
        }
      }
    );
  }

  // Add Vendor
  addv(): void {
    this.addVendorService.addVendorFuntion().subscribe(
      (response) => {
        if (response && response.vendors) {
          this.vendors = response.vendors;
          console.log(
            'Control-panel-component add-vendor-service: successfully added a vendor in the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel add-vendor-service error: ', error);
        }
      }
    );
  }

  // Remove Vendor
  stopv(id: number): void {
    this.removeVendorService.removeVendorFunction(id).subscribe(
      (response) => {
        if (response && response.vendors) {
          this.vendors = response.vendors;
          console.log(
            'Control-panel-component remove-vendor-service: successfully remove the vendor from the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel remove-vendor-service error: ', error);
        }
      }
    );
  }

  // Add Customer
  addc(priority: string): void {
    const priorityNumber = parseInt(priority, 10);
    this.addCustomerService.addCustomerFunction(priorityNumber).subscribe(
      (response) => {
        if (response && response.customers) {
          this.customers = response.customers;
          console.log(
            'Control-panel-component add-customer-service: successfully added a customer in the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel add-customer-service error: ', error);
        }
      }
    );
  }

  // Remove Customer
  stopc(id: number): void {
    this.removeCustomerService.removeCustomerFunction(id).subscribe(
      (response) => {
        if (response && response.customers) {
          this.customers = response.customers;
          console.log(
            'Control-panel-component remove-customer-service: successfully remove the customer from the program.'
          );
        }
      },
      (error) => {
        if (error.status == 0) {
          alert(
            'Unable to connect to the backend server. Please ensure the backend is running and try again.'
          );
        } else {
          alert('An unexpected error occurred. Please try again later.');
          console.log('control-panel remove-customer-service error: ', error);
        }
      }
    );
  }

  // Cleanup on component destruction
  ngOnDestroy(): void {
    this.webSocketService.close();
  }
}
