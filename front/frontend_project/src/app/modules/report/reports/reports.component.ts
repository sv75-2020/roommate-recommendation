import { Component, OnInit } from '@angular/core';
import { ReportsService } from '../reports.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  months: string[] = [
    'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'
  ];
  selectedMonth: string | undefined;
  monthlyEarnings: number | undefined;
  averageRatings: { neighborhood: string; averageRating: number; }[] | undefined;
  popularLocations: { month: string; location: string; }[] | undefined;

  constructor(private reportsService: ReportsService) { }

  ngOnInit(): void {
    this.selectedMonth = this.months[new Date().getMonth()];
    this.fetchMonthlyEarnings();
    this.fetchAverageRatings();
    this.fetchPopularLocations();
  }

  fetchMonthlyEarnings(): void {
    this.reportsService.getMonthlyEarnings().subscribe(data => {
      this.monthlyEarnings = data;
    });
  }

  fetchAverageRatings(): void {
    this.reportsService.getAverageRatings().subscribe(data => {
      this.averageRatings = data;
    });
  }

  fetchPopularLocations(): void {
    this.reportsService.getPopularLocations().subscribe(data => {
      this.popularLocations = data;
    });
  }

  onMonthChange(): void {
    this.fetchMonthlyEarnings();
  }
}
