import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {Automobile} from '../Automobile';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UltraCarServiceService {

  readonly rootApiUrl: string = environment.host_ip + '/ultra-api';

  constructor(private http: HttpClient) {
  }

  getListOfCars(): Observable<any> {
    return this.http.get<Automobile[]>(this.rootApiUrl + '/read-all');
  }

  searchSomething(searchTerm: string): Observable<Automobile> {
    if (!searchTerm.trim()) {
      return of();
    }
    return this.http.get<Automobile>(`${this.rootApiUrl}/read-single?id=${searchTerm}`);
  }

  deleteSomething(id: number): void{
    this.http.delete(`${this.rootApiUrl}/delete-car?id=${id}`).subscribe();
  }

  addSomething(automobile: any): void{
    this.http.post<string>(`${this.rootApiUrl}/create-car`, automobile).subscribe();
  }

  updateSomething(automobile: any): void{
    this.http.put(`${this.rootApiUrl}/update-car?id=${automobile.m_id}`, automobile).subscribe();
  }
}
