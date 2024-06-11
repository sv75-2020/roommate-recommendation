import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[] = [];

  constructor(private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.userState$.subscribe((result) => {
      if(result=="USER"){
        this.userService.getUserNotifications(this.authService.getId()).subscribe((result: any) =>{
          console.log(result);
          this.notifications=result
         });
      }
      else{
        this.userService.getAdminNotifications().subscribe((result: any) =>{
          console.log(result);
          this.notifications=result
         });
      }
    });
   

  }
}

export interface Notification {
  id: number;
  message: string;
  type: string;
}

export interface ActionNotification extends Notification {
  acceptText: string;
  denyText: string;
}

export interface PaymentNotification extends Notification {
  pay:string
}
