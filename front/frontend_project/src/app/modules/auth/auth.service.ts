import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable} from 'rxjs';
import { environment } from '../../../environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    skip: 'true',
    });

    private headersReg = new HttpHeaders({
      'Access-Control-Allow-Methods':'*',
      'Content-type':'application/json'
    });
  
    private user$ = new BehaviorSubject<string | null>("");
    userState$ = this.user$.asObservable();
  
    constructor(private http: HttpClient) {
      this.user$.next(this.getRole());
    }
  
    login(auth: any): Observable<Token> {
      return this.http.post<Token>(environment.apiHost + 'api/user/login', auth, {
        headers: this.headers,
      });
    }

   
    getRole(): string | null {
      if (this.isLoggedIn()) {
        const accessToken: string = localStorage.getItem('user') || '';
        const helper = new JwtHelperService();
        const role = helper.decodeToken(accessToken).role;
        return role;
      }
      return null;
    }
  
    getId(): any{
      const accessToken: any = localStorage.getItem('user');
      const helper = new JwtHelperService();
      const id = helper.decodeToken(accessToken).id;
      return id;
    }

     isLoggedIn(): boolean {
    if (localStorage.getItem('user') != null) {
      return true;
    }
    return false;
  }
  getUsername(): string{
    const accessToken: any = localStorage.getItem('user');
    const helper = new JwtHelperService();
    const sub = helper.decodeToken(accessToken).sub;
    return sub;
  }

  setUser(): void {
    this.user$.next(this.getRole());
  }

  getUrlPath(): string {
    if (this.getRole() =="USER") {
        return "certificates";
    }else if (this.getRole() == "ADMIN") {
      return "certificates";
    }
    return "";
  }
  logout(): Observable<string> {
    return this.http.get(environment.apiHost + 'api/logout', {
      responseType: 'text',
    });
  }


  /*register(user: User): Observable<any> {
    return this.http.post<any>(environment.apiHost + 'api/user/register', user,  {headers: this.headersReg});
  }*/

  
}

export interface Login{
  username: string,
  password: string
}
