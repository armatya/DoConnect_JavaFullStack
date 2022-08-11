import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Question } from '../question';
import { User } from '../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-questions',
  templateUrl: './all-questions.component.html',
  styleUrls: ['./all-questions.component.css']
})
export class AllQuestionsComponent implements OnInit {
  imageToShow: string;
  showImage: boolean =false;
  showImageColumn: boolean;

  constructor( private userService:UserService , private router:Router) { }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    this.getQuestions('all')

  }
  questions:Question[] | undefined

  user= new User()

 getQuestions(topic:string){
    this.userService.getQuestions(topic).subscribe((data)=>{
    this.questions=data
    console.log("data : ",data)
    let count=0;
    for(let i=0;i<data.length;i++)
    if(data[i].picByte!=null)
    {
      this.questions[i].imageToShow='data:image/jpeg;base64,' +data[i].picByte;
      this.questions[i].showImage=true;
      count++;
    }
    console.log("Count : ",count);
    if(count==0)
    this.showImageColumn=false;
    else
    this.showImageColumn=true;
  })
 }
 sendQuestionToGetAnswer(questionId:number){
  this.userService.getQuestionId(questionId)
  this.router.navigate(['/get-answer'])

 }


}
