import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationrRquest } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router:Router, private authService:AuthenticationService,private tokenService:TokenService) { }
  authRequest :AuthenticationrRquest = {email: '', password:''};
  errorMsg:Array<string> = [];
  ngOnInit(): void {
  }

  login(){
    this.errorMsg=[];
    this.authService.authenticationResponse({body: this.authRequest})
    .subscribe({
      next:(result)=>{
        this.tokenService.token = result.token as string;
        this.router.navigate(['/books']);
      },
      error:(err)=>{
        console.log(err);
        if(err.error.validationErrors){
          this.errorMsg= err.error.validationErrors
        }
        else{
          this.errorMsg.push(err.error.error)
        }
      }
    })
  }
  register(){
    this.router.navigate(['/register']);
  }

}

