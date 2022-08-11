import { Component, OnInit } from '@angular/core';
import { Answer } from '../answer';
import { User } from '../user';
import { UserService } from './../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-get-answer',
  templateUrl: './get-answer.component.html',
  styleUrls: ['./get-answer.component.css']
})
export class GetAnswerComponent implements OnInit {

  constructor(private userService:UserService , private router:Router) { }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    this.questionId= this.userService.sendQuestionId()
    this.getAnswers(this.questionId)
  }
  user= new User()

  postAnswerDTO={
    userId:this.user.id,
    questionId:0,
    answer:''
  }
  questionId:number=0
  answer= new Answer()
  answers:Answer[] | undefined
  alreadyAnswered:boolean=false
  firstAnswer:boolean=false


  giveAnswer(data:any){
    if(this.user.id==0){
      alert("Login Required to answer")
      this.router.navigate(['/login'])
    }

    this.postAnswerDTO.answer=data.answer
    this.postAnswerDTO.questionId=this.questionId
    this.postAnswerDTO.userId=this.user.id
	  this.userService.giveAnswer(this.postAnswerDTO).subscribe((data)=>{
      this.answer=data

      if(data.id !==0){
        alert("Answer Posted")
        this.router.navigate(['/user'])
      }
    })
	}

  getAnswers(questionId:number) {


    this.userService.getAnswers(questionId).subscribe((data)=>{
      console.log(data)
     this.answers=data
     console.log("answers are"+this.answers)
     if(data.length==0){
      alert("Not answerd yet! Add answer")
      this.firstAnswer=true
     }
     else{
      this.alreadyAnswered=true
     }

    })
 }

}
