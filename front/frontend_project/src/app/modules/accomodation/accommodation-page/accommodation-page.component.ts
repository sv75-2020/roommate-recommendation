import { Component } from '@angular/core';
import { AccommodationService } from '../accommodation.service';

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

export interface AccommodationPage {
  totalItems: number; // Total number of accommodation listings
  totalPages: number; // Total number of pages available
  currentPage: number; // Current page number
  pageSize: number; // Number of listings per page
  accommodations: AccommodationDTO[]; // Array of accommodation listings
}


export interface AccommodationItemDTO {
  id: number;
  address: string;
  numOfRooms: number;
  price: number;
  location: LocationDTO;
}

export interface AccommodationDTO {
  id: number;
  address: string;
  numOfRooms: number;
  price: number;
  petsAllowed: boolean;
  smokingAllowed: boolean;
  parking: boolean;
  fastInternet: boolean;
  location: LocationDTO;
}

export interface LocationDTO {
  id: number;
  address: string;
}
