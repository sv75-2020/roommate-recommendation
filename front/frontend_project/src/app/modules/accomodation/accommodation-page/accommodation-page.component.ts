import { Component } from '@angular/core';
import { AccommodationItemDTO, AccommodationService } from '../accommodation.service';

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrls: ['./accommodation-page.component.css']
})
export class AccommodationPageComponent {
properties: AccommodationItemDTO[] = [];
propertiesLength: number = 0;

constructor(private accommodationService: AccommodationService){

}

ngOnInit(): void {
  this.accommodationService.getAllAccommodations().subscribe((accommodations: AccommodationItemDTO[]) =>{
      this.properties = accommodations;
      this.propertiesLength = accommodations.length;
   });
}

}


