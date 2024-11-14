import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ControlPanelComponent } from './control-panel/control-panel.component';
import { LogTableViewComponent } from './log-table-view/log-table-view.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ControlPanelComponent, LogTableViewComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'Frontend';
}
