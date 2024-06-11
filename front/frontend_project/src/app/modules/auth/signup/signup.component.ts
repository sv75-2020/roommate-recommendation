import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/error-dialog/error-dialog.component';
import { OkDialogComponent } from '../../shared/ok-dialog/ok-dialog.component';
import { AuthService } from '../auth.service';
import { validateRePassword } from './custom-validator/validator';
import { environment } from 'src/environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

  signupForm !: FormGroup;
  hide = true;
  file_profile: File | null | undefined;


  personalityTypes = ['INTROVERT', 'EXTROVERT', 'NOT_SURE']; 
  jobStatuses = ['EMPLOYED', 'UNEMPLOYED', 'STUDENT']; 
  cleaningHabits = ['ONCE_IN_A_WHILE', 'OFTEN', 'EVERYDAY']; 
  months = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];
  locations: Location[] = [];

  constructor(private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private authService: AuthService,
    private router: Router){
    }
   

  ngOnInit(){
    this.authService.getLocations().subscribe({
      next: (result: any) => {
       this.locations=result;
       console.log(result)


      },
    });
    
    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      fullName: ['', Validators.required],
      password: ['', Validators.required],
      gender: ['FEMALE', Validators.required],
      dateOfBirth: ['', Validators.required],
      smoker: [false],
      hasPets: [false],
      personalityType: ['', Validators.required],
      jobStatus: ['', Validators.required],
      interests: ['', Validators.required],
      cleaningHabit: ['', Validators.required],
      worksFromHome: [false],
      hasCar: [false],
      moveInMonth: ['', Validators.required],
      likesQuiet: [false],
      budget: ['', Validators.required],
      locations: [[]],
      doesntWantPets: [false],
      dislikesSmokingIndoors: [false],
      hasRoommate: [false],
      blocked: [false],
      photo:['', Validators.required],
    });
    
  }

  signup() : void{  
    let user: User = {
      username: this.signupForm.value.username,
      fullName: this.signupForm.value.fullName,
      password: this.signupForm.value.password,
      gender: this.signupForm.value.gender,
      dateOfBirth: this.signupForm.value.dateOfBirth,
      smoker: this.signupForm.value.smoker,
      hasPets: this.signupForm.value.hasPets,
      personalityType: this.signupForm.value.personalityType,
      jobStatus: this.signupForm.value.jobStatus,
      interests: this.signupForm.value.interests,
      cleaningHabit: this.signupForm.value.cleaningHabit,
      worksFromHome: this.signupForm.value.worksFromHome,
      hasCar: this.signupForm.value.hasCar,
      moveInMonth: this.signupForm.value.moveInMonth,
      likesQuiet: this.signupForm.value.likesQuiet,
      budget: this.signupForm.value.budget,
      locations: this.signupForm.value.locations,
      doesntWantPets: this.signupForm.value.doesntWantPets,
      dislikesSmokingIndoors: this.signupForm.value.dislikesSmokingIndoors,
      hasRoommate: this.signupForm.value.hasRoommate,
      blocked: this.signupForm.value.blocked,
      photo: this.signupForm.value.photo
      };
      console.log(user);
      this.authService.registerUser(user).subscribe({
        next: (result: any) => {
         console.log(result);
         this.authService.downloadPicture(this.file_profile).subscribe({
          next: (result) =>{
            console.log(result);
    
          },
          error: (error) => {
            if (error instanceof HttpErrorResponse){
              
            }
          }, 
  
        })
        this.router.navigate(['/accommodationPreferences']);
        },
        error: (error: { status: number; }) => {
          if (error instanceof HttpErrorResponse) {
            if (error.status == 403){
              alert('Please verify captcha!.');  
            }
          }
        },
      });
    
  }

 
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

  handleFileInputChangePicture(l: FileList): void {
    this.file_profile = l.item(0);
    if (l.length) {
      const f = l[0];
      const count = l.length > 1 ? `(+${l.length - 1} files)` : "";
      this.signupForm.patchValue({photo: `${f.name}${count}`});
      
    } else {
      this.signupForm.patchValue({photo: ``});
    }
  }
  

  
}

  
interface Location {
  id:number,
  address: string;
  
}

interface User {
  username: string;
  fullName: string;
  password: string;
  gender: Gender;
  dateOfBirth: String;
  smoker: boolean;
  hasPets: boolean;
  personalityType: PersonalityType;
  jobStatus: JobStatus;
  interests: string;
  cleaningHabit: CleaningHabit;
  worksFromHome: boolean;
  hasCar: boolean;
  moveInMonth: Month;
  likesQuiet: boolean;
  budget: number;
  locations: Location[];
  doesntWantPets: boolean;
  dislikesSmokingIndoors: boolean;
  hasRoommate: boolean;
  blocked: boolean;
  photo:String;
}

enum Gender {
  Male = 'MALE',
  Female = 'FEMALE'
}

enum PersonalityType {
  Introvert = 'INTROVERT',
  Extrovert = 'EXTROVERT',
  NotSure = 'NOT SURE'
}

enum JobStatus {
  Employed = 'EMPLOYED',
  Unemployed = 'UNEMPLOYED',
  Student = 'STUDENT'
}

enum CleaningHabit {
  OnceInAWhile = 'ONCE IN A WHILE',
  Everyday = 'EVERYDAY',
  Often = 'OFTEN'
}

enum Month {
  January = 'January',
  February = 'February',
  March = 'March',
  April = 'April',
  May = 'May',
  June = 'June',
  July = 'July',
  August = 'August',
  September = 'September',
  October = 'October',
  November = 'November',
  December = 'December'
}
