import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AddVendorService {
  private apiUrl = 'http://localhost:8080/api/addVendor';

  constructor(private http: HttpClient) {}

  addVendorFuntion(): Observable<{ vendors: any[] }> {
    return this.http.post<{ vendors: any[] }>(this.apiUrl, {});
  }
}
