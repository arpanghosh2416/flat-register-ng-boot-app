import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

//const DOMAIN = 'http://localhost:8888'
const DOMAIN = 'https://flatregister.herokuapp.com'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _baseURL = `${DOMAIN}/api/user/auth`;
  private _loginAPI = `${this._baseURL}/login`;

  constructor(
    private _httpClient: HttpClient,
    private _tokenService: TokenService
  ) {
  }

  login(request: any): Observable<any> {
    return this._httpClient.post<any>(this._loginAPI, request);
  }

  logout(): void {
    this._tokenService.removeUser();
    this._tokenService.removeOwner();
    this._tokenService.removeToken();
  }

  isOwner(): boolean {
    return (this._tokenService.isUserOwner()) ? true : false;
  }

  isLoggedIn(): boolean {
    return (this._tokenService.isTokenExist()) ? true : false;
  }

  isSuperuser(): boolean {
    if (this._tokenService.isUserExist()) {
      let userRole = this._tokenService.fetchUser().userRole;
      return (userRole === 'ROLE_ADMIN') ? true : false;
    } else return false;
  }

}
