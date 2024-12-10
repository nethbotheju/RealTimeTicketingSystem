import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private stompClient!: Client;

  private startDataSubject: Subject<string> = new Subject<string>();
  private stopDataSubject: Subject<string> = new Subject<string>();

  constructor() {
    this.stompClient = new Client({
      // WebSocket URL
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {},
      onConnect: () => {
        console.log(
          'Connected to WebSocket server, for recieve start/stop data in control-panel component'
        );

        // Subscribe to /topic/start/data to receive start data
        this.stompClient.subscribe('/topic/start/data', (message: IMessage) => {
          this.startDataSubject.next(message.body); // Emit message to subscribers
        });

        // Subscribe to /topic/stop/data to receive stop data
        this.stompClient.subscribe('/topic/stop/data', (message: IMessage) => {
          this.stopDataSubject.next(message.body); // Emit message to subscribers
        });
      },
      onStompError: (frame) => {
        console.error('Control-panel component STOMP error:', frame);
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

  // Observable to listen for start data from the server
  getStartData(): Observable<string> {
    return this.startDataSubject.asObservable();
  }

  // Observable to listen for stop data from the server
  getStopData(): Observable<string> {
    return this.stopDataSubject.asObservable();
  }
}
