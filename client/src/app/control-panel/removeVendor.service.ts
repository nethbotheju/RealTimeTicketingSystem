import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RemoveVendorService {
  private apiUrl = 'http://localhost:8080/api/removeVendor';

  constructor(private http: HttpClient) {}

  removeVendorFunction(id: number): Observable<{ vendors: any[] }> {
    const urlWithId = `${this.apiUrl}/${id}`;
    return this.http.delete<{ vendors: any[] }>(urlWithId, {});
  }
}
