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

  onAccept() {
    // handle accept action
    //this.userService.removeNotification(this.notification.id);
  }

  onDeny() {
    // handle deny action
    //this.notificationService.removeNotification(this.notification.id);
  }

  getDisplayText(type: string): string {
    switch (type) {
      case 'bill':
        return 'Monthly Bill';
      case 'roommateRequest':
        return 'Roommate Request';
      case 'reservationRequest':
        return 'Reservation Request';
      default:
        return type;
    }
  }
}
