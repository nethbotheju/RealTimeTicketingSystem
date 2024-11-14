import { Component } from '@angular/core';
import { StartService } from './start-service.service';
import { StopService } from './stop-service.service';
import { AddVendorService } from './addVendor.service';
import { AddCustomerService } from './addCustomer.service';
import { RemoveVendorService } from './removeVendor.service';
import { map } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control-panel.component.html',
  styleUrl: './control-panel.component.css',
})
export class ControlPanelComponent {
  vendors: any[] = [];
  customers: any[] = [];
  isStartted: boolean = false;
  isStopped: boolean = true;

  stopv(id: number) {
    this.removeVendorService.removeVendorFunction(id).subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.vendors) {
          this.vendors = response.vendors;
        } else {
          console.error('Invalid response:', response);
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  stopc(id: number) {
    console.log('Customer ' + id);
  }

  addc(priority: string) {
    // Convert priority to a number before passing it to the service function
    const priorityNumber = parseInt(priority, 10);

    this.addCustomerService.addCustomerFunction(priorityNumber).subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.customers) {
          this.customers = response.customers;
        } else {
          console.error('Invalid response:', response);
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  addv() {
    this.addVendorService.addVendorFuntion().subscribe({
      next: (response) => {
        console.log(response);
        if (response && response.vendors) {
          this.vendors = response.vendors;
        } else {
          console.error('Invalid response:', response);
        }
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }

  constructor(
    private startService: StartService,
    private stopService: StopService,
    private addVendorService: AddVendorService,
    private addCustomerService: AddCustomerService,
    private removeVendorService: RemoveVendorService
  ) {}

  start() {
    this.startService
      .startFuntion()
      .pipe(
        map((response) => response) // No need to parse the response
      )
      .subscribe((response) => {
        console.log(response);
        this.vendors = response.vendors;
        this.customers = response.customers;
      });
    this.isStartted = true;
    this.isStopped = false;
  }

  stop() {
    this.stopService.stopFuntion().subscribe((response) => {
      console.log(response);
      this.vendors = [];
      this.customers = [];
    });

    this.isStartted = false;
    this.isStopped = true;
  }
}
