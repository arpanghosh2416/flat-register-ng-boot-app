import { Router } from '@angular/router';
import { Component, DoCheck, OnInit } from '@angular/core';

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
  serverError: boolean = false;
  loadingServer: boolean = true;
  errorMessage: string | null = null;
  successMessage: string | null = null;

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

  closeErrorMessage(): void {
    this.errorMessage = null;
  }

  closeSuccessMessage(): void {
    this.successMessage = null;
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
        this.serverError = false;
        this.loadingServer = false;
      },
      error => {
        console.log('error', error);

        this.serverError = true;
        this.loadingServer = false;
        this._tokenService.removeUser();
        this._tokenService.removeOwner();
        this._tokenService.removeToken();
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
                this.successMessage = `Congratulation ${owner.ownerName}, You have successfully registered flat number ${flatId}`;
                document.getElementById('register-btn')?.click();
                window.scroll(0, 0);
              },
              error => {
                console.log('rectifying-error', error);
                this._ownerService.getOwnerByFlatId(flatId).subscribe(
                  newOwner => {
                    console.log('newOwner', newOwner);
                    this.errorMessage = `Flat number ${flatId} is already registered by ${newOwner.ownerName}. Reload the page to reflect the updated information`;
                    window.scroll(0, 0);
                  },
                  error => {
                    console.log('error', error);
                  }
                );
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
    this.errorMessage = null;
    this.successMessage = null;

    if (this.isOwner) {
      const targetFlat = this.flats.find((element: any) => element.flatId === flatId)
      let msg = `Are you ready to pay ₹${targetFlat.price}0 for flat number ${targetFlat.flatId}? (Press OK to confirm)`;
      let response = confirm(msg);

      if (response) {
        console.log('Admin, user want to buy');
        this.registerFlat(flatId);
      } else console.log('Admin, user do not want to buy');
    } else this._router.navigateByUrl('/register-owner');
  }

}
