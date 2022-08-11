import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { ChatService } from './../chat.service';
import { MessageDTO } from './../messageDTO';
import { UserService } from './../user.service';
import { Router } from '@angular/router';
import {MatCardModule} from '@angular/material/card';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor( private chatService:ChatService , private userService:UserService,
    private router:Router) { }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()

    if (this.user.id==0) {
      alert("Login Required")
      this.router.navigate(['/login'])
    }
  }
messageDTO= new MessageDTO()
messages:MessageDTO [] | undefined
user= new User()

  sendMessage(m:string) {
   this.messageDTO.fromUser=this.user.name
    this.messageDTO.message=m

    this.chatService.sendMessage(this.messageDTO).subscribe((data)=>{
      this.messageDTO=data
      this.getMessage()
    })
  }

  getMessage() {
    this.chatService.getMessage().subscribe((data)=>{
      this.messages=data
    })


  }
}
