import { Component, OnInit } from '@angular/core';
import { AdminService } from './../admin.service';
import { Question } from './../question';
import { Answer } from './../answer';
import { User } from './../user';
import { Admin } from '../admin';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private adminService:AdminService,private router:Router) { }

  ngOnInit(): void {
   this.admin= this.adminService.giveAdminData()
   console.log("admin data is"+this.admin.id)
   if(this.admin.id==0){
    alert("Login Required")
    this.router.navigate(['/admin-login'])
   }
  }

  response:any
  admin = new Admin()
  answer:Answer | undefined
  question = new Question()
  questions:Question[] | undefined
  answers:Answer[] | undefined
  user = new User()
  users: User[] | undefined
  blankQuestions=false;
  blankAnswers=false;
  blankUsers=false;

  adminLogout(adminId:number){

     this.adminService.adminLogout(adminId).subscribe((data)=>{
      this.response=(data)
     },err =>{
      console.log("error called"+err)
      this.admin=new Admin()
      this.adminService.sendAdminData(this.admin)
      this.router.navigate(['/admin-login'])
     }

     )

   }

   getUnApprovedQuestions(){
     this.adminService.getUnApprovedQuestions().subscribe((data)=>{
      console.log(data)
      this.questions=data
      if(this.questions.length==0)
      this.blankQuestions=true;
     })

  }
  getUnApprovedAnswers(){
     this.adminService.getUnApprovedAnswers().subscribe((data)=>{
      console.log(data)
      this.answers=data
      if(this.answers.length==0)
      this.blankAnswers=true;
     })

  }
  approveQuestion(questionId:number){
     this.adminService.approveQuestion(questionId).subscribe((data)=>{
      this.question=data
      if(data)
      alert("Question Approved")
      const index = this.questions.findIndex(element => {
        if (element.id === questionId) {
          return true;
        }
        return false;
      });
      this.questions.splice(index);
      if(this.questions.length==0)
      this.blankQuestions=true;
      this.router.navigate(['/admin'])
     })

  }

  approveAnswer(answerId:number){
     this.adminService.approveAnswer(answerId).subscribe((data)=>{
      this.answer=data
      if(data)
      alert("Answer Approved")
      const index = this.answers.findIndex(element => {
        if (element.id === answerId) {
          return true;
        }
        return false;
      });
      this.answers.splice(index);
      if(this.answers.length==0)
      this.blankAnswers=true;
      this.router.navigate(['/admin'])
     })
  }
  deleteQuestion(questionId:number){
    this.adminService.deleteQuestion(questionId).subscribe((data)=>{
      this.response=data
      if(data)
      alert("Question Removed")
      const index = this.questions.findIndex(element => {
        if (element.id === questionId) {
          return true;
        }
        return false;
      });
      this.questions.splice(index);
      if(this.questions.length==0)
      this.blankQuestions=true;
      this.router.navigate(['/admin'])
    })

  }
  deleteAnswer(answerId:number){
    this.adminService.deleteAnswer(answerId).subscribe((data)=>{
      this.response=data
      if(data)
      alert("Answer Removed")
      const index = this.answers.findIndex(element => {
        if (element.id === answerId) {
          return true;
        }
        return false;
      });
      this.answers.splice(index);
      if(this.answers.length==0)
      this.blankAnswers=true;
      this.router.navigate(['/admin'])
    })

  }
  getUser(email:string) {
    this.adminService.getUser(email).subscribe((data)=>{
      this.user=data
    })
  }

  addAdmin(){
    this.router.navigate(['/admin-register'])
  }

  getAllUsers(){
    this.adminService.getAllUsers().subscribe((data)=>{
      console.log(data)
      this.users=data
      if(this.users.length==0)
      this.blankUsers=true;
    })
  }

}
