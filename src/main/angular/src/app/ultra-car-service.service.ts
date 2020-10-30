import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {Automobile} from '../Automobile';

@Injectable({
  providedIn: 'root'
})
export class UltraCarServiceService {

  readonly rootApiUrl: string = 'http://localhost:8080/ultra-api';

  constructor(private http: HttpClient) {
  }

  getSomething(){
    return this.http.get<Automobile[]>(this.rootApiUrl + '/read-all');
  }

}
