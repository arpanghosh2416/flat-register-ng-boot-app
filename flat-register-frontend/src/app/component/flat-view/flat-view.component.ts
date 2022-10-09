import { ActivatedRoute, Router } from '@angular/router';
import { Component, DoCheck, OnInit } from '@angular/core';

import { AuthService } from 'src/app/service/auth/auth.service';
import { FlatService } from 'src/app/service/flat/flat.service';
import { OwnerService } from 'src/app/service/owner/owner.service';
import { TokenService } from 'src/app/service/token/token.service';

@Component({
  selector: 'app-flat-view',
  templateUrl: './flat-view.component.html',
  styleUrls: ['./flat-view.component.css']
})
export class FlatViewComponent implements OnInit, DoCheck {

  flat: any;
  owner: any = null;
  isOwner: boolean = false;
  errorMessage: string | null = null;

  constructor(
    private _router: Router,
    private _authService: AuthService,
    private _flatService: FlatService,
    private _ownerService: OwnerService,
    private _tokenService: TokenService,
    private _activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.fetchFlat();
    window.scroll(0, 0);
  }

  ngDoCheck(): void {
    this.isOwner = this._authService.isOwner();
  }

  closeErrorMessage(): void {
    this.errorMessage = null;
  }

  fetchFlat(): void {
    let flatId = this._activatedRoute.snapshot.paramMap.get('flatId');
    if (flatId !== null) {
      this._flatService.getFlatById(Number(flatId)).subscribe(
        response => {
          console.log('response', response);
          this.flat = response;
          this._ownerService.getOwnerByFlatId(this.flat.flatId).subscribe(
            response => {
              console.log('response', response);
              this.owner = response;
            },
            error => {
              console.log('error', error);
            }
          );
        },
        error => {
          console.log('error', error);
          this._router.navigateByUrl('/no-flat-found');
        }
      );
    }
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
                this.fetchFlat();
                document.getElementById('register-btn')?.click();
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

    if (this.isOwner) {
      let msg = `Are you ready to pay ₹${this.flat.price}0 for flat number ${this.flat.flatId}? (Press OK to confirm)`;
      let response = confirm(msg);

      if (response) {
        console.log('Admin, user want to buy');
        this.registerFlat(flatId);
      } else console.log('Admin, user do not want to buy');
    } else this._router.navigateByUrl('/register-owner');
  }

}
