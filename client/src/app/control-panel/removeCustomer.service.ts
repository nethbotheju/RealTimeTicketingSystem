import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RemoveCustomerService {
  private apiUrl = 'http://localhost:8080/api/removeCustomer';

  constructor(private http: HttpClient) {}

  removeCustomerFunction(id: number): Observable<{ customers: any[] }> {
    const urlWithId = `${this.apiUrl}/${id}`;
    return this.http.delete<{ customers: any[] }>(urlWithId, {});
  }
}
