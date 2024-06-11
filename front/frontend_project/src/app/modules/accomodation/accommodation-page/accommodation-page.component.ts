import { Component } from '@angular/core';
import { AccommodationItemDTO, AccommodationService } from '../accommodation.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-accommodation-page',
  templateUrl: './accommodation-page.component.html',
  styleUrls: ['./accommodation-page.component.css']
})
export class AccommodationPageComponent {
properties: AccommodationItemDTO[] = [];
propertiesLength: number = 0;
hasActiveAccommodation:boolean=false;

constructor(private accommodationService: AccommodationService, private authService:AuthService){

}

ngOnInit(): void {
  this.accommodationService.getAllAccommodations().subscribe((accommodations: AccommodationItemDTO[]) =>{
      this.properties = accommodations;
      this.propertiesLength = accommodations.length;
   });
   this.accommodationService.getActiveAccommodation(this.authService.getId()).subscribe((accommodation: any) =>{
    if(accommodation!=null)
    this.hasActiveAccommodation=true;
    console.log(accommodation)
 });
}

findAccommodation():void{
  this.accommodationService.findAccommodation().subscribe((accommodation: any) =>{
    console.log(accommodation);
 });
}

}


