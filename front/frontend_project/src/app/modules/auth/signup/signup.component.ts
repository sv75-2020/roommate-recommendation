import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/error-dialog/error-dialog.component';
import { OkDialogComponent } from '../../shared/ok-dialog/ok-dialog.component';
import { AuthService } from '../auth.service';
import { validateRePassword } from './custom-validator/validator';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

  signupForm !: FormGroup;
  hide = true;

  constructor(private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private authService: AuthService){
    }
   

  ngOnInit(){
    this.signupForm = this.formBuilder.group({
      name: [
        '',
        [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
        Validators.pattern('[a-zA-Z]+'),

      ],
      ],
      surname: [
        '',
        [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
        Validators.pattern('[a-zA-Z]+'),

      ],
      ],
      phone: [
        '',
        [
          Validators.required,
          Validators.minLength(9),
          Validators.maxLength(13),
          Validators.pattern('^[0-9]*$'),
        ],
      ],

      email:['',
      [
        Validators.required,
        Validators.minLength(5),
        Validators.pattern('^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$'),
      ],
      ],
      password:['',
          [
            Validators.required,
            Validators.minLength(8),
          ],
      ],
      confirmpsw:['',
          [
            Validators.required,
            validateRePassword,
          ],
      ],
      captchaResponse: ['', [
        Validators.required
      ]]


    });
  }

  signup() : void{  
   /* let user:User = {
      name : this.signupForm.value.name,
      surname: this.signupForm.value.surname,
      mobile: this.signupForm.value.phone,
      email: this.signupForm.value.email,
      password:this.signupForm.value.password*/
    }
    
    /*if (this.signupForm.valid){
      this.authService.robot(this.signupForm.value.captchaResponse).subscribe({
        next: (result: any) => {
          const dialogRef = this.dialog.open(ConfirmIdentityComponent, {
            data: user
          });
        },
        error: (error: { status: number; }) => {
          if (error instanceof HttpErrorResponse) {
            if (error.status == 403){
              alert('Please verify captcha!.');  
            }
          }
        },
      });
      
    }else{
      alert("Please complete all required fields!");
    }

    

  }*/

 
  openErrorDialog(message: string) {
    const dialogRef = this.dialog.open(ErrorDialogComponent, {
      data: {dialogMessage: message},
    });
  }


  openOKDialog(message: string) {
    const dialogRef = this.dialog.open(OkDialogComponent, {
      data: {dialogMessage: message},
    });
  }


  
  

  

  
}


