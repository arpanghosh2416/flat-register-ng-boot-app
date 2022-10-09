import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

//const DOMAIN = 'http://localhost:8888'
const DOMAIN = 'https://flatregister.herokuapp.com'

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private _baseURL = `${DOMAIN}/api/feedback`;
  private _sendFeedbackAPI = `${this._baseURL}/send-feedback`;

  constructor(
    private _httpClient: HttpClient
  ) {
  }

  sendFeedback(request: any): Observable<any> {
    return this._httpClient.post(this._sendFeedbackAPI, request);
  }

}
