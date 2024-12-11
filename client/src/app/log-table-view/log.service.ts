import { Injectable, NgZone } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LogWebSocket {
  private stompClient!: Client;
  private MessageSubject: Subject<string> = new Subject<string>();

  constructor(private zone: NgZone) {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws', // WebSocket URL
      connectHeaders: {},
      onConnect: () => {
        console.log(
          'Connected to WebSocket server, for recieve logg data in log-table-view component'
        );

        this.zone.runOutsideAngular(() => {
          // Subscribe to /topic/log to receive loggs
          this.stompClient.subscribe('/topic/log', (message: IMessage) => {
            this.zone.run(() => {
              this.MessageSubject.next(message.body);
            });
          });
        });
      },
      onStompError: (frame) => {
        console.error('Log-table-view component STOMP error:', frame);
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
  getLogMessages(): Observable<string> {
    return this.MessageSubject.asObservable();
  }
}
