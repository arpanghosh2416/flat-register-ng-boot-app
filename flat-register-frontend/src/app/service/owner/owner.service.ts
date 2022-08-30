import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

//const DOMAIN = 'http://localhost:8888'
const DOMAIN = 'https://flatregister.herokuapp.com'

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  private _baseURL = `${DOMAIN}/api/owner`;
  private _getOwnerByIdAPI = `${this._baseURL}/get-owner`;
  private _createOwnerAPI = `${this._baseURL}/register-flat`;
  private _getAllOwnersAPI = `${this._baseURL}/get-all-owners`;
  private _getOwnerByFlatIdAPI = `${this._baseURL}/get-flat-owner`;

  constructor(
    private _httpClient: HttpClient
  ) {
  }

  getAllOwners(): Observable<any> {
    return this._httpClient.get<any>(this._getAllOwnersAPI);
  }

  createOwner(request: any): Observable<any> {
    return this._httpClient.post<any>(this._createOwnerAPI, request);
  }

  getOwnerByFlatId(flatId: number): Observable<any> {
    return this._httpClient.get<any>(`${this._getOwnerByFlatIdAPI}/${flatId}`);
  }

  getOwnerById(ownerId: number): Observable<any> {
    return this._httpClient.get<any>(`${this._getOwnerByIdAPI}/${ownerId}`);
  }

  updateOwner(ownerId: any, request: any): Observable<any> {
    return this._httpClient.put<any>(`${this._createOwnerAPI}/${ownerId}`, request);
  }

}
