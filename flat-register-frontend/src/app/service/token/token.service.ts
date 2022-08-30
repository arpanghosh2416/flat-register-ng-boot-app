import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() {
  }

  storeToken(token: string): void {
    localStorage.setItem('token', token);
  }

  storeUser(user: any): void {
    user.password = null;
    localStorage.setItem('user', JSON.stringify(user));
  }

  storeOwner(owner: any): void {
    localStorage.setItem('owner', JSON.stringify(owner));
  }

  removeUser(): void {
    if (this.isUserExist())
    localStorage.removeItem('user');
  }

  removeOwner(): void {
    if (this.isOwnerExist())
    localStorage.removeItem('owner');
  }

  removeToken(): void {
    if (this.isTokenExist())
    localStorage.removeItem('token');
  }

  fetchToken(): string | null {
    return localStorage.getItem('token');
  }

  fetchUser(): any {
    let user = localStorage.getItem('user');
    return (user !== null) ? JSON.parse(user) : null;
  }

  fetchOwner(): any {
    let owner = localStorage.getItem('owner');
    return (owner !== null) ? JSON.parse(owner) : null;
  }

  isUserExist(): boolean {
    return (this.fetchUser() === null || this.fetchUser() === undefined || this.fetchUser() === '') ? false : true;
  }

  isOwnerExist(): boolean {
    return (this.fetchOwner() === null || this.fetchOwner() === undefined || this.fetchOwner() === '') ? false : true;
  }

  isTokenExist(): boolean {
    return (this.fetchToken() === null || this.fetchToken() === undefined || this.fetchToken() === '') ? false : true;
  }

  isUserOwner(): boolean {
    if (this.isUserExist()) {
      let ownerStatus = (this.fetchUser().ownerStatus === 0) ? false : true;
      return ownerStatus;
    } else return false;
  }

}
