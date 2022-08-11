import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [NavBarComponent]
})

export class RegisterComponent implements OnInit {
  @ViewChild('password_input') input: ElementRef;
  showPassword: boolean;
  constructor( private userService:UserService , private router:Router, private nav:NavBarComponent) { }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    if(this.user.id !==0){
      alert("Already Logged")
      this.router.navigate(['/user'])
    }
  }

  user=new User()

  userRegister(data:any) {

    this.user.email=data.email
    this.user.name=data.fName+" "+data.lName
    this.user.password=data.password
    this.user.phoneNumber=data.mNumber
		 this.userService.userRegister(this.user,'register').subscribe((data)=>{
      this.user=data
      this.router.navigate(['/login'])
     },err =>{
      alert("User Already Registered")
      this.router.navigate(['/login'])
     }
     );
     this.nav.isSearched=false;
	}
  toggleShow() {
    this.showPassword = !this.showPassword;
    this.input.nativeElement.type = this.showPassword ? 'text' : 'password';
  }
}
