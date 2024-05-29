import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-roommates-page',
  templateUrl: './roommates-page.component.html',
  styleUrls: ['./roommates-page.component.css']
})
export class RoommatesPageComponent implements OnInit {
    users: RoommatesItemDTO[] = [];
  
    constructor(private userService: UserService) {}
  
    ngOnInit(): void {
      this.getUsers();
    }
  
    getUsers(): void {
      this.userService.getRoommates()
        .subscribe((roommates: any[]) => {
          this.users = roommates;
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
