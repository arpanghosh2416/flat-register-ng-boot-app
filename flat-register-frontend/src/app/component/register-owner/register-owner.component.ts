import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { OwnerService } from 'src/app/service/owner/owner.service';
import { TokenService } from 'src/app/service/token/token.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-register-owner',
  templateUrl: './register-owner.component.html',
  styleUrls: ['./register-owner.component.css']
})
export class RegisterOwnerComponent implements OnInit {

  registerOwnerForm: FormGroup = new FormGroup({
    fname: new FormControl(''),
    lname: new FormControl(''),
    email: new FormControl(''),
    phone: new FormControl('')
  });

  owner: any = {
    ownerName: '',
    ownerEmail: '',
    phoneNumber: '',
    flats: [
      {
        flatId: 0,
        storeyNumber: 0,
        livingStatus: true,
        soldOut: true,
        price: 0
      }
    ]
  };

  constructor(
    private _router: Router,
    private _userService: UserService,
    private _ownerService: OwnerService,
    private _tokenService: TokenService
  ) {
  }

  ngOnInit(): void {
  }

  prepareOwner(): any {
    let formDetails = this.registerOwnerForm.value;

    this.owner.ownerName = `${formDetails.fname} ${formDetails.lname}`;
    this.owner.ownerEmail = `${formDetails.email}`;
    this.owner.phoneNumber = `${formDetails.phone}`;

    return this.owner;
  }

  updateOwnerStatus(ownerId: number): void {
    if (this._tokenService.fetchUser() !== null) {
      let username = this._tokenService.fetchUser().username;
      this._userService.updateOwnerStatus(username, {ownerStatus: ownerId}).subscribe(
        response => {
          console.log('response', response);
          this._tokenService.storeUser(response);
        },
        error => {
          console.log('error', error);
        }
      );
    }
  }

  registerOwnerOnClick(): void {
    let request = this.prepareOwner();
    console.log('register owner works:', request);
    this._ownerService.createOwner(request).subscribe(
      response => {
        console.log('response', response);
        this.updateOwnerStatus(response.ownerId);
        // this._tokenService.storeOwner(response);
        this._router.navigateByUrl('/home');
      },
      error => {
        console.log('error', error);
      }
    );
  }

}
