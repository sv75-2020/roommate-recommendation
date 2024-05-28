import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

 
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
    });

    constructor(private http: HttpClient) {
    }
   

  editUser(user: any, id:any): Observable<any> {
    return this.http.put<any>(environment.apiHost + 'api/updateUser/'+id, user,  {headers: this.headers});
  }

  getUser(id:any): Observable<any> {
    return this.http.get<any>(environment.apiHost + 'api/getUser/'+id,  {headers: this.headers});
  }

  
}