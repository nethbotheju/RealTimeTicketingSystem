import { Component } from '@angular/core';
import { StartService } from './start-service.service';
import { StopService } from './stop-service.service';
import { AddVendorService } from './addVendor.service';
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
    console.log('Vendor ' + id + ' removed successfully');
  }

  stopc(id: number) {
    console.log('Customer ' + id);
  }

  addc(priority: string) {
    console.log('Customer added. Priority: ' + priority);
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
    private addVendorService: AddVendorService
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
