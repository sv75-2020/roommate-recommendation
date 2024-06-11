import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../auth/auth.service';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent {


  signupForm !: FormGroup;
  hide = true;

  personalityTypes = ['INTROVERT', 'EXTROVERT', 'NOT SURE']; 
  jobStatuses = ['EMPLOYED', 'UNEMPLOYED', 'STUDENT']; 
  cleaningHabits = ['ONCE_IN_A_WHILE', 'OFTEN', 'EVERYDAY']; 
  months = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];
  user: User ={
    username: '',
    fullName: '',
    password: '',
    gender: '',
    dateOfBirth: '',
    smoker: false,
    hasPets: false,
    personalityType: '',
    jobStatus: '',
    interests: '',
    cleaningHabit: '',
    worksFromHome: false,
    hasCar: false,
    moveInMonth: '',
    likesQuiet: false,
    budget: 0,
    locations: [],
    doesntWantPets: false,
    dislikesSmokingIndoors: false,
    hasRoommate: false,
    blocked: false
  };
  locations:Location[]=[];

  constructor(private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private authService: AuthService,
    private userService: UserService,
    private router: Router){
    }
   

  ngOnInit(){

    this.authService.getLocations().subscribe({
      next: (result: any) => {
       this.locations=result;
      
       this.userService.getUser(this.authService.getId()).subscribe({
        next: (result: any) => {
         this.user=result;
        
  
         this.signupForm = this.formBuilder.group({
          fullName: [this.user.fullName, Validators.required],
          username: [this.user.username, Validators.required],
          budget: [this.user.budget, [Validators.required, Validators.min(0)]],
          password: ['', Validators.required],
          interests: [this.user.interests, Validators.required],
          moveInMonth: [this.user.moveInMonth, Validators.required],
          locations: [this.user.locations, Validators.required],
          personalityType: [this.user.personalityType, Validators.required],
          jobStatus: [this.user.jobStatus, Validators.required],
          cleaningHabit: [this.user.cleaningHabit, Validators.required],
          dateOfBirth: [this.user.dateOfBirth[0]+"-"+this.user.dateOfBirth[1]+"-"+this.user.dateOfBirth[2], Validators.required],
          gender: [this.user.gender, Validators.required],
          hasPets: [this.user.hasPets],
          smoker: [this.user.smoker],
          worksFromHome: [this.user.worksFromHome],
          hasCar: [this.user.hasCar],
          likesQuiet: [this.user.likesQuiet],
          doesntWantPets: [this.user.doesntWantPets],
          dislikesSmokingIndoors: [this.user.dislikesSmokingIndoors],
          hasRoommate: [this.user.hasRoommate],
          blocked: [this.user.blocked]
        });
        console.log(this.user)
  
  
        },
      });
      
      }
      });
    
    
  }

  editUser() : void{  
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
      blocked: this.signupForm.value.blocked
      };
      console.log(user);
      this.userService.editUser(user,this.authService.getId()).subscribe({
        next: (result: any) => {
         console.log(result);
         this.router.navigate(['/user']);
        },
      });
    
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
  gender: String;
  dateOfBirth: String;
  smoker: boolean;
  hasPets: boolean;
  personalityType: String;
  jobStatus: String;
  interests: string;
  cleaningHabit: String;
  worksFromHome: boolean;
  hasCar: boolean;
  moveInMonth: String;
  likesQuiet: boolean;
  budget: number;
  locations: Location[];
  doesntWantPets: boolean;
  dislikesSmokingIndoors: boolean;
  hasRoommate: boolean;
  blocked: boolean;
}
