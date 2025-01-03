import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TicketAvailWebsocket {
  private stompClient!: Client;
  private MessageSubject: Subject<string> = new Subject<string>();

  constructor() {}

  initialize(): void {
    if (typeof window == 'undefined') {
      return;
    }
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws', // WebSocket URL
      connectHeaders: {
        // Optional connection headers if needed
      },
      onConnect: () => {
        console.log(
          'Connected to WebSocket server, for recieve real time ticket data in ticket-details component'
        );

        // Subscribe to /topic/start/data to receive messages
        this.stompClient.subscribe(
          '/topic/ticketAvail',
          (message: IMessage) => {
            this.MessageSubject.next(message.body); // Emit message to subscribers
          }
        );
      },
      onStompError: (frame) => {
        console.error('Ticket-details component STOMP error:', frame);
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket server');
      },
    });
  }

  // Method to activate the WebSocket connection
  connect(): void {
    if (typeof window == 'undefined') {
      return;
    }
    this.stompClient.activate();
  }

  // Method to close the WebSocket connection
  close(): void {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }

  // Observable to listen for messages from the server
  getTicketAvail(): Observable<string> {
    return this.MessageSubject.asObservable();
  }
}
