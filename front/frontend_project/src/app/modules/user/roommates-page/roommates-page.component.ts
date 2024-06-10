import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from '../../shared/ok-dialog/ok-dialog.component';

@Component({
  selector: 'app-roommates-page',
  templateUrl: './roommates-page.component.html',
  styleUrls: ['./roommates-page.component.css']
})
export class RoommatesPageComponent implements OnInit {
    users: RoommatesItemDTO[] = [];
  
    constructor(private userService: UserService, private dialog:MatDialog) {}
  
    ngOnInit(): void {
      this.getUsers();
    }
  
    getUsers(): void {
      this.userService.getRoommates()
        .subscribe((roommates: any[]) => {
          this.users = roommates;
        });
    }

    findRoommate(): void {
      this.userService.findRoommate()
        .subscribe((roommate: any) => {
          this.dialog.open(OkDialogComponent, {
            data: {dialogMessage: "Roommate request sent to user "+roommate.fullName},
          });  
        });
    }
    
  }

  export interface RoommatesItemDTO {
    fullName: string;
    username: string;
    gender: string;
    dateOfBirth: string;
    smoker: boolean;
    hasPets: boolean;
    jobStatus: string;
    budget: number;
  }
