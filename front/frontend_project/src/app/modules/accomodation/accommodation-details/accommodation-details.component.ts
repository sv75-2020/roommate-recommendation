import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Accommodation, AccommodationService } from '../accommodation.service';

@Component({
  selector: 'app-accommodation-details',
  templateUrl: './accommodation-details.component.html',
  styleUrls: ['./accommodation-details.component.css']
})
export class AccommodationDetailsComponent {
  accommodation!: Accommodation;

  constructor(private route: ActivatedRoute, private accommodationService: AccommodationService) {}
  
  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      console.log(id);
      this.accommodationService.getAccommodation(id).subscribe((a: Accommodation)=>{
        this.accommodation = a;
      })
    });
  }
  
}
