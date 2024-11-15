import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient!: Client;
  private messageSubject: Subject<string> = new Subject<string>();

  constructor() {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws', // WebSocket URL
      connectHeaders: {
        // Optional connection headers if needed
      },
      onConnect: () => {
        console.log('Connected to WebSocket server');

        // Subscribe to /topic/start/data to receive messages
        this.stompClient.subscribe('/topic/start/data', (message: IMessage) => {
          console.log('Update from server:', message.body);
          this.messageSubject.next(message.body); // Emit message to subscribers
        });
      },
      onStompError: (frame) => {
        console.error('STOMP error:', frame);
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket server');
      },
    });
  }

  // Method to activate the WebSocket connection
  connect(): void {
    this.stompClient.activate();
  }

  // Method to close the WebSocket connection
  close(): void {
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }

  // Observable to listen for messages from the server
  getMessages(): Observable<string> {
    return this.messageSubject.asObservable();
  }
}
