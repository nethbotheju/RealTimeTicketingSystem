import { Component } from '@angular/core';
import { timestamp } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-log-table-view',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log-table-view.component.html',
  styleUrl: './log-table-view.component.css',
})
export class LogTableViewComponent {
  logs = [
    {
      level: 'Error',
      message: 'Failed to load resource',
      timestamp: '2024-12-03 10:15:30',
    },
    {
      level: 'Warning',
      message: 'Deprecated API usage',
      timestamp: '2024-12-03 11:20:45',
    },
    {
      level: 'Success',
      message: 'User successfully logged in',
      timestamp: '2024-12-03 12:30:00',
    },
    {
      level: 'Error',
      message: 'Unhandled exception occurred',
      timestamp: '2024-12-03 13:45:15',
    },
    {
      level: 'Warning',
      message: 'Low disk space',
      timestamp: '2024-12-03 14:50:20',
    },
    {
      level: 'Success',
      message: 'Data saved successfully',
      timestamp: '2024-12-03 15:55:25',
    },
    {
      level: 'Success',
      message: 'User successfully logged in',
      timestamp: '2024-12-03 12:30:00',
    },
    {
      level: 'Error',
      message: 'Unhandled exception occurred',
      timestamp: '2024-12-03 13:45:15',
    },
    {
      level: 'Warning',
      message: 'Low disk space',
      timestamp: '2024-12-03 14:50:20',
    },
    {
      level: 'Success',
      message: 'Data saved successfully',
      timestamp: '2024-12-03 15:55:25',
    },
  ];

  add() {
    // unshift will add to beginning of the array
    this.logs.unshift({
      level: 'Success',
      message: 'First added',
      timestamp: '2024-12-03 15:55:25',
    });
  }
}
