import { Component, OnInit } from '@angular/core';
import { TicketAvailWebsocket } from './ticket-details.service';

@Component({
  selector: 'app-ticket-details',
  standalone: true,
  imports: [],
  templateUrl: './ticket-details.component.html',
  styleUrl: './ticket-details.component.css',
})
export class TicketDetailsComponent implements OnInit {
  noOfTickets = 0;

  constructor(private ticketAvailWebSocket: TicketAvailWebsocket) {}

  ngOnInit(): void {
    this.ticketAvailWebSocket.initialize();
    this.ticketAvailWebSocket.connect();
    this.update();
  }

  update(): void {
    this.ticketAvailWebSocket.getTicketAvail().subscribe((message) => {
      const num = JSON.parse(message);
      this.noOfTickets = num;
    });
  }

  clear(): void {
    this.noOfTickets = 0;
  }

  ngOnDestroy(): void {
    this.ticketAvailWebSocket.close();
  }
}
