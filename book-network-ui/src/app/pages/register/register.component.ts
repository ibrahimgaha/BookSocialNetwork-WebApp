import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationRequest } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerRequest : RegistrationRequest = {email: '',firstname:'',lastname:'', password:''};
  errorMsg:Array<string> = [];
  constructor(private router:Router,private authService:AuthenticationService) { }

  ngOnInit(): void {
  }

  register(){
    this.errorMsg=[];
    this.authService.register({body: this.registerRequest}).subscribe({
      next:()=>{
        this.router.navigate(['/activate-account']);
      },
      error:(err)=>{
         this.errorMsg= err.error.validationErrors
  
      }
    }
      
    )
  }


  redirectToLogin() {
this.router.navigate(['/login']);
  }
}
