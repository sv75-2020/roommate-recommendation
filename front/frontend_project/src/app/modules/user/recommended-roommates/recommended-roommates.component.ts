import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { MatDialog } from '@angular/material/dialog';
import { OkDialogComponent } from '../../shared/ok-dialog/ok-dialog.component';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-recommended-roommates',
  templateUrl: './recommended-roommates.component.html',
  styleUrls: ['./recommended-roommates.component.css']
})
export class RecommendedRoommatesComponent {


  users: RoommatesItemDTO[] = [];
  user:any;

  constructor(private userService: UserService, private dialog:MatDialog, private sanitizer: DomSanitizer, private authService:AuthService) {}
    

  ngOnInit(): void {
    this.getUsers();
  }


  getUsers(): void {
    this.userService.getRecommendedRoommates()
      .subscribe((roommates: any[]) => {
        console.log(roommates)
        this.users = roommates;
        this.users.forEach(u => {
          this.loadUserPhoto(u);
        });
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
  clickView(_t10: any) {
    console.log("aa")
  }

  loadUserPhoto(user:any) {
    if (user.photo != ''){
      this.userService.getPicture(user.photo).then((data: Blob | MediaSource)=> {

        const temp = URL.createObjectURL(data);

        user.fileProfile = this.sanitizer.bypassSecurityTrustUrl(temp);
      });
    }
  }

  
}

export interface RoommatesItemDTO {
  fullName: string;
  username: string;
  gender: string;
  dateOfBirth: string;
  jobStatus: string;
  photo: string;
  fileProfile: any;
}

