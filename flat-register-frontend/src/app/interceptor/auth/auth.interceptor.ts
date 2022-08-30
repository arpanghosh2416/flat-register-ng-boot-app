import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from 'src/app/service/token/token.service';
import { AuthService } from 'src/app/service/auth/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private _authService: AuthService,
    private _tokenService: TokenService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const authHeaderKey = 'Authorization';
    const authHeaderValue = `Bearer ${this._tokenService.fetchToken()}`;

    if (this._authService.isLoggedIn()) {
      const modifiedRequest = request.clone({
        headers: request.headers.set(authHeaderKey, authHeaderValue)
      });
      request = modifiedRequest;
    }

    return next.handle(request);
  }

}
