import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationServiceService {

  private BASEURL = 'http://localhost:8080/api/v1/notifications';

  constructor(private http: HttpClient) { }

  getNotifications(): Observable<string[]> {
    return this.http.get<string[]>(this.BASEURL);
  }

  getUserNotifications(userId: number): Observable<any> {
    return this.http.get(`${this.BASEURL}/${userId}`);
  }
}
