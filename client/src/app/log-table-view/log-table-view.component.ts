import { Component, OnInit } from '@angular/core';
import { timestamp } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LogWebSocket } from './log.service';

@Component({
  selector: 'app-log-table-view',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log-table-view.component.html',
  styleUrl: './log-table-view.component.css',
})
export class LogTableViewComponent implements OnInit {
  logs: any[] = [];

  constructor(private logWebSocket: LogWebSocket) {}

  ngOnInit(): void {
    this.logWebSocket.connect();
    this.add();
  }

  private add(): void {
    this.logWebSocket.getLogMessages().subscribe((message) => {
      const parsedMessage = JSON.parse(message);
      this.logs.unshift({
        level: parsedMessage.level,
        message: parsedMessage.message,
        timestamp: parsedMessage.timestamp,
      });
    });
  }
}
