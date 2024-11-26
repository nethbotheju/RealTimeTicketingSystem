import { Component } from '@angular/core';
import { TicketDetailsComponent } from '../ticket-details/ticket-details.component';
import { ControlPanelComponent } from '../control-panel/control-panel.component';
import { LogTableViewComponent } from '../log-table-view/log-table-view.component';
import { SalesChartComponent } from '../sales-chart/sales-chart.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    ControlPanelComponent,
    LogTableViewComponent,
    TicketDetailsComponent,
    SalesChartComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  constructor(private router: Router) {}

  natigateToCongitForm() {
    this.router.navigate(['/configuration-form']);
  }
}
