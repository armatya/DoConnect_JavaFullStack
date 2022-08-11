import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { UserService } from './../user.service';
import { Answer } from './../answer';
import { Question } from './../question';
import { User } from '../user';
import { Router } from '@angular/router';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  @ViewChild('fName') fName: ElementRef;
  @ViewChild('lName') lName: ElementRef;
  @ViewChild('pass') pass: ElementRef;
  @ViewChild('mNumber') mNumber: ElementRef;
  @ViewChild('mail') mail: ElementRef;
  constructor(private userService:UserService , private router:Router) {

   }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    console.log("user data is"+this.user.id)
    if(this.user.id==0){
      alert("Login required")
      this.router.navigate(['/login'])
    }

  }

  answer:Answer | undefined
  answers:Answer[] | undefined
  questions:Question[] | undefined
  response:any
  user= new User()



	searchQuestion(question:string) {
	   this.userService.searchQuestion(question).subscribe((data)=>{
      this.questions=data
     })
	}

  userLogout( userId:number) {
		 this.userService.userLogout(userId).subscribe((data)=>{
      this.response=data
     },err =>{
      this.user=new User()
      this.userService.sendUserData(this.user)
      this.router.navigate(["/login"])
     }
     )
	}
  userUpdate(data)
  {
    this.user.email=data.email
    this.user.name=data.fName+" "+data.lName
    this.user.password=data.password
    this.user.phoneNumber=data.mNumber
		 this.userService.userRegister(this.user,'update').subscribe((data)=>{
      this.user=data
      this.router.navigate(['/user'])
     },err =>{
     }
     );
  }
}
