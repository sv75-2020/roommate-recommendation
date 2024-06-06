import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {



  constructor(private http: HttpClient) {
  }
  
  getAllAccommodations(): Observable<AccommodationItemDTO[]> {
    return this.http.get<AccommodationItemDTO[]>(environment.apiHost + 'api/accommodations');
  }

  getAccommodation(id:any): Observable<Accommodation> {
    return this.http.get<Accommodation>(environment.apiHost + 'api/accommodations/'+id);
  }

  getHistoryAccommodations(): Observable<AccommodationItemDTO[]> {
    return this.http.get<AccommodationItemDTO[]>(environment.apiHost + 'api/accommodations/history');
  }

  rateAccommodation(review: any): Observable<any>  {
    return this.http.post<any>(environment.apiHost + 'api/rateAccommodation', review);
  }

  getAverageAccommodationRating(id:any): Observable<number> {
    return this.http.get<number>(environment.apiHost + 'api/accommodations/'+id+'/average');
  }

  getReviewForAccommodation(accommodationId: number): Observable<AccommodationReviewDTO> {
    return this.http.get<AccommodationReviewDTO>(environment.apiHost +`api/review/${accommodationId}/accommodation`);
  }
  
}

export interface AccommodationPage {
  totalItems: number; // Total number of accommodation listings
  totalPages: number; // Total number of pages available
  currentPage: number; // Current page number
  pageSize: number; // Number of listings per page
  accommodations: Accommodation[]; // Array of accommodation listings
}


export interface AccommodationItemDTO {
rating: any;
rated: any;
  id: number;
  address: string;
  numOfRooms: number;
  price: number;
  location: LocationDTO;
}

export interface Accommodation {
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

export interface AccommodationReviewDTO {
  comment: string;
  accommodationId: number;
  rating: number;
}
