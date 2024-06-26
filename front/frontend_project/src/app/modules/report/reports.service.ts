import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {


  constructor(private http: HttpClient) { }


  getReport(month: number, year: number): Observable<{ accommodationRatingReport: AverageRating[], popularLocationsDTO: PopularLocationsDTO[], totalEarnings: number}> {
    const url = environment.apiHost + `api/reports?month=${month}&year=${year}`;
    return this.http.get<{ accommodationRatingReport: AverageRating[], popularLocationsDTO: PopularLocationsDTO[], totalEarnings: number }>(url);
  }

  checkDeposits(): Observable<any>{
    return this.http.get<any>(environment.apiHost+'api/events/deposit')
  }
  checkBills() : Observable<any>{
    return this.http.get<any>(environment.apiHost+'api/events/bills')
  }
}

export interface PopularLocationsDTO {
  count: number;
  locationName: string;
}

export interface AverageRating {
  locationName: string;
  averageRating: number;
}
