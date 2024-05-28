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
  
  getAllAccommodations(): Observable<any> {
    return this.http.get<any>(environment.apiHost + 'api/accommodations');
  }

}
