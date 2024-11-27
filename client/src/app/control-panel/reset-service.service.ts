import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ResetService {
  private apiUrl = 'http://localhost:8080/api/reset';

  constructor(private http: HttpClient) {}

  resetFuntion(): Observable<boolean> {
    return this.http.get<boolean>(this.apiUrl);
  }
}
