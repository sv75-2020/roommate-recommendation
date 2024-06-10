import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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

  getPopularLocations(): Observable<{ month: string, location: string }[]> {
    return this.http.get<{ month: string, location: string }[]>('/api/reports/popular-locations');
  }
}
