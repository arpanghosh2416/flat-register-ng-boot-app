import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenService } from 'src/app/service/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginform: FormGroup= new FormGroup({
    username:new FormControl('',[Validators.required]),
    password: new FormControl('',[Validators.required])
  });

  showMessage: boolean = false;
  showWrongMessage: boolean = false;
  errorMessage: string | null = null;
  showSuccessMessage: boolean = false;

  constructor(
    private _router: Router,
    private _authService: AuthService,
    private _tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
  }

  closeMessage(): void {
    this.showMessage = false;
  }

  closeErrorMessage(): void {
    this.errorMessage = null;
  }

  closeWrongMessage(): void {
    this.showWrongMessage = false;
  }

  closeSuccessMessage(): void {
    this.showSuccessMessage = false;
  }

  userLogin(): void {
    let request = this.loginform.value;
    console.log('login works:', request);

    this._authService.login(request).subscribe(
      response => {
        console.log('response', response);
        this.errorMessage = null;
        this._tokenService.storeUser(response.user);
        this._tokenService.storeToken(response.jwt);
        this._router.navigateByUrl('/home');
      },
      error => {
        console.log('error', error);
        if (error.error === 'Bad Credential')
          this.errorMessage = `Wrong Username or Password`;
        else
          this.errorMessage = `Can't login ${this.loginform.value.username}, Something went wrong in our server`;
      }
    );
  }

  get username(): FormControl {
    return this.loginform.get('username') as FormControl;
  }

  get password(): FormControl {
    return this.loginform.get('password') as FormControl;
  }

}