import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { FeedbackService } from 'src/app/service/feedback/feedback.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  errorMessage: string | null = null;
  successMessage: string | null = null;

  feedbackForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    message: new FormControl('', [Validators.required])
  });

  constructor(
    private _feedbackService: FeedbackService
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

  sendFeedbackOnClick(): void {
    let request = this.feedbackForm.value;
    console.log('send feedback works', request);

    this._feedbackService.sendFeedback(request).subscribe(
      response => {
        console.log('response', response);
        this.errorMessage = null;
        this.feedbackForm.reset();
        this.successMessage = 'Thanks for giving us feedback 😊';
      },
      error => {
        console.log('error', error);
        this.successMessage = null;
        this.errorMessage = `Internal Server Error 500! Something went wrong in our server`;
      }
    );
  }

  get email(): FormControl {
    return this.feedbackForm.get('email') as FormControl;
  }

  get message(): FormControl {
    return this.feedbackForm.get('message') as FormControl;
  }

}
