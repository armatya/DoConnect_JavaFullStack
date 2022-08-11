import { Component, ContentChild, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [NavBarComponent]
})
export class LoginComponent implements OnInit {
  showPassword = false;
  @ViewChild('password_input') input: ElementRef;
  @ViewChild('prev_email') prev_email: ElementRef;
  @ViewChild('forget_mail') forget_mail: ElementRef;
  @ViewChild('modal') modal: ElementRef;

  constructor(private userService: UserService
    , private router: Router, private nav:NavBarComponent) { }

  ngOnInit(): void {
  }

  user = new User()
  email: string = ''
  password: string = ''
  forgetEmail
  sendUserData(user: User) {
    this.userService.sendUserData(user)
  }

  userLogin(data: any) {

    this.email = data.email
    this.password = data.password

    this.userService.userLogin(this.email, this.password).
      subscribe((data) => {
        this.user = data
        this.sendUserData(this.user)
        this.router.navigate(['/user'])
      }, err => {
        alert("UserName or Password Wrong")
      })
      this.nav.isSearched=false;
  }

  toggleShow() {
    this.showPassword = !this.showPassword;
    this.input.nativeElement.type = this.showPassword ? 'text' : 'password';
  }
  resetPassword(data: any) {
    console.log("data:", data);
    this.userService.forgetPassword(data.forgetEmail).subscribe((data) => {
      if (data) {
        alert("Your Old password is sent via email");
      }
      else {
        alert("Your email ID is not registered, Please register");
      }
    }, err => {

    });
  }
  setEmail() {
    let mail = this.prev_email.nativeElement.value;
    if (mail.includes(".") && this.email.includes("@"))
      this.forget_mail.nativeElement.value = mail;
    console.log(this.forget_mail.nativeElement.value);
  }
}
