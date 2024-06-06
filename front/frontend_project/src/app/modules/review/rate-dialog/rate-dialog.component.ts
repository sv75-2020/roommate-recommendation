import { Component, Inject, Optional } from '@angular/core';
import { AccommodationItemDTO, AccommodationReviewDTO, AccommodationService } from '../../accomodation/accommodation.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-rate-dialog',
  templateUrl: './rate-dialog.component.html',
  styleUrls: ['./rate-dialog.component.css']
})
export class RateDialogComponent {

  constructor(private rideService:AccommodationService,public dialogRef: MatDialogRef<RateDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
    this.accommodation = data;
  }

accommodation:any;
review:AccommodationReviewDTO | undefined;

rateForm = new FormGroup({
  accComment: new FormControl('', [Validators.required]),
  accRating: new FormControl('', [Validators.required,Validators.pattern("^[0-5]*$"),Validators.maxLength(1)]),
});

rate(){
  const ratingVal = {
    accComment: this.rateForm.value.accComment,
    accRating: this.rateForm.value.accRating
  };

  this.review={
    comment:ratingVal.accComment || '',
    rating:Number(ratingVal.accRating),
    accommodationId:this.accommodation
  }
  
  if (this.rateForm.valid) {
    this.rideService.rateAccommodation(this.review).subscribe({
      next: (res: any) => {
        console.log(res);
      }
    });

  }
 
}


}

