import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Admin } from '../admin';
import { AdminService } from './../admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-register',
  templateUrl: './admin-register.component.html',
  styleUrls: ['./admin-register.component.css']
})
export class AdminRegisterComponent implements OnInit {
  showPassword: boolean;
  @ViewChild('password_input') input: ElementRef;
  constructor(private adminService: AdminService,private router:Router) { }

  ngOnInit(): void {

    this.admin=this.adminService.giveAdminData()
    if(this.admin.id==0){
      alert("Login Required")
      this.router.navigate(['/admin-login'])
    }
  }

  admin= new Admin();

  adminRegister(data:any){

   this.admin.email= data.email
    this.admin.name= data.fName+" "+data.lName
    this.admin.password=data.password
    this.admin.phoneNumber=data.mNumber

     this.adminService.adminRegister(this.admin).subscribe((data)=>{
      this.admin=data
      alert("Admin Updated")
      this.router.navigate(['/admin'])
     },err =>{
      alert("Admin Already Available")
      this.router.navigate(['/admin'])
     })

    }
    toggleShow() {
      this.showPassword = !this.showPassword;
      this.input.nativeElement.type = this.showPassword ? 'text' : 'password';
    }
}
