import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-rate-module',
  templateUrl: './rate-module.component.html',
  styleUrls: ['./rate-module.component.css']
})
export class RateModuleComponent {
  rating = 0;
  rating1 = 0;
  starCount = 5;
  ratingArr: boolean[];
  ratingArr1: boolean[];

  
  
  snackNArDuration = 1000;
  constructor(
    public snackBar:MatSnackBar
  ){
    this.ratingArr = Array(this.starCount).fill(false);
    this.ratingArr1 = Array(this.starCount).fill(false)
  }

 
  returnStar(i:number, vehicle:boolean){
    if (vehicle){
      if (this.rating>=i+1){
        return 'star'
      }else{
        return 'star_border'
      }
    }else{
      if (this.rating1>=i+1){
        return 'star'
      }else{
        return 'star_border'
      }
    }
  }
  
  onCLick(i:number, vehicle:boolean){
   if (vehicle){
    this.rating = i+1;
    this.snackBar.open('You rated vehicle' , this.rating + ' / ' + this.starCount,{
      duration:this.snackNArDuration,
      panelClass: ['snack-bar']
    })
   }else{
    this.rating1= i+1;
    this.snackBar.open('You rated driver  ', this.rating1 + ' / ' + this.starCount,{
      duration:this.snackNArDuration,
      panelClass: ['snack-bar'],
    })

   }
}
}