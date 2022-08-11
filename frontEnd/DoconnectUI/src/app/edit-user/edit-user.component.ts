import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  @ViewChild('password_input') input: ElementRef;
  showPassword: boolean;
  constructor(private userService:UserService , private router:Router, private activeRoute:ActivatedRoute) { }
  fName:String;
  lName:String;
  email:string;
  password:String
  mNumber:number
  ngOnInit(): void {
    console.log(this.activeRoute.queryParams['_value']);
    let userData=this.activeRoute.queryParams['_value'];
    console.log(userData.email);
    this.fName=userData.name.split(" ")[0];
    this.lName=userData.name.split(" ")[1];
    this.email=userData.email;
    this.password=userData.password;
    this.mNumber=userData.phoneNumber;
  }
  userRegister(data)
  {
    let user=new User()
    user.email=this.email;
    user.name=data.fName+" "+data.lName
    user.password=data.password
    user.phoneNumber=data.mNumber
    console.log("user data : ",user);
		 this.userService.userRegister(user,'update').subscribe((data)=>{
      user=data
      alert("Update Successful !!! Please Login")
      this.router.navigate(['/login'])
     },err =>{
      // this.router.navigate(['/login'])
      alert("Some Error occured");
     });
  }
  reset(){
    this.fName="";
    this.lName="";
    this.password="";
    this.mNumber=null;
  }
  toggleShow() {
    this.showPassword = !this.showPassword;
    this.input.nativeElement.type = this.showPassword ? 'text' : 'password';
  }
}
