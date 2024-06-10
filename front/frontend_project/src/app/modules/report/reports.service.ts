import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  constructor(private http: HttpClient) { }

  getMonthlyEarnings(): Observable<number> {
    return this.http.get<number>('/api/reports/monthly-earnings');
  }

  getAverageRatings(): Observable<{ neighborhood: string, averageRating: number }[]> {
    return this.http.get<{ neighborhood: string, averageRating: number }[]>('/api/reports/average-ratings');
  }

  getReport(month: number, year: number): Observable<{ accommodationRatingReport: AverageRating[], popularLocationsDTO: PopularLocationsDTO[], totalEarnings: number}> {
    const url = environment.apiHost + `api/reports?month=${month}&year=${year}`;
    return this.http.get<{ accommodationRatingReport: AverageRating[], popularLocationsDTO: PopularLocationsDTO[], totalEarnings: number }>(url);
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
