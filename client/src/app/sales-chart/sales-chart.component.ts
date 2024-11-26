import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { SalesWebSocket } from './sales.service';
Chart.register(...registerables);

@Component({
  selector: 'app-sales-chart',
  standalone: true,
  imports: [],
  templateUrl: './sales-chart.component.html',
  styleUrls: ['./sales-chart.component.css'],
})
export class SalesChartComponent implements OnInit {
  config: any = {
    type: 'bar',
    data: {
      labels: [],
      datasets: [
        {
          label: 'Sales',
          data: [],
          barThickness: 40,
        },
      ],
    },
    options: {
      maintainAspectRatio: false,
      scales: {
        x: {},
        y: {
          beginAtZero: true,
        },
      },
      plugins: {
        legend: {
          display: false, // Hide the legend
        },
        title: {
          display: true, // Display the title
          text: 'Ticket Sales Chart',
          align: 'start',
          font: {
            size: 24,
          },
          padding: {
            bottom: 20,
          },
        },
      },
    },
  };

  constructor(private salesWebSocket: SalesWebSocket) {}
  chart: any;
  ngOnInit(): void {
    this.chart = new Chart('TicketSales', this.config);
    this.salesWebSocket.connect();
    this.add();
  }

  private add(): void {
    this.salesWebSocket.getSales().subscribe((message) => {
      console.log('Received update from server:', message);

      const sale = JSON.parse(message);
      const dateIndex = this.chart.data.labels?.indexOf(sale.date) ?? -1;

      if (dateIndex === -1) {
        // New date, add to labels and data
        this.chart.data.labels.push(sale.date);
        this.chart.data.datasets[0].data.push(sale.count);
      } else {
        // Existing date, update the count
        this.chart.data.datasets[0].data[dateIndex] += sale.count;
      }

      // Trigger chart update
      this.chart.update();
    });
  }
}
