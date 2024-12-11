import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SalesWebSocket {
  private stompClient!: Client;

  private salesDataSubject: Subject<string> = new Subject<string>();

  constructor() {}

  initialize(): void {
    if (typeof window == 'undefined') {
      return;
    }
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws', // WebSocket URL
      connectHeaders: {},
      onConnect: () => {
        console.log(
          'Connected to WebSocket server, for recieve real time sales data in sales-chart component'
        );
        // Subscribe to /topic/sales to receive real time sales
        this.stompClient.subscribe('/topic/sales', (message: IMessage) => {
          this.salesDataSubject.next(message.body); // Emit message to subscribers
        });
      },
      onStompError: (frame) => {
        console.error('Sales-panel component STOMP error:', frame);
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

  // Observable to listen for real time sales data from the server
  getSales(): Observable<string> {
    return this.salesDataSubject.asObservable();
  }
}
