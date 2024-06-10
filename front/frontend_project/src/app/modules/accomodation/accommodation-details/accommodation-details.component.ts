import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Accommodation, AccommodationService } from '../accommodation.service';
import { RateDialogComponent } from '../../review/rate-dialog/rate-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-accommodation-details',
  templateUrl: './accommodation-details.component.html',
  styleUrls: ['./accommodation-details.component.css']
})
export class AccommodationDetailsComponent {

accommodation!: Accommodation;
rating: any;
rated: any;
accommodationRate: any;

  constructor(private route: ActivatedRoute, private accommodationService: AccommodationService, private dialog:MatDialog) {}
  
  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.accommodationService.getAccommodation(id).subscribe((a: Accommodation)=>{
        this.accommodation = a;
      })
    });
  }

  
}
