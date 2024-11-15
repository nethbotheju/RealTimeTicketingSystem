import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StartService {
  private apiUrl = 'http://localhost:8080/api/start';

  constructor(private http: HttpClient) {}

  startFuntion(): Observable<boolean> {
    return this.http.get<boolean>(this.apiUrl);
  }
}
