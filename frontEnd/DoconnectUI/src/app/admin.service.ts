import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Answer } from './answer';
import { Question } from './question';
import { User } from './user';
import { Admin } from './admin';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) {

   }

   admin= new Admin()

   url:string="http://localhost:8081/admin"

   sendAdminData(admin:Admin){
    console.log("admin data coming from "+admin.id)
    this.admin.id=admin.id
    this.admin.email=admin.email
    this.admin.name=admin.name
   }

   giveAdminData(){
    return this.admin
   }

   adminLogin(email:string,password:string):Observable<Admin>{
    return this.http.get<Admin>(this.url+"/login/"+email+"/"+password)
   }

   adminLogout(adminId:number):Observable<string>{
    return this.http.get<string>(this.url+"/logout/"+adminId)
   }

   adminRegister(admin:Admin):Observable<Admin>{
    return this.http.post<Admin>(this.url+"/register",admin)
   }

   getUnApprovedQuestions():Observable<Question[]>{
     return this.http.get<Question[]>(this.url+"/getUnApprovedQuestions")
   }
   getUnApprovedAnswers():Observable<Answer[]>{
     return this.http.get<Answer[]>(this.url+"/getUnApprovedAnswers")
   }

   question={
  }
   approveQuestion(questionId:number):Observable<Question>{
     return this.http.put<Question>(this.url+"/approveQuestion/"+questionId,this.question)

   }

   approveAnswer(answerId:number):Observable<Answer>{
     return this.http.put<Answer>(this.url+"/approveAnswer/"+answerId,Answer)
   }
   deleteQuestion(questionId:number){
     return this.http.delete(this.url+"/deleteQuestion/"+questionId)

   }
   deleteAnswer(answerId:number){
     return this.http.delete(this.url+"/deleteAnswer/"+answerId)

   }
   getUser(email:string):Observable<User> {
     return this.http.get<User>(this.url+"/getUser/"+email);
   }

   getAllUsers():Observable<User[]>{
    return this.http.get<User[]>(this.url+"/getAllUsers")
   }

}
