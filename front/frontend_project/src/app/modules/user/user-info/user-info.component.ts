import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent {

  constructor(private authService: AuthService,
    private dialog: MatDialog,
    private userService: UserService,
    private sanitizer: DomSanitizer){

  }

  fileProfile: any;
  addresses:String="";


  user: User ={
    username: '',
    fullName: '',
    password: '',
    gender: '',
    dateOfBirth: '',
    smoker: false,
    hasPets: false,
    personalityType: '',
    jobStatus: '',
    interests: '',
    cleaningHabit: '',
    worksFromHome: false,
    hasCar: false,
    moveInMonth: '',
    likesQuiet: false,
    budget: 0,
    locations: [],
    doesntWantPets: false,
    dislikesSmokingIndoors: false,
    hasRoommate: false,
    blocked: false,
    photo:''
  };

  ngOnInit(): void {
    this.userService.getUser(this.authService.getId()).subscribe((user: any) =>{
      this.user = user
      console.log(user);
      for (let location of this.user.locations) {
        this.addresses += location.address + ", ";
      }
      this.addresses = this.addresses.slice(0, -2);
      if (this.user.photo != ''){
        this.userService.getPicture(this.user.photo).then((data: Blob | MediaSource)=> {

          const temp = URL.createObjectURL(data);

          this.fileProfile = this.sanitizer.bypassSecurityTrustUrl(temp);
        });
      }
     });
  }


}



interface Location {
  id:number,
  address: string;
  
}


interface User {
  username: string;
  fullName: string;
  password: string;
  gender: String;
  dateOfBirth: String;
  smoker: boolean;
  hasPets: boolean;
  personalityType: String;
  jobStatus: String;
  interests: string;
  cleaningHabit: String;
  worksFromHome: boolean;
  hasCar: boolean;
  moveInMonth: String;
  likesQuiet: boolean;
  budget: number;
  locations: Location[];
  doesntWantPets: boolean;
  dislikesSmokingIndoors: boolean;
  hasRoommate: boolean;
  blocked: boolean;
  photo:String;
}
