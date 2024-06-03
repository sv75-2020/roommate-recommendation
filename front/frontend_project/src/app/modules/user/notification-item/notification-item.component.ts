import { Component, Input } from '@angular/core';
import { UserService } from '../user.service';
import { Notification } from '../notifications/notifications.component';

@Component({
  selector: 'app-notification-item',
  templateUrl: './notification-item.component.html',
  styleUrls: ['./notification-item.component.css']
})
export class NotificationItemComponent {
  @Input() notification!: Notification;

  constructor(private userService: UserService) {}

  onAccept(notification:any) {
    if(notification.type=="reservationRequest"){
      this.userService.acceptReservation(notification.requestId).subscribe((result: any) =>{
        console.log(result)
       });
    }
    else{
      this.userService.acceptRoommate(notification.requestId).subscribe((result: any) =>{
        console.log(result)
       });
    }
   
  }

  onDeny(notification:any) {
    if(notification.type=="reservationRequest"){
      this.userService.denyReservation(notification.requestId).subscribe((result: any) =>{
        console.log(result)
       });
    }
    else{
      this.userService.denyRoommate(notification.requestId).subscribe((result: any) =>{
        console.log(result)
       });
    }
  }

  payBill(notification:any) {
    
    this.userService.payBill(notification.requestId).subscribe((result: any) =>{
      console.log(result)
      });

  }

  getDisplayText(type: string): string {
    switch (type) {
      case 'bill':
        return 'Monthly Bill';
      case 'roommateRequest':
        return 'Roommate Request';
      case 'reservationRequest':
        return 'Reservation Request';
        case 'warning':
          return 'Warning';
      default:
        return type;
    }
  }
}
