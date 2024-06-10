import { Component, OnInit } from '@angular/core';
import { AverageRating, ReportsService } from '../reports.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  months: string[] = [
    'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'
  ];
  selectedMonth: string  = this.months[new Date().getMonth()];
  selectedYear: number = new Date().getFullYear();
  monthlyEarnings: number = 0;
  popularLocations: { count: number; locationName: string; }[] | undefined;
  accommodationRatingReport: AverageRating[] | undefined;
  years: number[] = [
    2024, 2023, 2022
  ];

  constructor(private reportsService: ReportsService) { }

  ngOnInit(): void {
    this.fetchReport();
  }


  fetchReport(): void {
    this.reportsService.getReport(this.months.indexOf(this.selectedMonth)+1, this.selectedYear).subscribe(data => {
      this.popularLocations = data.popularLocationsDTO;
      this.accommodationRatingReport = data.accommodationRatingReport;
      this.monthlyEarnings = data.totalEarnings;
    });
  }

  onMonthChange(): void {
    this.fetchReport();
  }
}
