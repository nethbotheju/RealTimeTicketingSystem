import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StopService {
  private apiUrl = 'http://localhost:8080/api/stop';

  constructor(private http: HttpClient) {}

  stopFuntion(): Observable<boolean> {
    return this.http.get<boolean>(this.apiUrl);
  }
}
