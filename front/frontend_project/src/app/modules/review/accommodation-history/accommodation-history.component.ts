import { Component } from '@angular/core';
import { AccommodationItemDTO, AccommodationReviewDTO, AccommodationService } from '../../accomodation/accommodation.service';
import { AuthService } from '../../auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { RateDialogComponent } from '../rate-dialog/rate-dialog.component';

@Component({
  selector: 'app-accommodation-history',
  templateUrl: './accommodation-history.component.html',
  styleUrls: ['./accommodation-history.component.css']
})
export class AccommodationHistoryComponent {

  properties: AccommodationItemDTO[] = [];
  propertiesLength: number = 0;
  
  constructor(private accommodationService: AccommodationService, private authService: AuthService, private dialog:MatDialog){
  
  }
  
  ngOnInit(): void {
    this.accommodationService.getHistoryAccommodations().subscribe((accommodations: AccommodationItemDTO[]) =>{
        this.properties = accommodations;
        this.propertiesLength = accommodations.length;
        this.properties.forEach((property: AccommodationItemDTO) => {
          // Call function to find the review for the accommodation
          property.rated = false;
          property.rating = 0;
          console.log("a")
          this.accommodationService.getReviewForAccommodation(property.id).subscribe((review: AccommodationReviewDTO) => {
              // If a review is found, set property.rated to true and property.rating to review.rating
             console.log(review)
              if (review) {
                  property.rated = true;
                  property.rating = review.rating;
              }
          });
     });
     
  });
  }

  openDialog(property:any){
    const dialogRef = this.dialog.open(RateDialogComponent, {
      data:property.id
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
    
  }
}