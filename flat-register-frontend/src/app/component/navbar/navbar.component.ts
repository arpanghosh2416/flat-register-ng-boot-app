import { Router } from '@angular/router';
import { Component, DoCheck, OnInit } from '@angular/core';

import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenService } from 'src/app/service/token/token.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, DoCheck {

  user: any = {
    username: '',
    password: '',
    ownerStatus: '',
    userRole: ''
  };

  isOwner: boolean = false;
  isLoggedIn: boolean = false;
  isSuperuser: boolean = false;

  constructor(
    private _router: Router,
    private _authService: AuthService,
    private _tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
  }

  ngDoCheck(): void {
    this.verifyUser();
  }

  verifyUser(): void {
    this.user = this._tokenService.fetchUser();
    this.isOwner = this._authService.isOwner();
    this.isLoggedIn = this._authService.isLoggedIn();
    this.isSuperuser = this._authService.isSuperuser();
    // console.log('[login, admin, owner]', `[${this.isLoggedIn}, ${this.isSuperuser}, ${this.isOwner}]`);
  }

  logoutOnClick(): void {
    this._authService.logout();
    this._router.navigateByUrl('/login');
  }

}
