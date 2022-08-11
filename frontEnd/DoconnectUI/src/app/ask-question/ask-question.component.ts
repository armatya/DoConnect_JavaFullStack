import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Question } from './../question';
import { User } from '../user';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Image } from '../messageDTO';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {

  constructor(private userService: UserService, private router: Router
    , private httpClient: HttpClient) { }



  selectedFile;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string
  imageName: any;

  ngOnInit(): void {
    this.user = this.userService.giveUserData()
    console.log(this.user.id)
    if (this.user.id == 0) {
      alert("Login required")
      this.router.navigate(['/login'])
    }
  }

  askQuestionDTO = {
    userId: 0,
    question: '',
    topic: '',
    isImage: false
  }

  user = new User()

  question: Question | undefined
  askQuestion(data: any) {
    this.askQuestionDTO.question = data.question
    this.askQuestionDTO.userId = this.user.id
    this.askQuestionDTO.topic = data.topic
    if (this.selectedFile) {
      this.askQuestionDTO.isImage = true;
      this.onUpload();
      this.userService.askQuestion((this.askQuestionDTO)).subscribe((data) => { //0.5sec
        this.question = data
        alert("Question Posted ")
        this.router.navigate(['/user'])
      });
    }
    else {
      this.userService.askQuestion((this.askQuestionDTO)).subscribe((data) => { //0.5sec
        this.question = data
        alert("Question Posted ")
        this.router.navigate(['/user'])
      });
    }
  }

  public onFileChanged(event) {
    let selectedFile = event.target.files[0];
    console.log("change : ",this.selectedFile);
    if(selectedFile.size>61440)
    {
      alert("Please Select an image less than 60KB !!!");
    }else
    this.selectedFile=selectedFile;
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload(): Boolean {
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    console.log("image : ", uploadImageData);
    //Make a call to the Spring Boot Application to save the image
    this.httpClient.post('http://localhost:8081/user/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
        if (response) {
          this.message = 'Image uploaded successfully';
          // alert("Uploaded");
          return true;
        } else {
          this.message = 'Image not uploaded successfully';
          alert("Not Uploaded");
          return false;
        }
      }
      );
    return false;
  }

  //Gets called when the user clicks on retieve image button to get the image from back end
  getImage() {
    //Make a call to Sprinf Boot to get the Image Bytes.
    this.httpClient.get('http://localhost:8081/user/get/' + this.imageName)
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }

}


