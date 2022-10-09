import { Component, DoCheck, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth/auth.service';

import { OwnerService } from '../../service/owner/owner.service';

@Component({
  selector: 'app-owner-list',
  templateUrl: './owner-list.component.html',
  styleUrls: ['./owner-list.component.css']
})
export class OwnerListComponent implements OnInit, DoCheck {

  owners: any = [];
  isSuperuser: boolean = false;

  constructor(
    private _authService: AuthService,
    private _ownerService: OwnerService
  ) {
  }

  ngOnInit(): void {
    setTimeout(function(){
      $(function(){
        $('#owner-list-table').DataTable();
      });
    }, 500);

    this.fetchAllOwners();
  }

  ngDoCheck(): void {
    this.verifyUser();
  }

  verifyUser(): void {
    this.isSuperuser = this._authService.isSuperuser();
  }

  fetchAllOwners(): void {
    this._ownerService.getAllOwners().subscribe(
      response => {
        console.log('response', response);
        this.owners = response;
      },
      error => {
        console.log('error', error);
      }
    );
  }

}
