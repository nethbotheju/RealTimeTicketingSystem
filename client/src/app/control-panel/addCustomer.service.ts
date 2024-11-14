import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AddCustomerService {
  private apiUrl = 'http://localhost:8080/api/addCustomer';

  constructor(private http: HttpClient) {}

  addCustomerFunction(priority: number): Observable<{ customers: any[] }> {
    // Append the priority to the URL path
    const urlWithPriority = `${this.apiUrl}/${priority}`;
    return this.http.post<{ customers: any[] }>(urlWithPriority, {});
  }
}
