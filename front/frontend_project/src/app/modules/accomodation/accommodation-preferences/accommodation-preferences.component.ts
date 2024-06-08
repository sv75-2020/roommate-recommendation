import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth/auth.service';
import { AccommodationService } from '../accommodation.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accommodation-preferences',
  templateUrl: './accommodation-preferences.component.html',
  styleUrls: ['./accommodation-preferences.component.css']
})
export class AccommodationPreferencesComponent implements OnInit{

  constructor(private formBuilder: FormBuilder,
    private accommodationService: AccommodationService,
     private router: Router){
    }

  preferencesForm !: FormGroup;
  hide = true;

  ngOnInit(){
      
      this.preferencesForm = this.formBuilder.group({
        closeToUni: [false],
        closeToCenter: [false],
        groundFloor: [false],
        topFloor: [false],
        elevator: [false],
        AC: [false],
        numOfRooms:['', Validators.required],
      });
      
    }
  
   
  savePreferences() : void{  
    let preferences: AccommodationPreferences = {
      closeToUni:this.preferencesForm.value.closeToUni,
      closeToCenter:this.preferencesForm.value.closeToCenter,
      topFloor:this.preferencesForm.value.topFloor,
      groundFloor:this.preferencesForm.value.groundFloor,
      elevator:this.preferencesForm.value.elevator,
      AC:this.preferencesForm.value.AC,
      numOfRooms:this.preferencesForm.value.numOfRooms,
      user:null
      };

      this.accommodationService.addAccommodationPreferences(preferences).subscribe({
        next: (result: any) => {
         console.log(result)
         this.router.navigate(['/login']);
  
        },
      });
    
  }

 
  

  

}

interface AccommodationPreferences {
  closeToUni:boolean;
  closeToCenter:boolean;
  topFloor:boolean;
  groundFloor:boolean;
  elevator:boolean;
  AC:boolean;
  numOfRooms:number;
  user:any
}