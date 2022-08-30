import { Component, DoCheck, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/service/auth/auth.service';
import { FlatService } from 'src/app/service/flat/flat.service';
import { OwnerService } from 'src/app/service/owner/owner.service';
import { TokenService } from 'src/app/service/token/token.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, DoCheck {

  flats: any = [];
  owner: any = {
  };

  isOwner: boolean = false;
  isLoggedIn: boolean = false;
  isSuperuser: boolean = false;

  constructor(
    private _router: Router,
    private _authService: AuthService,
    private _flatService: FlatService,
    private _ownerService: OwnerService,
    private _tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
    this.fetchAllFlats();
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

  fetchAllFlats(): void {
    this._flatService.getAllFlats().subscribe(
      response => {
        console.log('response', response);
        this.flats = response;
        this.flats.splice(0, 1);
      },
      error => {
        console.log('error', error);
      }
    );
  }

  registerFlat(flatId: number): void {
    let ownerId = this._tokenService.fetchUser().ownerStatus;
    this._flatService.getFlatById(flatId).subscribe(
      flat => {
        console.log('response', flat);
        this._ownerService.getOwnerById(ownerId).subscribe(
          owner => {
            console.log('response', owner);
            let request = owner;
            flat.soldOut = true;
            flat.livingStatus = true;
            request.flats = [Object(flat)];
            console.log('Final Request is:', request);
            this._ownerService.updateOwner(ownerId, request).subscribe(
              response => {
                console.log('response', response);
                this.fetchAllFlats();
                document.getElementById('register-btn')?.click();
              },
              error => {
                console.log('error', error);
              }
            );
          },
          error => {
            console.log('error', error);
          }
        );
      },
      error => {
        console.log('error', error);
      }
    );
  }

  registerFlatOnClick(flatId: number): void {
    if (this.isOwner) {
      let response = confirm("Do you want to buy this flat? (Please confirm)");

      if (response) {
        console.log('Admin, user want to buy');
        this.registerFlat(flatId);
      } else console.log('Admin, user do not want to buy');
    } else this._router.navigateByUrl('/register-owner');
  }

}
