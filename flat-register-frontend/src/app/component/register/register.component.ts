import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    userRole: new FormControl(''),
    cpass: new FormControl('')
  });

  errormessage:boolean = false;
  successMessage:boolean = false;

  constructor(
    private _userService: UserService
  ) {
  }

  ngOnInit(): void {
  }

  closeSuccessMessage(): void {
    this.successMessage = false;
  }

  registerOnClick(): void {
    let request = this.registerForm.value;
    console.log('register works:', request);
    this.registerForm.value.userRole = 'NORMAL';
    this._userService.register(request).subscribe(
      response => {
        console.log('response', response);
        this.successMessage = true;
        this.registerForm.reset();
      },
      error => {
        console.log('error', error);
        this.successMessage = false;
      }
    );
  }

}
