import { Component, OnInit } from '@angular/core';
import { AverageRating, ReportsService } from '../reports.service';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from '../../shared/ok-dialog/ok-dialog.component';

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

  constructor(private reportsService: ReportsService, private dialog:MatDialog) { }

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

  checkBills() {
    this.reportsService.checkBills().subscribe(data=> {
      this.dialog.open(OkDialogComponent, {
        data: {dialogMessage: "Bills checked. You can see user warnings in notifactions."},
      }); 
    });
  }
    
  checkDeposits() {
    this.reportsService.checkDeposits().subscribe(data=> {
      this.dialog.open(OkDialogComponent, {
        data: {dialogMessage: "Deposits checked. You can see user blocked warnings in notifactions."},
      }); 
    });
  }
}
