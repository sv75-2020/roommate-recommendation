import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

import { OAuthService } from 'angular-oauth2-oidc';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  hide = true;
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });
  hasError: boolean = false;
  constructor(private authService: AuthService, private router: Router,
    private dialog: MatDialog) {}

  login(): void {
    const loginVal = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
    };

    console.log("aaaaa")
    if (this.loginForm.valid) {
      console.log(loginVal)
      this.authService.login(loginVal).subscribe({
        next: (result) => {
          localStorage.setItem('user', JSON.stringify(result));
          this.authService.setUser();
          this.router.navigate(['/' + this.authService.getUrlPath()]);
          console.log(result)
        },
        error: (error) => {
          if (error instanceof HttpErrorResponse) {
           
              this.hasError = true;
            

          }
        },
      });   
      
    }
  }
  
}
