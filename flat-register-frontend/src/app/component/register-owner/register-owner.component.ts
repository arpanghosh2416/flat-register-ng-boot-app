import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { UserService } from 'src/app/service/user/user.service';
import { OwnerService } from 'src/app/service/owner/owner.service';
import { TokenService } from 'src/app/service/token/token.service';

@Component({
  selector: 'app-register-owner',
  templateUrl: './register-owner.component.html',
  styleUrls: ['./register-owner.component.css']
})
export class RegisterOwnerComponent implements OnInit {

  errorMessage: string | null = null;

  registerOwnerForm: FormGroup = new FormGroup({
    fname: new FormControl('', [Validators.required, Validators.minLength(3)]),
    lname: new FormControl('', [Validators.required, Validators.minLength(2)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    phone: new FormControl('', [Validators.required, Validators.pattern("^\\d{10}$")])
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

  closeErrorMessage(): void {
    this.errorMessage = null;
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
    console.log(this.fname.errors);
    console.log(this.lname.errors);
    console.log(this.email.errors);
    console.log(this.phone.errors);

    this._ownerService.createOwner(request).subscribe(
      response => {
        console.log('response', response);
        this.errorMessage = null;
        this.updateOwnerStatus(response.ownerId);
        // this._tokenService.storeOwner(response);
        this._router.navigateByUrl('/home');
      },
      error => {
        console.log('error', error);
        this.errorMessage = error.error.message;
      }
    );
  }

  get fname(): FormControl {
    return this.registerOwnerForm.get('fname') as FormControl;
  }

  get lname(): FormControl {
    return this.registerOwnerForm.get('lname') as FormControl;
  }

  get email(): FormControl {
    return this.registerOwnerForm.get('email') as FormControl;
  }

  get phone(): FormControl {
    return this.registerOwnerForm.get('phone') as FormControl;
  }

}
