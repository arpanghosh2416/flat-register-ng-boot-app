import { Router } from '@angular/router';
import { Component, DoCheck, OnInit } from '@angular/core';

import { AuthService } from 'src/app/service/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, DoCheck {

  isOwner: boolean = false;
  isLoggedIn: boolean = false;
  isSuperuser: boolean = false;

  constructor(
    private _router: Router,
    private _authService: AuthService
  ) {
  }

  ngOnInit(): void {
  }

  ngDoCheck(): void {
    this.verifyUser();
  }

  verifyUser(): void {
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
