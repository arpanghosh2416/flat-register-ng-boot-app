import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]),
    password: new FormControl('', [Validators.required, Validators.pattern("((?=.*[@!#$%])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,30})")]),
    userRole: new FormControl(''),
    cpass: new FormControl('')
  });

  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private _userService: UserService
  ) {
  }

  ngOnInit(): void {
  }

  closeErrorMessage(): void {
    this.errorMessage = null;
  }

  closeSuccessMessage(): void {
    this.successMessage = null;
  }

  confirmPasswordMatched(): boolean {
    return (this.registerForm.value.password === this.registerForm.value.cpass) ? true : false;
  }

  registerOnClick(): void {
    if (this.confirmPasswordMatched()) {
      this.errorMessage = null;
      this.registerForm.value.userRole = 'NORMAL';

      let request = this.registerForm.value;
      console.log('register works:', request);

      this._userService.register(request).subscribe(
        response => {
          console.log('response', response);
          this.successMessage = `${response.username}, Your Registration is Successful. You can login now.`;
          this.registerForm.reset();
        },
        error => {
          console.log('error', error);
          this.successMessage = null;
          if (error.error?.cause === 'username')
            this.errorMessage = error.error.message;
          else
            this.errorMessage = `Can't register ${this.registerForm.value.username}, Something went wrong in our server`;
        }
      );
    } else {
      this.successMessage = null;
      this.errorMessage = `${this.registerForm.value.username}, Your confirm password didn't match`;
    }
  }

  get username(): FormControl {
    return this.registerForm.get('username') as FormControl;
  }

  get password(): FormControl {
    return this.registerForm.get('password') as FormControl;
  }

}
